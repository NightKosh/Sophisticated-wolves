package sophisticated_wolves.entity;


import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenForest;
import net.minecraft.world.biome.WorldChunkManager;
import org.apache.logging.log4j.Level;
import sophisticated_wolves.SWConfiguration;
import sophisticated_wolves.SWItems;
import sophisticated_wolves.SophisticatedWolvesMod;
import sophisticated_wolves.api.ISophisticatedWolf;
import sophisticated_wolves.entity.ai.*;
import sophisticated_wolves.item.ItemDogTag;

import java.lang.reflect.Field;
import java.util.List;

public class SophisticatedWolf extends EntityWolf implements ISophisticatedWolf {

    private static boolean isDebugMode = false;
    //New Sophisticated Wolves variables
    public boolean smoking;
    public boolean puking;

    public SophisticatedWolf(World world) {
        super(world);

        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(3, new EntityAIAvoidCreeper(this, 8, 6, 1, 1.4)); //new behavior
        this.tasks.addTask(5, this.aiSit);
        this.tasks.addTask(7, new EntityAIShake(this)); //behavior for shaking
        this.tasks.addTask(10, new EntityAIAttackCancel(this)); //new behavior
        this.tasks.addTask(15, new EntityAILeapAtTarget(this, 0.4F));
        this.tasks.addTask(20, new EntityAIAttackOnCollide(this, 1, true));
        this.tasks.addTask(22, new EntityAIMoveCancel(this, 6));
        this.tasks.addTask(25, new EntityAINewFollowOwner(this, 1, 6, 2)); //changed 10.0F to 6.0F, mod class
        this.tasks.addTask(27, new EntityAIAvoidFire(this, 1, 1.4D)); //new behavior
        this.tasks.addTask(30, new EntityAIMate(this, 1));
        this.tasks.addTask(35, new EntityAINewBeg(this, 8)); //changed order with Wander, mod class
        this.tasks.addTask(40, new EntityAIWander(this, 1)); //changed order with Beg
        this.tasks.addTask(45, new EntityAIWatchClosest(this, EntityPlayer.class, 8));
        this.tasks.addTask(50, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAINewOwnerHurtByTarget(this)); //mod class
        this.targetTasks.addTask(2, new EntityAINewOwnerHurtTarget(this)); //mod class

        //Sophisticated Wolves variables
        fireResistance = 5; //modified from default value
    }

    /**
     * main AI tick function, replaces updateEntityActionState
     */
    @Override
    protected void updateAITick() {
        if (!this.getMoveHelper().isUpdating()) {
            this.setSprinting(false);
        } else {
            double f = this.getMoveHelper().getSpeed();

            if (f == 0.4F) {
                this.setSprinting(true);
            } else {
                this.setSprinting(false);
            }
        }
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(21, new String("")); //wolfname
        this.dataWatcher.addObject(22, new Byte((byte) 0)); //species
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    @Override
    public void writeEntityToNBT(NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);

        nbtTagCompound.setInteger("Species", this.getSpecies());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readEntityFromNBT(NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);

        this.updateSpecies(nbtTagCompound.getInteger("Species"));
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    @Override
    protected String getLivingSound() {
        if (this.isAngry()) {
            return "mob.wolf.growl";
        }

        //Growls if creeper is near
        if (this.isTamed() && this.CreeperAlert()) {
            return "mob.wolf.growl";
        }

        if (this.rand.nextInt(3) == 0 && !this.CreeperAlert()) {
            if (this.isTamed() && this.dataWatcher.getWatchableObjectFloat(18) < 10.0F) {
                return "mob.wolf.whine";
            } else {
                return "mob.wolf.panting";
            }
        } else {
            //sitting wolves will only bark 1/4 of the time
            if (!this.isSitting()) {
                return "mob.wolf.bark";
            } else {
                if (this.rand.nextInt(3) == 0) {
                    return "mob.wolf.bark";
                } else {
                    return null;
                }
            }
        }
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        //Stops tamed wolves from being angry at the player
        if (this.isTamed()) {
            this.setAngry(false);
        }
        //sets species
        if (this.getSpecies() == 0) {
            this.updateSpecies(this.setSpecies());
        }
    }

    /**
     * Called to update the entity's position/logic.
     */
    @Override
    public void onUpdate() {        //Checks if wolf is burning and not currently standing in fire or if wolf is poison
        if (!this.getField_70928_h() &&
                ((this.isBurning() && !this.worldObj.func_147470_e(this.boundingBox.contract(0.001D, 0.001D, 0.001D))) ||
                        ((this.isPotionActive(Potion.poison) || this.isPotionActive(Potion.wither))))) {
            this.setShaking(true);
            this.setTimeWolfIsShaking(0);
            this.setPrevTimeWolfIsShaking(0);
        }


        float timeWolfIsShaking = this.timeWolfIsShaking();
        if (!this.isWet() && this.isShaking() && this.getField_70928_h()) {
            if (timeWolfIsShaking == 0) {
                //checks if burning/poisoned/wet and sets variables
                if (this.isBurning()) {
                    this.playSound("mob.wolf.shake", this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
                    this.smoking = true;
                } else if (this.isPotionActive(Potion.poison) || this.isPotionActive(Potion.wither)) {
                    this.puking = true;
                } else {
                    this.playSound("mob.wolf.shake", this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
                }
            }

            if (this.prevTimeWolfIsShaking() >= 1.95F) {
                //extinguishing added
                if (this.smoking) {
                    this.extinguish();
                    this.playSound("random.fizz", 0.7F, 1.6F + (rand.nextFloat() - rand.nextFloat()) * 0.4F);
                    this.smoking = false;
                }
                if (this.puking) {
                    this.clearActivePotions();
                    this.puking = false;
                }
            }
            if (timeWolfIsShaking > 0.35F) {
                if (this.smoking) {
                    this.playTameEffect(false); //generates smoke particles while shaking
                }
                if (!this.puking) {
                    float var1 = (float) this.boundingBox.minY;
                    int var2 = (int) (MathHelper.sin((timeWolfIsShaking - 0.4F) * (float) Math.PI) * 7);

                    for (int var3 = 0; var3 < var2; ++var3) {
                        float var4 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width * 0.5F;
                        float var5 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width * 0.5F;
                        this.worldObj.spawnParticle("splash", this.posX + (double) var4, (double) (var1 + 0.8F), this.posZ + (double) var5, this.motionX, this.motionY, this.motionZ);
                    }
                }
            }
        }
        super.onUpdate();
    }

    /**
     * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
     */
    @Override
    public boolean interact(EntityPlayer player) {
        super.interact(player);

        ItemStack stack = player.inventory.getCurrentItem();

        if (this.isTamed()) {
            if (stack != null) {
                if (stack.getItem() instanceof ItemFood) {
                    ItemFood foodItem = (ItemFood) stack.getItem();
                    //checks static FurnaceRecipes for cooked version of held food
                    ItemStack cookedStack = FurnaceRecipes.smelting().getSmeltingResult(stack);
                    if (cookedStack != null && cookedStack.getItem() instanceof ItemFood) {
                        ItemFood foodCooked = (ItemFood) cookedStack.getItem();
                        if ((float) foodCooked.func_150905_g(cookedStack) > (float) foodItem.func_150905_g(stack)) {
                            foodItem = (ItemFood) cookedStack.getItem(); //sets ID to cooked version of food if it exists
                        }
                    }

                    if ((stack.getItem().equals(Items.cooked_fished) || stack.getItem().equals(Items.fish)) && this.dataWatcher.getWatchableObjectFloat(18) < 20.0F) {
                        if (!player.capabilities.isCreativeMode) {
                            --stack.stackSize;
                        }

                        this.heal((float) foodItem.func_150905_g(stack));
                        this.playTameEffect(false); //generates smoke particles on feeding

                        if (stack.stackSize <= 0) {
                            player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack) null);
                        }
                        return true;
                    }
                } else if (stack.getItem() instanceof ItemDogTag) {
                    return false;
                }
            }
            //removed !isBreedingItem() check
            if (player.getCommandSenderName().equalsIgnoreCase(this.getOwnerName())) {
                //calls super.interact for breeding
                if (stack != null && isBreedingItem(stack) && getGrowingAge() == 0) {
                    this.aiSit.setSitting(false);
                }

                player.setRevengeTarget((EntityLivingBase) null);
                return true;
            }
            return false;
        }

        return false;
    }

    /**
     * changed to use treats for breeding
     */
    @Override
    public boolean isBreedingItem(ItemStack stack) {
        if (stack == null) {
            return false;
        }

        if (SWConfiguration.customBreeding) {
            return stack.getItem().equals(SWItems.dogTreat);
        } else {
            return super.isBreedingItem(stack);
        }
    }

    @Override
    public EntityWolf createChild(EntityAgeable entity) {
        SophisticatedWolf wolf = new SophisticatedWolf(this.worldObj);
        wolf.updateSpecies(this.getSpecies()); //setting species to same as parent that spawned it
        String name = this.getOwnerName();

        if (name != null && name.trim().length() > 0) {
            wolf.setOwner(name);
            wolf.setTamed(true);
        }
        wolf.setHealth(20); //setting puppy health
        return wolf;
    }

    /**
     * Returns true if the mob is currently able to mate with the specified mob.
     */
    @Override
    public boolean canMateWith(EntityAnimal animal) {
        if (animal == this || !this.isTamed() || !(animal instanceof SophisticatedWolf)) {
            return false;
        } else {
            SophisticatedWolf wolf = (SophisticatedWolf) animal;
            return !wolf.isTamed() ? false : (wolf.isSitting() ? false : this.isInLove() && wolf.isInLove());
        }
    }

    /**
     * Determines if an entity can be despawned, used on idle far away entities
     */
    @Override
    protected boolean canDespawn() {
        //Changed for respawning wolves
        return super.canDespawn() && (SWConfiguration.respawningWolves || this.isAngry());
    }

    //Custom functions below here
    public boolean isFavoriteFood(ItemStack itemstack) {
        if (itemstack == null) {
            return false;
        }
        if (itemstack.getItem() instanceof ItemFood) {
            return this.dataWatcher.getWatchableObjectFloat(18) < 20.0F && (((ItemFood) itemstack.getItem()).isWolfsFavoriteMeat() || itemstack.getItem().equals(Items.fish) || itemstack.getItem().equals(Items.cooked_fished));
        } else {
            return itemstack.getItem().equals(SWItems.dogTreat) && getGrowingAge() == 0;
        }
    }

    //checks for creepers nearby
    public boolean CreeperAlert() {
        List list = this.worldObj.getEntitiesWithinAABB(EntityCreeper.class, this.boundingBox.expand(16D, 4D, 16D));
        if (!list.isEmpty()) {
            this.playSound("mob.wolf.growl", getSoundVolume(), (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
            return true;
        } else {
            return false;
        }
    }

    //reduces wolf spawning rate (necessary as monster spawn rate is called faster than animal)
    @Override
    public boolean getCanSpawnHere() {
        if (SWConfiguration.respawningWolves) {
            int k1 = this.rand.nextInt(3);
            return k1 == 0 && super.getCanSpawnHere();
        }
        return super.getCanSpawnHere();
    }

    /*
     * determine species based on biome
     */
    @Override
    public int setSpecies() {
        int wi = MathHelper.floor_double(this.posX);
        int wk = MathHelper.floor_double(this.posZ);
        WorldChunkManager worldchunkmanager = this.worldObj.getWorldChunkManager();
        if (worldchunkmanager != null) {
            BiomeGenBase biomegenbase = worldchunkmanager.getBiomeGenAt(wi, wk);
            if (biomegenbase instanceof BiomeGenForest) {
                if (this.rand.nextInt(7) == 0) {
                    return 4;
                }
                return 2;
            }
            if (this.rand.nextInt(7) == 0) {
                return 3;
            }
        }
        return 1;
    }

    /*
     * reads data for species
     */
    @Override
    public int getSpecies() {
        return this.dataWatcher.getWatchableObjectByte(22);
    }

    /*
     * modifies data for species
     */
    @Override
    public void updateSpecies(int species) {
        this.dataWatcher.updateObject(22, Byte.valueOf((byte) species));
    }


    // Reflection
    public boolean isShaking() {
        Object value = getPrivateFieldValue(isDebugMode ? "isShaking" : "field_70925_g");
        if (value == null) {
            if (SWConfiguration.logWolfErrors) {
                SophisticatedWolvesMod.logger.log(Level.ERROR, "isShaking is null!!!");
            }
            return false;
        } else {
            return (Boolean) value;
        }
    }

    public void setShaking(boolean isShaking) {
        setPrivateFieldValue(isDebugMode ? "isShaking" : "field_70925_g", isShaking);
    }

    public boolean getField_70928_h() {
        Object value = getPrivateFieldValue("field_70928_h");
        if (value == null) {
            if (SWConfiguration.logWolfErrors) {
                SophisticatedWolvesMod.logger.log(Level.ERROR, "field_70928_h is null!!!");
            }
            return false;
        } else {
            return (Boolean) value;
        }
    }

    public float timeWolfIsShaking() {
        Object value = getPrivateFieldValue(isDebugMode ? "timeWolfIsShaking" : "field_70929_i");
        if (value == null) {
            if (SWConfiguration.logWolfErrors) {
                SophisticatedWolvesMod.logger.log(Level.ERROR, "timeWolfIsShaking is null!!!");
            }
            return 0;
        } else {
            return (Float) value;
        }
    }

    public void setTimeWolfIsShaking(float time) {
        setPrivateFieldValue(isDebugMode ? "timeWolfIsShaking" : "field_70929_i", time);
    }

    public float prevTimeWolfIsShaking() {
        Object value = getPrivateFieldValue(isDebugMode ? "prevTimeWolfIsShaking" : "field_70927_j");
        if (value == null) {
            if (SWConfiguration.logWolfErrors) {
                SophisticatedWolvesMod.logger.log(Level.ERROR, "prevTimeWolfIsShaking is null!!!");
            }
            return 0;
        } else {
            return (Float) value;
        }
    }

    public void setPrevTimeWolfIsShaking(float time) {
        setPrivateFieldValue(isDebugMode ? "prevTimeWolfIsShaking" : "field_70927_j", time);
    }

    private Object getPrivateFieldValue(String fieldName) {
        Field field = getField(this.getClass().getSuperclass(), fieldName);
        if (field == null) {
            if (SWConfiguration.logWolfErrors) {
                SophisticatedWolvesMod.logger.log(Level.ERROR, "Can't get private field " + fieldName);
            }
            return null;
        }
        try {
            field.setAccessible(true);
            return field.get(this);
        } catch (IllegalAccessException e) {
            if (SWConfiguration.logWolfErrors) {
                SophisticatedWolvesMod.logger.log(Level.ERROR, "IllegalAccessException " + fieldName);
            }
            return null;
        }
    }

    private void setPrivateFieldValue(String fieldName, Object value) {
        Field field = getField(this.getClass().getSuperclass(), fieldName);
        if (field == null) {
            if (SWConfiguration.logWolfErrors) {
                SophisticatedWolvesMod.logger.log(Level.ERROR, "Can't set private field " + fieldName);
            }
            return;
        }
        try {
            field.setAccessible(true);
            field.set(this, value);
        } catch (IllegalAccessException e) {
            if (SWConfiguration.logWolfErrors) {
                SophisticatedWolvesMod.logger.log(Level.ERROR, "IllegalAccessException " + fieldName);
            }
        }
    }

    private static Field getField(Class clazz, String fieldName) {
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            Class superClass = clazz.getSuperclass();
            if (superClass == null) {
                if (SWConfiguration.logWolfErrors) {
                    SophisticatedWolvesMod.logger.log(Level.ERROR, "Can't get field " + fieldName);
                }
                return null;
            } else {
                return getField(superClass, fieldName);
            }
        }
    }


}

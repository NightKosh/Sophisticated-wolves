package sophisticated_wolves.entity;


import net.minecraft.entity.Entity;
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
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenForest;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sophisticated_wolves.SWConfiguration;
import sophisticated_wolves.SWItems;
import sophisticated_wolves.api.ISophisticatedWolf;
import sophisticated_wolves.entity.ai.*;
import sophisticated_wolves.item.ItemDogTag;

import java.util.Iterator;
import java.util.List;

/**
 * Sophisticated Wolves
 *
 * @author metroidfood
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class EntitySophisticatedWolf extends EntityWolf implements ISophisticatedWolf {

    //New Sophisticated Wolves variables
    public boolean puking;
    protected boolean isDrowning = false;
    protected int drownCount = 0;

    public EntitySophisticatedWolf(World world) {
        super(world);

        Iterator taskIterator = this.tasks.taskEntries.iterator();
        while (taskIterator.hasNext()) {
            boolean removeTask = false;
            EntityAIBase task = ((EntityAITasks.EntityAITaskEntry) taskIterator.next()).action;
            if (task instanceof EntityAISit) {
                removeTask = true;
            } else if (task instanceof EntityAILeapAtTarget) {
                removeTask = true;
            } else if (task instanceof EntityAIAttackOnCollide) {
                removeTask = true;
            } else if (task instanceof EntityAIFollowOwner) {
                removeTask = true;
            } else if (task instanceof EntityAIMate) {
                removeTask = true;
            } else if (task instanceof EntityAIWander) {
                removeTask = true;
            } else if (task instanceof EntityAIBeg) {
                removeTask = true;
            } else if (task instanceof EntityAIWatchClosest) {
                removeTask = true;
            } else if (task instanceof EntityAILookIdle) {
                removeTask = true;
            }
            if (removeTask) {
                taskIterator.remove();
            }
        }

        this.tasks.addTask(3, new EntityAIAvoidCreeper(this, 8, 6, 3, 1, 1.4)); //new behavior
        this.tasks.addTask(5, this.aiSit);
        this.tasks.addTask(7, new EntityAIShake(this)); //behavior for shaking
        this.tasks.addTask(10, new EntityAIAttackCancel(this)); //new behavior
        this.tasks.addTask(15, new EntityAILeapAtTarget(this, 0.4F));
        this.tasks.addTask(20, new EntityAIAttackOnCollide(this, 1, true));
        this.tasks.addTask(22, new EntityAIMoveCancel(this, 6));
        this.tasks.addTask(25, new EntityAINewFollowOwner(this, 1, 6, 2)); //changed 10.0F to 6.0F, mod class
        this.tasks.addTask(27, new EntityAIAvoidFire(this, 1, 1.4)); //new behavior
        this.tasks.addTask(28, new EntityAITeleportAtDrowning(this)); //new behavior
        this.tasks.addTask(30, new EntityAIMate(this, 1));
        this.tasks.addTask(40, new EntityAIWander(this, 1)); //changed order with Beg
        this.tasks.addTask(35, new EntityAINewBeg(this, 8)); //changed order with Wander, mod class
        this.tasks.addTask(45, new EntityAIWatchClosest(this, EntityPlayer.class, 8));
        this.tasks.addTask(50, new EntityAILookIdle(this));

        taskIterator = this.targetTasks.taskEntries.iterator();
        while (taskIterator.hasNext()) {
            boolean removeTask = false;
            EntityAIBase task = ((EntityAITasks.EntityAITaskEntry) taskIterator.next()).action;
            if (task instanceof EntityAIOwnerHurtByTarget) {
                removeTask = true;
            } else if (task instanceof EntityAIOwnerHurtTarget) {
                removeTask = true;
            }
            if (removeTask) {
                taskIterator.remove();
            }
        }
        this.targetTasks.addTask(1, new EntityAINewOwnerHurtByTarget(this)); //mod class
        this.targetTasks.addTask(2, new EntityAINewOwnerHurtTarget(this)); //mod class

        fireResistance = 5; //modified from default value
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(22, new Byte((byte) 0)); //species
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);

        nbtTagCompound.setInteger("Species", this.getSpecies());
    }

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
            if (this.isTamed() && this.getHealth() < 10) {
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

    @Override
    @SideOnly(Side.CLIENT)
    public float getTailRotation() {
        if (this.isAngry()) {
            return 1.5393804F;
        } else if (this.isTamed()) {
            return (0.55F - (20 - this.getHealth()) * 0.02F) * (float) Math.PI;
        } else {
            return (float) Math.PI / 5F;
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
        if (!this.isWet &&
                ((this.isBurning() && !this.worldObj.func_147470_e(this.getEntityBoundingBox().contract(0.001, 0.001, 0.001))) ||
                        ((this.isPotionActive(Potion.poison) || this.isPotionActive(Potion.wither))))) {
            this.isShaking = true;
            this.timeWolfIsShaking = 0;
            this.prevTimeWolfIsShaking = 0;
        }

        float timeWolfIsShaking = this.timeWolfIsShaking;
        if (!this.isWet() && this.isWet) {
            if (timeWolfIsShaking == 0) {
                //checks if burning/poisoned/wet and sets variables
                if (this.isBurning()) {
                    this.playSound("mob.wolf.shake", this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1);
                } else if (this.isPotionActive(Potion.poison) || this.isPotionActive(Potion.wither)) {
                    this.puking = true;
                } else {
                    this.playSound("mob.wolf.shake", this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1);
                }
            }

            if (this.prevTimeWolfIsShaking >= 1.95F) {
                //extinguishing added
                if (this.isBurning()) {
                    this.extinguish();
                    this.playSound("random.fizz", 0.7F, 1.6F + (rand.nextFloat() - rand.nextFloat()) * 0.4F);
                }
                if (this.puking) {
                    this.clearActivePotions();
                    this.puking = false;
                }
            }
            if (timeWolfIsShaking > 0.35F) {
                if (this.isBurning()) {
                    this.playTameEffect(false); //generates smoke particles while shaking
                }
                if (!this.puking) {
                    float var1 = (float) this.getEntityBoundingBox().minY;
                    int var2 = (int) (MathHelper.sin((timeWolfIsShaking - 0.4F) * (float) Math.PI) * 7);

                    for (int var3 = 0; var3 < var2; ++var3) {
                        float var4 = (this.rand.nextFloat() * 2 - 1) * this.width * 0.5F;
                        float var5 = (this.rand.nextFloat() * 2 - 1) * this.width * 0.5F;
                        this.worldObj.spawnParticle(EnumParticleTypes.WATER_SPLASH, this.posX + var4, var1 + 0.8F, this.posZ + var5, this.motionX, this.motionY, this.motionZ);
                    }
                }
            }
        }
        if (this.isDrowning) {
            if (this.drownCount > 0) {
                this.drownCount--;
            } else {
                this.isDrowning = false;
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
                    ItemStack cookedStack = FurnaceRecipes.instance().getSmeltingResult(stack);
                    if (cookedStack != null && cookedStack.getItem() instanceof ItemFood) {
                        ItemFood foodCooked = (ItemFood) cookedStack.getItem();
                        if ((float) foodCooked.getHealAmount(cookedStack) > (float) foodItem.getHealAmount(stack)) {
                            foodItem = (ItemFood) cookedStack.getItem(); //sets ID to cooked version of food if it exists
                        }
                    }

                    if ((stack.getItem().equals(Items.cooked_fish) || stack.getItem().equals(Items.fish)) && this.getHealth() < 20) {
                        if (!player.capabilities.isCreativeMode) {
                            --stack.stackSize;
                        }

                        this.heal((float) foodItem.getHealAmount(stack));
                        this.playTameEffect(false); //generates smoke particles on feeding

                        if (stack.stackSize <= 0) {
                            player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack) null);
                        }
                        this.aiSit.setSitting(this.isSitting());
                        return true;
                    }
                } else if (stack.getItem() instanceof ItemDogTag) {
                    this.aiSit.setSitting(this.isSitting());
                    return false;
                }
            }
            //removed !isBreedingItem() check
            //TODO
            if (this.isOwner(player) && !this.worldObj.isRemote) {
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
        EntitySophisticatedWolf wolf = new EntitySophisticatedWolf(this.worldObj);
        wolf.updateSpecies(this.getSpecies()); //setting species to same as parent that spawned it
        String ownerId = this.getOwnerId();

        if (ownerId != null && ownerId.trim().length() > 0) {
            wolf.setOwnerId(ownerId);
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
        if (animal == this || !this.isTamed() || !(animal instanceof EntitySophisticatedWolf)) {
            return false;
        } else {
            EntitySophisticatedWolf wolf = (EntitySophisticatedWolf) animal;
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
            return this.getHealth() < 20 && (((ItemFood) itemstack.getItem()).isWolfsFavoriteMeat() ||
                    itemstack.getItem().equals(Items.fish) || itemstack.getItem().equals(Items.cooked_fish));
        } else {
            return itemstack.getItem().equals(SWItems.dogTreat) && getGrowingAge() == 0;
        }
    }

    //checks for creepers nearby
    public boolean CreeperAlert() {
        List list = this.worldObj.getEntitiesWithinAABB(EntityCreeper.class, this.getEntityBoundingBox().expand(16, 4, 16));
        if (!list.isEmpty()) {
            this.playSound("mob.wolf.growl", getSoundVolume(), (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1);
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
        WorldChunkManager worldchunkmanager = this.worldObj.getWorldChunkManager();
        if (worldchunkmanager != null) {
            BiomeGenBase biomegenbase = this.worldObj.getBiomeGenForCoords(new BlockPos(this));
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
        this.dataWatcher.updateObject(22, (byte) species);
    }

    @Override
    public void onDeath(DamageSource damageSource) {
        if (isTamed() && this.getOwner() != null) {
            ((EntityPlayer) this.getOwner()).addChatComponentMessage(this.getCombatTracker().getDeathMessage());
        }
        super.onDeath(damageSource);
    }

    @Override
    public boolean attackEntityFrom(DamageSource damageSource, float p_70097_2_) {
        if ((damageSource.getEntity() != null && damageSource.getEntity().equals(this.getOwner()) && !damageSource.getEntity().isSneaking()) ||
                (SWConfiguration.immuneToCacti && damageSource.equals(DamageSource.cactus))) {
            return false;
        } else {
            if (damageSource.equals(DamageSource.drown)) {
                this.isDrowning = true;
                this.drownCount = 30;
            }
            return super.attackEntityFrom(damageSource, p_70097_2_);
        }
    }

    @Override
    public void applyEntityCollision(Entity entity) {
        if (!(entity instanceof EntitySophisticatedWolf)) {
            super.applyEntityCollision(entity);
        }
    }

    public boolean isDrowning() {
        return isDrowning;
    }

    public void setDrowning(boolean isDrowning) {
        this.isDrowning = isDrowning;
        if (isDrowning) {
            this.drownCount = 30;
        }
    }
}

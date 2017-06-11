package sophisticated_wolves.entity;


import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeForest;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sophisticated_wolves.SWConfiguration;
import sophisticated_wolves.SWItems;
import sophisticated_wolves.api.AEntitySophisticatedWolf;
import sophisticated_wolves.api.EnumWolfSpecies;
import sophisticated_wolves.entity.ai.*;
import sophisticated_wolves.item.ItemDogTag;
import sophisticated_wolves.item.pet_carrier.ItemPetCarrier;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * Sophisticated Wolves
 *
 * @author metroidfood
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class EntitySophisticatedWolf extends AEntitySophisticatedWolf {

    private static final DataParameter<Integer> WOLF_SPECIES = EntityDataManager.createKey(EntityWolf.class, DataSerializers.VARINT);
    private static final int POTION_POISON_ID = 19;
    private static final int POTION_WITHER_ID = 20;

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
            } else if (task instanceof EntityAIAttackMelee) {
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
        this.tasks.addTask(5, this.aiSit = new EntityAISit(this));
        this.tasks.addTask(7, new EntityAIShake(this)); //behavior for shaking
        this.tasks.addTask(10, new EntityAIAttackCancel(this)); //new behavior
        this.tasks.addTask(15, new EntityAILeapAtTarget(this, 0.4F));
        this.tasks.addTask(20, new EntityAIAttackMelee(this, 1, true));
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

        this.updateSpecies(this.getSpeciesByBiome());
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(WOLF_SPECIES, 0);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);

        nbtTagCompound.setInteger("Species", this.getSpecies().ordinal());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);

        this.updateSpecies(EnumWolfSpecies.getSpeciesByNum(nbtTagCompound.getInteger("Species")));
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    @Override
    protected SoundEvent getAmbientSound() {
        if (this.isAngry()) {
            return SoundEvents.ENTITY_WOLF_GROWL;
        }

        //Growls if creeper is near
        if (this.isTamed() && this.CreeperAlert()) {
            return SoundEvents.ENTITY_WOLF_GROWL;
        }

        if (this.rand.nextInt(3) == 0 && !this.CreeperAlert()) {
            if (this.isTamed() && this.getHealth() < 10) {
                return SoundEvents.ENTITY_WOLF_WHINE;
            } else {
                return SoundEvents.ENTITY_WOLF_PANT;
            }
        } else {
            //sitting wolves will only bark 1/4 of the time
            if (!this.isSitting()) {
                return SoundEvents.ENTITY_WOLF_AMBIENT;
            } else {
                if (this.rand.nextInt(3) == 0) {
                    return SoundEvents.ENTITY_WOLF_AMBIENT;
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
    }

    /**
     * Called to update the entity's position/logic.
     */
    @Override
    public void onUpdate() {        //Checks if wolf is burning and not currently standing in fire or if wolf is poison
        if (!this.isWet && //isWolfWet method is client side!
                ((this.isBurning() && !this.world.isFlammableWithin(this.getEntityBoundingBox().contract(0.001))) ||
                        (this.isPotionActive(Potion.REGISTRY.getObjectById(POTION_POISON_ID)) ||
                                this.isPotionActive(Potion.REGISTRY.getObjectById(POTION_WITHER_ID))))) {
            this.isShaking = true;
            this.timeWolfIsShaking = 0;
            this.prevTimeWolfIsShaking = 0;
            this.isWet = true;
        }

        if (!this.isWet() && this.isWet) {//isWolfWet method is client side!
            if (this.timeWolfIsShaking == 0) {
                //checks if burning/poisoned/wet and sets variables
                if (this.isBurning()) {
                    this.playSound(SoundEvents.ENTITY_WOLF_SHAKE, this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1);
                } else if (this.isPotionActive(Potion.REGISTRY.getObjectById(POTION_POISON_ID)) || this.isPotionActive(Potion.REGISTRY.getObjectById(POTION_WITHER_ID))) {
                    this.puking = true;
                } else {
                    this.playSound(SoundEvents.ENTITY_WOLF_SHAKE, this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1);
                }
            }

            if (this.prevTimeWolfIsShaking >= 1.95) {
                //extinguishing added
                if (this.isBurning()) {
                    this.extinguish();
                    this.playSound(SoundEvents.BLOCK_FIRE_EXTINGUISH, 0.7F, 1.6F + (rand.nextFloat() - rand.nextFloat()) * 0.4F);
                }
                if (this.puking) {
                    this.clearActivePotions();
                    this.puking = false;
                }
            }
            if (this.timeWolfIsShaking > 0.35) {
                if (this.isBurning()) {
                    this.playTameEffect(false); //generates smoke particles while shaking
                }
                if (!this.puking) {
                    float var1 = (float) this.getEntityBoundingBox().minY;
                    int var2 = (int) (MathHelper.sin((this.timeWolfIsShaking - 0.4F) * (float) Math.PI) * 7);

                    for (int var3 = 0; var3 < var2; ++var3) {
                        float var4 = (this.rand.nextFloat() * 2 - 1) * this.width * 0.5F;
                        float var5 = (this.rand.nextFloat() * 2 - 1) * this.width * 0.5F;
                        this.world.spawnParticle(EnumParticleTypes.WATER_SPLASH, this.posX + var4, var1 + 0.8, this.posZ + var5, this.motionX, this.motionY, this.motionZ);
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

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);

        if (this.isTamed()) {
            if (stack != null) {
                if (stack.getItem() instanceof ItemFood && this.getHealth() < 20) {
                    ItemFood foodItem = (ItemFood) stack.getItem();

                    //checks static FurnaceRecipes for cooked version of held food
                    ItemStack cookedStack = FurnaceRecipes.instance().getSmeltingResult(stack);
                    if (cookedStack != null && cookedStack.getItem() instanceof ItemFood) {
                        ItemFood foodCooked = (ItemFood) cookedStack.getItem();
                        if (foodCooked.getHealAmount(cookedStack) > foodItem.getHealAmount(stack)) {
                            foodItem = (ItemFood) cookedStack.getItem(); //sets ID to cooked version of food if it exists
                        }
                    }

                    if (foodItem.isWolfsFavoriteMeat()) {
                        if (!player.capabilities.isCreativeMode) {
                            stack.shrink(1);
                        }

                        this.heal((float) foodItem.getHealAmount(stack));
                        this.aiSit.setSitting(this.isSitting());
                        return true;
                    }

                    if ((stack.getItem().equals(Items.COOKED_FISH) || stack.getItem().equals(Items.FISH))) {
                        if (!player.capabilities.isCreativeMode) {
                            stack.shrink(1);
                        }

                        this.heal(foodItem.getHealAmount(stack));
                        this.playTameEffect(false); //generates smoke particles on feeding

                        if (stack.getCount() <= 0) {
                            player.inventory.removeStackFromSlot(player.inventory.currentItem);
                        }
                        this.aiSit.setSitting(this.isSitting());
                        return true;
                    }
                } else if (stack.getItem() instanceof ItemDogTag || stack.getItem() instanceof ItemPetCarrier) {
                    this.aiSit.setSitting(this.isSitting());
                    return false;
                }
            }
        }

        return super.processInteract(player, hand);
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
        EntitySophisticatedWolf wolf = new EntitySophisticatedWolf(this.world);
        wolf.updateSpecies(this.getSpecies()); //setting species to same as parent that spawned it
        UUID ownerId = this.getOwnerId();

        if (ownerId != null) {
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
            return wolf.isTamed() && (!wolf.isSitting() && this.isInLove() && wolf.isInLove());
        }
    }

    /**
     * Determines if an entity can be despawned, used on idle far away entities
     */
    @Override
    protected boolean canDespawn() {
        return !this.isTamed() && SWConfiguration.respawningWolves && this.ticksExisted > 5000;
    }

    //Custom functions below here
    public boolean isFavoriteFood(ItemStack itemstack) {
        if (itemstack == null) {
            return false;
        }
        if (itemstack.getItem() instanceof ItemFood) {
            return this.getHealth() < 20 && (((ItemFood) itemstack.getItem()).isWolfsFavoriteMeat() ||
                    itemstack.getItem().equals(Items.FISH) || itemstack.getItem().equals(Items.COOKED_FISH));
        } else {
            return itemstack.getItem().equals(SWItems.dogTreat) && getGrowingAge() == 0;
        }
    }

    //checks for creepers nearby
    public boolean CreeperAlert() {
        List list = this.world.getEntitiesWithinAABB(EntityCreeper.class, this.getEntityBoundingBox().expand(16, 4, 16));
        if (!list.isEmpty()) {
            this.playSound(SoundEvents.ENTITY_WOLF_GROWL, getSoundVolume(), (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1);
            return true;
        } else {
            return false;
        }
    }

    //reduces wolf spawning rate (necessary as monster spawn rate is called faster than animal)
    @Override
    public boolean getCanSpawnHere() {
        BlockPos blockpos = new BlockPos(MathHelper.floor(this.posX), MathHelper.floor(this.getEntityBoundingBox().minY), MathHelper.floor(this.posZ));
        return this.world.getLight(blockpos) < 5 && this.rand.nextInt(3) == 0 &&
                this.world.getBlockState(blockpos.down()).getBlock() == this.spawnableBlock &&
                this.world.getBlockState((new BlockPos(this)).down()).canEntitySpawn(this) &&
                this.getBlockPathWeight(new BlockPos(this.posX, this.getEntityBoundingBox().minY, this.posZ)) >= 0.0F;
    }

    /*
     * determine species based on biome
     */
    @Override
    public EnumWolfSpecies getSpeciesByBiome() {
        Biome biome = this.world.getBiomeForCoordsBody(new BlockPos(this));
        if (biome instanceof BiomeForest) {
            if (this.rand.nextInt(7) == 0) {
                return EnumWolfSpecies.BROWN;
            }
            return EnumWolfSpecies.FOREST;
        } else if (this.rand.nextInt(7) == 0) {
            return EnumWolfSpecies.BLACK;
        } else {
            return EnumWolfSpecies.VANILLA;
        }
    }

    /*
         * reads data for species
         */
    @Override
    public EnumWolfSpecies getSpecies() {
        return EnumWolfSpecies.values()[this.dataManager.get(WOLF_SPECIES)];
    }

    /*
     * modifies data for species
     */
    @Override
    public void updateSpecies(EnumWolfSpecies species) {
        this.dataManager.set(WOLF_SPECIES, species.ordinal());
    }

    @Override
    public void onDeath(DamageSource damageSource) {
        if (isTamed() && this.getOwner() != null) {
            ((EntityPlayer) this.getOwner()).sendMessage(this.getCombatTracker().getDeathMessage());
        }
        super.onDeath(damageSource);
    }

    @Override
    public boolean attackEntityFrom(DamageSource damageSource, float amount) {
        if ((damageSource.getEntity() != null && damageSource.getEntity().equals(this.getOwner()) && !damageSource.getEntity().isSneaking()) ||
                (SWConfiguration.immuneToCacti && damageSource.equals(DamageSource.CACTUS))) {
            return false;
        } else {
            if (damageSource.equals(DamageSource.DROWN)) {
                this.isDrowning = true;
                this.drownCount = 30;
            }
            return super.attackEntityFrom(damageSource, amount);
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

    @Override
    protected int getFireImmuneTicks() {
        return 5;
    }

    @Override
    public boolean isWet() {
        return super.isWet() || this.world.handleMaterialAcceleration(this.getEntityBoundingBox().expand(0, -0.25, 0).contract(0.001D), Material.WATER, this);
    }
}

package sophisticated_wolves.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NonTameRandomTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.Tags;
import sophisticated_wolves.FoodHelper;
import sophisticated_wolves.core.SWConfiguration;
import sophisticated_wolves.api.AEntitySophisticatedWolf;
import sophisticated_wolves.api.EnumWolfSpecies;
import sophisticated_wolves.core.SWEntities;
import sophisticated_wolves.core.SWItems;
import sophisticated_wolves.entity.ai.*;
import sophisticated_wolves.gui.WolfFoodConfigScreen;
import sophisticated_wolves.item.ItemDogTag;
import sophisticated_wolves.item.pet_carrier.ItemPetCarrier;

import javax.annotation.Nullable;

/**
 * Sophisticated Wolves
 *
 * @author metroidfood
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class SophisticatedWolf extends AEntitySophisticatedWolf {

    public static final int DEFAULT_WILD_WOLF_HEALTH = 8;
    public static final int DEFAULT_TAMED_WOLF_HEALTH = 30;

    private static final EntityDataAccessor<Integer> WOLF_SPECIES =
            SynchedEntityData.defineId(SophisticatedWolf.class, EntityDataSerializers.INT);

    protected boolean rottenMeatAndBones;
    protected boolean rawMeat;
    protected boolean rawFish;
    protected boolean specialFish;
    protected boolean cookedMeat;
    protected boolean cookedFish;
    protected boolean anyFood;

    //New Sophisticated Wolves variables
    public boolean puking;
    protected boolean isDrowning = false;
    protected int drownCount = 0;

    public SophisticatedWolf(EntityType<? extends Wolf> entityType, Level level) {
        super(entityType, level);
    }


    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(1, new Wolf.WolfPanicGoal(1.5D));
        this.goalSelector.addGoal(2, new AvoidCreeperGoal(this, 8, 6, 3, 1, 1.4)); //new behavior
        this.goalSelector.addGoal(5, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(7, new ShakeGoal(this)); //new behavior
        this.goalSelector.addGoal(8, new AttackCancelGoal(this)); //new behavior
        this.goalSelector.addGoal(10, new Wolf.WolfAvoidEntityGoal<>(this, Llama.class, 24, 1.5, 1.5));
        this.goalSelector.addGoal(15, new LeapAtTargetGoal(this, 0.4F));
        this.goalSelector.addGoal(20, new MeleeAttackGoal(this, 1, true));
        this.goalSelector.addGoal(22, new MoveCancelGoal(this, 6)); //new behavior
        this.goalSelector.addGoal(25, new SWFollowOwnerGoal(this, 1, 6, 2)); //new behavior. changed 10 to 6
        this.goalSelector.addGoal(27, new AvoidFireGoal(this, 1, 1.4)); //new behavior
        this.goalSelector.addGoal(28, new TeleportAtDrowningGoal(this)); //new behavior
        this.goalSelector.addGoal(29, new BreedGoal(this, 1));
        this.goalSelector.addGoal(30, new WaterAvoidingRandomStrollGoal(this, 1));
        this.goalSelector.addGoal(31, new FeedFromBowlGoal(this)); //new behavior
        this.goalSelector.addGoal(32, new FeedGoal(this)); //new behavior
        this.goalSelector.addGoal(35, new SWBegGoal(this, 8)); //changed behavior
        this.goalSelector.addGoal(45, new LookAtPlayerGoal(this, Player.class, 8));
        this.goalSelector.addGoal(50, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new SWOwnerHurtByTargetGoal(this));//changed behavior
        this.targetSelector.addGoal(2, new SWOwnerHurtTargetGoal(this));//changed behavior
        this.targetSelector.addGoal(3, (new HurtByTargetGoal(this)).setAlertOthers());
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::isAngryAt));
        if (SWConfiguration.ATTACK_ANIMALS.get()) {
            this.targetSelector.addGoal(5, new NonTameRandomTargetGoal<>(this, Animal.class, false, PREY_SELECTOR));
            this.targetSelector.addGoal(6, new NonTameRandomTargetGoal<>(this, Turtle.class, false, Turtle.BABY_ON_LAND_SELECTOR));
        }
        if (SWConfiguration.ATTACK_SKELETONS.get()) {
            this.targetSelector.addGoal(7, new NearestAttackableTargetGoal<>(this, AbstractSkeleton.class, false));
        }
        this.targetSelector.addGoal(8, new ResetUniversalAngerTargetGoal<>(this, true));
    }

    public static AttributeSupplier createAttributeSupplier() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.4)
                .add(Attributes.MAX_HEALTH, SophisticatedWolf.DEFAULT_WILD_WOLF_HEALTH)
                .add(Attributes.ATTACK_DAMAGE, 3)
                .build();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(WOLF_SPECIES, 0);
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(
            ServerLevelAccessor accessor, DifficultyInstance difficulty,
            MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag tag) {
        this.setWolfSpeciesByBiome();
        return super.finalizeSpawn(accessor, difficulty, mobSpawnType, spawnGroupData, tag);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);

        tag.putInt("Species", this.getSpecies().ordinal());

        var allowedFoodTag = new CompoundTag();
        allowedFoodTag.putBoolean("RottenMeatAndBones", this.rottenMeatAndBones);
        allowedFoodTag.putBoolean("RawMeat", this.rawMeat);
        allowedFoodTag.putBoolean("RawFish", this.rawFish);
        allowedFoodTag.putBoolean("SpecialFish", this.specialFish);
        allowedFoodTag.putBoolean("CookedMeat", this.cookedMeat);
        allowedFoodTag.putBoolean("CookedFish", this.cookedFish);
        tag.put("AllowedFood", allowedFoodTag);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);

        this.updateSpecies(EnumWolfSpecies.getSpeciesByNum(tag.getInt("Species")));

        if (tag.contains("AllowedFood")) {
            var allowedFoodTag = tag.getCompound("AllowedFood");
            if (allowedFoodTag.contains("RottenMeatAndBones")) {
                this.rottenMeatAndBones = allowedFoodTag.getBoolean("RottenMeatAndBones");
            }
            if (allowedFoodTag.contains("RawMeat")) {
                this.rawMeat = allowedFoodTag.getBoolean("RawMeat");
            }
            if (allowedFoodTag.contains("RawFish")) {
                this.rawFish = allowedFoodTag.getBoolean("RawFish");
            }
            if (allowedFoodTag.contains("SpecialFish")) {
                this.specialFish = allowedFoodTag.getBoolean("SpecialFish");
            }
            if (allowedFoodTag.contains("CookedMeat")) {
                this.cookedMeat = allowedFoodTag.getBoolean("CookedMeat");
            }
            if (allowedFoodTag.contains("CookedFish")) {
                this.cookedFish = allowedFoodTag.getBoolean("CookedFish");
            }
        }
        updateFood();
    }

    @Override
    protected SoundEvent getAmbientSound() {
        if (this.isAngry()) {
            return SoundEvents.WOLF_GROWL;
        }

        //Growls if creeper is near
        if (this.isTame() && this.creeperAlert()) {
            return SoundEvents.WOLF_GROWL;
        }

        if (this.getRandom().nextInt(3) == 0 && !this.creeperAlert()) {
            if (this.isTame() && this.getHealth() < SWConfiguration.WOLVES_HEALTH_TAMED.get() / 2F) {
                return SoundEvents.WOLF_WHINE;
            } else {
                return SoundEvents.WOLF_PANT;
            }
        } else {
            //sitting wolves will only bark 1/4 of the time
            if (!this.isInSittingPose()) {
                return SoundEvents.WOLF_AMBIENT;
            } else {
                if (this.getRandom().nextInt(3) == 0) {
                    return SoundEvents.WOLF_AMBIENT;
                } else {
                    return null;
                }
            }
        }
    }

    @Override
    public float getTailAngle() {
        if (this.isAngry()) {
            return 1.5393804F;
        } else if (this.isTame()) {
            //override for custom health value
            return (0.55F - (SWConfiguration.WOLVES_HEALTH_TAMED.get() - this.getHealth()) * 0.02F) * (float) Math.PI;
        } else {
            return (float) Math.PI / 5;
        }
    }

    @Override
    public void tick() {
        //TODO rewrite ??
        //Stops tamed wolves from being angry at the player
        if (this.isTame()) {
            this.stopBeingAngry();
        }
        //Checks if wolf is burning and not currently standing in fire or if wolf is poison
        if (!this.isWet() &&
                (//TODO (this.isOnFire() && !this.getLevel().isFlammableWithin(this.getBoundingBox().contract(0.001, 0.001, 0.001))) ||
                        (this.hasEffect(MobEffects.POISON) || this.hasEffect(MobEffects.WITHER)))) {
            this.isShaking = true;
            this.shakeAnim = 0;
            this.shakeAnimO = 0;
            this.isWet = true;
        }

        if (!this.isWet()) {
            if (this.shakeAnim == 0) {
                //checks if burning/poisoned/wet and sets variables
                if (this.isOnFire()) {
                    this.playSound(SoundEvents.WOLF_SHAKE, this.getSoundVolume(),
                            //TODO wtf???
                            (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.2F + 1);
                } else if (this.hasEffect(MobEffects.POISON) || this.hasEffect(MobEffects.WITHER)) {
                    this.puking = true;
                } else {
                    this.playSound(SoundEvents.WOLF_SHAKE, this.getSoundVolume(),
                            //TODO wtf???
                            (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.2F + 1);
                }
            }

            if (this.shakeAnimO >= 1.95) {
                //extinguishing added
                if (this.isOnFire()) {
                    this.clearFire();
                    this.playSound(SoundEvents.FIRE_EXTINGUISH, this.getSoundVolume(),
                            //TODO wtf???
                            1.6F + (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.4F);
                }
                if (this.puking) {
                    this.removeEffect(MobEffects.POISON);
                    this.removeEffect(MobEffects.WITHER);
                    this.puking = false;
                }
            }
            if (this.shakeAnim > 0.35) {
                if (this.isOnFire()) {
                    this.spawnTamingParticles(false); //generates smoke particles while shaking
                }
                if (!this.puking) {
                    var moveVec = this.getDeltaMovement();
                    var y = this.getBoundingBox().minY + 0.8;
                    var halfWidth = this.getBbWidth() * 0.5F;
                    int var2 = (int) (Mth.sin((this.shakeAnim - 0.4F) * (float) Math.PI) * 7);

                    for (int i = 0; i < var2; i++) {
                        float dx = (this.getRandom().nextFloat() * 2 - 1) * halfWidth;
                        float dz = (this.getRandom().nextFloat() * 2 - 1) * halfWidth;
                        this.getLevel().addParticle(
                                ParticleTypes.SPLASH,
                                this.getX() + dx, y, this.getZ() + dz,
                                moveVec.x(), moveVec.y(), moveVec.z());
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
        super.tick();
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        var stack = player.getItemInHand(hand);

        if (this.isTame()) {
            if (FoodHelper.isFoodItem(stack) && this.getHealth() < SWConfiguration.WOLVES_HEALTH_TAMED.get()) {
                int hp = FoodHelper.getHealPoints(stack);

                if (hp > 0) {
                    if (!player.getAbilities().instabuild) {
                        stack.shrink(1);
                    }

                    this.heal(hp);
                    return InteractionResult.SUCCESS;
                }
            } else if (stack.getItem() instanceof ItemDogTag || stack.getItem() instanceof ItemPetCarrier) {
                return InteractionResult.FAIL;
            } else if (FoodHelper.isBone(stack)) {
                Minecraft.getInstance().setScreen(new WolfFoodConfigScreen(this));
                stack.shrink(1);
                return InteractionResult.SUCCESS;
            }
        } else if (stack.getItem() == Items.BONE && !this.isAngry()) {
            var result = super.mobInteract(player, hand);
            if (this.isTame() && !this.getLevel().isClientSide()) {
                this.setHealth(SWConfiguration.WOLVES_HEALTH_TAMED.get());
            }

            return result;
        }

        return super.mobInteract(player, hand);
    }

//TODO
//    @Override
//    public boolean isBreedingItem(ItemStack stack) {
//        if (stack == null) {
//            return false;
//        }
//
//        if (SWConfiguration.customBreeding) {
//            return stack.getItem().equals(SWItems.DOG_TREAT);
//        } else {
//            return super.isBreedingItem(stack);
//        }
//    }

    @Override
    public void setTame(boolean tamed) {
        super.setTame(tamed);
        //Used only to override default max health at spawn in case it was changed in configs
        if (tamed) {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(SWConfiguration.WOLVES_HEALTH_TAMED.get());
            this.setHealth(SWConfiguration.WOLVES_HEALTH_TAMED.get());
        } else {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(SWConfiguration.WOLVES_HEALTH_WILD.get());
            this.setHealth(SWConfiguration.WOLVES_HEALTH_WILD.get());
        }
    }

    @Override
    public Wolf getBreedOffspring(ServerLevel serverLevel, AgeableMob entity) {
        var wolf = SWEntities.getSophisticatedWolfType().create(this.level);
        wolf.updateSpecies(this.getSpecies()); //setting species to same as parent that spawned it
        var ownerId = this.getOwnerUUID();

        if (ownerId != null) {
            wolf.setOwnerUUID(ownerId);
            wolf.setTame(true);
        }
        return wolf;
    }

    @Override
    public boolean canMate(Animal animal) {
        if (animal == this || !this.isTame() || !(animal instanceof SophisticatedWolf)) {
            return false;
        } else {
            SophisticatedWolf wolf = (SophisticatedWolf) animal;
            return wolf.isTame() && (!wolf.isInSittingPose() && this.isInLove() && wolf.isInLove());
        }
    }

//TODO
//    @Override
//    protected boolean canDespawn() {
//        return !this.isTame() && SWConfiguration.respawningWolves && this.ticksExisted > 5000;
//    }

    //Custom functions below here
    public boolean isInterestingItem(ItemStack stack) {
        if (stack != null && !stack.isEmpty()) {
            if (this.getHealth() < SWConfiguration.WOLVES_HEALTH_TAMED.get() && FoodHelper.isWolfFood(this, stack)) {
                 return true;
            } else {
                return stack.getItem().equals(SWItems.getDogTreat()) && this.getAge() == 0;
            }
        }

        return false;
    }

    //checks for creepers nearby
    private boolean creeperAlert() {
        var list = this.level.getEntitiesOfClass(
                Creeper.class, this.getBoundingBox().expandTowards(16, 4, 16));
        if (!list.isEmpty()) {
            this.playSound(SoundEvents.WOLF_GROWL, getSoundVolume(),
                    (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.2F + 1);
            return true;
        } else {
            return false;
        }
    }

    //TODO
//    @Override
//    public boolean getCanSpawnHere() {
//        return super.getCanSpawnHere() && this.getLevel().provider.getDimension() == 0;
//    }

    @Override
    public EnumWolfSpecies getSpeciesByBiome() {
        var biome = this.getLevel().getBiome(this.getOnPos());

        if (biome.containsTag(Tags.Biomes.IS_SNOWY) || biome.containsTag(Tags.Biomes.IS_CONIFEROUS)) {
            if (this.getRandom().nextInt(7) == 0) {
                return EnumWolfSpecies.BLACK;
            } else {
                return EnumWolfSpecies.VANILLA;
            }
        } else {
            if (this.getRandom().nextInt(7) == 0) {
                return EnumWolfSpecies.BROWN;
            } else {
                return EnumWolfSpecies.FOREST;
            }
        }
    }

    public void setWolfSpeciesByBiome() {
        this.updateSpecies(this.getSpeciesByBiome());
    }

    @Override
    public EnumWolfSpecies getSpecies() {
        return EnumWolfSpecies.values()[this.getEntityData().get(WOLF_SPECIES)];
    }

    @Override
    public void updateSpecies(EnumWolfSpecies species) {
        this.getEntityData().set(WOLF_SPECIES, species.ordinal());
    }

    @Override
    public void die(DamageSource damageSource) {
        if (isTame() && this.getOwner() != null) {
            this.getOwner().sendSystemMessage(this.getCombatTracker().getDeathMessage());
        }
        super.die(damageSource);
    }

    @Override
    public boolean hurt(DamageSource damageSource, float amount) {
        if ((damageSource.getEntity() != null &&
                damageSource.getEntity().equals(this.getOwner()) &&
                !damageSource.getEntity().isShiftKeyDown()) ||
                (SWConfiguration.IMMUNE_TO_CACTI.get() && damageSource.equals(DamageSource.CACTUS))) {
            return false;
        } else {
            if (damageSource.equals(DamageSource.DROWN)) {
                this.isDrowning = true;
                this.drownCount = 30;
            }
            return super.hurt(damageSource, amount);
        }
    }

    @Override
    public boolean canCollideWith(Entity entity) {
        if (SWConfiguration.WOLVES_WALKS_THROUGH_EACH_OTHER.get() && entity instanceof SophisticatedWolf) {
            return false;
        }
        return super.canCollideWith(entity);
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
        return super.isWet() || this.isInWater();
    }

    @Override
    protected float getWaterSlowDown() {
        return 1;
    }

    public boolean isShaking() {
        return this.isShaking;
    }

    public boolean isRottenMeatAndBones() {
        return rottenMeatAndBones;
    }

    public void setRottenMeatAndBones(boolean rottenMeatAndBones) {
        this.rottenMeatAndBones = rottenMeatAndBones;
    }

    public boolean isRawMeat() {
        return rawMeat;
    }

    public void setRawMeat(boolean rawMeat) {
        this.rawMeat = rawMeat;
    }

    public boolean isRawFish() {
        return rawFish;
    }

    public void setRawFish(boolean rawFish) {
        this.rawFish = rawFish;
    }

    public boolean isSpecialFish() {
        return specialFish;
    }

    public void setSpecialFish(boolean specialFish) {
        this.specialFish = specialFish;
    }

    public boolean isCookedMeat() {
        return cookedMeat;
    }

    public void setCookedMeat(boolean cookedMeat) {
        this.cookedMeat = cookedMeat;
    }

    public boolean isCookedFish() {
        return cookedFish;
    }

    public void setCookedFish(boolean cookedFish) {
        this.cookedFish = cookedFish;
    }

    public boolean isAnyFood() {
        return anyFood;
    }

    public void setAnyFood(boolean anyFood) {
        this.anyFood = anyFood;
    }

    public void updateFood(boolean rottenMeatAndBones, boolean rawMeat, boolean rawFish, boolean specialFish, boolean cookedMeat, boolean cookedFish) {
        this.setRottenMeatAndBones(rottenMeatAndBones);
        this.setRawMeat(rawMeat);
        this.setRawFish(rawFish);
        this.setSpecialFish(specialFish);
        this.setCookedMeat(cookedMeat);
        this.setCookedFish(cookedFish);
        updateFood();
    }

    public void updateFood() {
        this.anyFood = rottenMeatAndBones && rawMeat && rawFish && specialFish && cookedMeat && cookedFish;
    }

}

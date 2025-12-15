package sophisticated_wolves.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NonTameRandomTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.equine.Llama;
import net.minecraft.world.entity.animal.turtle.Turtle;
import net.minecraft.world.entity.animal.wolf.Wolf;
import net.minecraft.world.entity.animal.wolf.WolfSoundVariants;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import sophisticated_wolves.api.AEntitySophisticatedWolf;
import sophisticated_wolves.core.SWConfiguration;
import sophisticated_wolves.core.SWEntities;
import sophisticated_wolves.core.SWItems;
import sophisticated_wolves.entity.ai.*;
import sophisticated_wolves.gui.screen.WolfFoodConfigScreen;
import sophisticated_wolves.item.ItemDogTag;
import sophisticated_wolves.item.ItemPetCarrier;
import sophisticated_wolves.util.FoodUtils;

import javax.annotation.Nonnull;

import static sophisticated_wolves.SophisticatedWolvesMod.LOGGER;

/**
 * Sophisticated Wolves
 *
 * @author metroidfood
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class SophisticatedWolf extends AEntitySophisticatedWolf {

    public static final int DEFAULT_WILD_WOLF_HEALTH = 8;
    public static final int DEFAULT_TAMED_WOLF_HEALTH = 30;
    public static final int DEFAULT_TAMED_WOLF_FLEE_HEALTH = 5;
    public static final int DEFAULT_TAMED_WOLF_DAMAGE = 5;
    public static final int DISTANCE_TO_TELEPORT_TO_OWNER_SQR = 900;//30^2 blocks
    public static final byte EXTINGUISH_EVENT_ID = 99;

    // food
    private static final EntityDataAccessor<Boolean> EAT_ROTTEN_MEAT_AND_BONES =
            SynchedEntityData.defineId(SophisticatedWolf.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> EAT_RAW_MEAT =
            SynchedEntityData.defineId(SophisticatedWolf.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> EAT_RAW_FISH =
            SynchedEntityData.defineId(SophisticatedWolf.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> EAT_SPECIAL_FISH =
            SynchedEntityData.defineId(SophisticatedWolf.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> EAT_COOKED_MEAT =
            SynchedEntityData.defineId(SophisticatedWolf.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> EAT_COOKED_FISH =
            SynchedEntityData.defineId(SophisticatedWolf.class, EntityDataSerializers.BOOLEAN);

    // targets
    private static final EntityDataAccessor<Boolean> ATTACK_SKELETONS =
            SynchedEntityData.defineId(SophisticatedWolf.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> ATTACK_ZOMBIES =
            SynchedEntityData.defineId(SophisticatedWolf.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> ATTACK_SPIDERS =
            SynchedEntityData.defineId(SophisticatedWolf.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> ATTACK_SLIMES =
            SynchedEntityData.defineId(SophisticatedWolf.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> ATTACK_NETHER =
            SynchedEntityData.defineId(SophisticatedWolf.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> ATTACK_RAIDER =
            SynchedEntityData.defineId(SophisticatedWolf.class, EntityDataSerializers.BOOLEAN);
    // commands
    private static final EntityDataAccessor<Boolean> FOLLOW_OWNER =
            SynchedEntityData.defineId(SophisticatedWolf.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> GUARD_ZONE =
            SynchedEntityData.defineId(SophisticatedWolf.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> GUARD_X =
            SynchedEntityData.defineId(SophisticatedWolf.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> GUARD_Y =
            SynchedEntityData.defineId(SophisticatedWolf.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> GUARD_Z =
            SynchedEntityData.defineId(SophisticatedWolf.class, EntityDataSerializers.INT);

    protected FleeGoal fleeGoal;
    protected ShakeIfBurnOrPoisonGoal shakeGoal;
    protected TeleportAtDrowningGoal drownGoal;
    protected TeleportAtBurningGoal burnGoal;

    public SophisticatedWolf(EntityType<? extends Wolf> entityType, Level level) {
        super(entityType, level);
        this.setPathfindingMalus(PathType.DANGER_FIRE, 30);
        this.setPathfindingMalus(PathType.LAVA, 100);

        if (SWConfiguration.DEBUG_MODE.get()) {
            LOGGER.info("SophisticatedWolf spawned");
        }
    }

    @Override
    protected void registerGoals() {
        this.fleeGoal = new FleeGoal(this, 16, 10, 1, 1.4);
        this.shakeGoal = new ShakeIfBurnOrPoisonGoal(this);
        this.drownGoal = new TeleportAtDrowningGoal(this);
        this.burnGoal = new TeleportAtBurningGoal(this);

        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(1, new TamableAnimal.TamableAnimalPanicGoal(1.5, DamageTypeTags.PANIC_ENVIRONMENTAL_CAUSES));
        this.goalSelector.addGoal(2, this.fleeGoal); //new behavior
        this.goalSelector.addGoal(3, new AvoidCreeperGoal(this, 8, 3, 1, 1.4)); //new behavior
        this.goalSelector.addGoal(5, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(7, this.shakeGoal); //new behavior
        this.goalSelector.addGoal(8, new AttackCancelGoal(this)); //new behavior
        this.goalSelector.addGoal(10, new Wolf.WolfAvoidEntityGoal<>(this, Llama.class, 24, 1.5, 1.5));
        this.goalSelector.addGoal(15, new LeapAtTargetGoal(this, 0.4F));
        this.goalSelector.addGoal(20, new MeleeAttackGoal(this, 1, true));
        this.goalSelector.addGoal(22, new MoveCancelAtMiningGoal(this, 6)); //new behavior
        this.goalSelector.addGoal(25, new SWFollowOwnerGoal(this, 1, 10, 2)); //new behavior
        this.goalSelector.addGoal(26, new StayNearGuardZone(this, 1)); //new behavior
        this.goalSelector.addGoal(27, this.burnGoal); //new behavior
        this.goalSelector.addGoal(28, this.drownGoal); //new behavior
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
        this.targetSelector.addGoal(7, new SWNearestAttackableTargetGoal(this)); //new behavior
        this.targetSelector.addGoal(8, new ResetUniversalAngerTargetGoal<>(this, true));
    }

    public static AttributeSupplier createAttributeSupplier() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.4)
                .add(Attributes.MAX_HEALTH, SophisticatedWolf.DEFAULT_WILD_WOLF_HEALTH)
                .add(Attributes.ATTACK_DAMAGE, 2)
                .build();
    }

    public static boolean checkSpawnRules(
            EntityType<SophisticatedWolf> entityType, LevelAccessor level, EntitySpawnReason spawnReason,
            BlockPos pos, RandomSource random) {
        return level.getBlockState(pos.below()).is(BlockTags.WOLVES_SPAWNABLE_ON) &&
                isBrightEnoughToSpawn(level, pos);
    }

    @Override
    protected void defineSynchedData(@Nonnull SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        // food
        builder.define(EAT_ROTTEN_MEAT_AND_BONES, true);
        builder.define(EAT_RAW_MEAT, false);
        builder.define(EAT_RAW_FISH, false);
        builder.define(EAT_SPECIAL_FISH, false);
        builder.define(EAT_COOKED_MEAT, false);
        builder.define(EAT_COOKED_FISH, false);
        // targets
        builder.define(ATTACK_SKELETONS, true);
        builder.define(ATTACK_ZOMBIES, false);
        builder.define(ATTACK_SPIDERS, false);
        builder.define(ATTACK_SLIMES, false);
        builder.define(ATTACK_NETHER, false);
        builder.define(ATTACK_RAIDER, false);
        // commands
        builder.define(FOLLOW_OWNER, true);
        builder.define(GUARD_ZONE, false);
        builder.define(GUARD_X, 0);
        builder.define(GUARD_Y, 64);
        builder.define(GUARD_Z, 0);
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput value) {
        super.addAdditionalSaveData(value);
        // food
        value.putBoolean("RottenMeatAndBones", eatRottenMeatAndBones());
        value.putBoolean("RawMeat", eatRawMeat());
        value.putBoolean("RawFish", eatRawFish());
        value.putBoolean("SpecialFish", eatSpecialFish());
        value.putBoolean("CookedMeat", eatCookedMeat());
        value.putBoolean("CookedFish", eatCookedFish());
        // targets
        value.putBoolean("AttackSkeletons", attackSkeletons());
        value.putBoolean("AttackZombies", attackZombies());
        value.putBoolean("AttackSpiders", attackSpiders());
        value.putBoolean("AttackSlimes", attackSlimes());
        value.putBoolean("AttackNether", attackNether());
        value.putBoolean("AttackRaider", attackRaider());
        // commands
        value.putBoolean("FollowOwner", followOwner());
        value.putBoolean("GuardZone", guardZone());
        value.putInt("GuardX", guardX());
        value.putInt("GuardY", guardY());
        value.putInt("GuardZ", guardZ());
    }

    @Override
    protected void readAdditionalSaveData(ValueInput value) {
        super.readAdditionalSaveData(value);
        this.updateFood(value);
        this.updateTargets(value);
        this.updateCommands(value);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        if (this.isAngry()) {
            return this.getSoundVariant().value().growlSound().value();
        }

        //Growls if creeper is near
        if (this.isTame() && this.creeperAlert()) {
            return this.getSoundVariant().value().growlSound().value();
        }

        if (this.getRandom().nextInt(3) == 0 && !this.creeperAlert()) {
            if (this.isTame() && this.getHealth() < SWConfiguration.WOLVES_HEALTH_TAMED.get() / 2F) {
                return this.getSoundVariant().value().whineSound().value();
            } else {
                return this.getSoundVariant().value().pantSound().value();
            }
        } else {
            //sitting wolves will only bark 1/4 of the time
            if (!this.isOrderedToSit()) {
                return this.getSoundVariant().value().ambientSound().value();
            } else {
                if (this.getRandom().nextInt(3) == 0) {
                    return this.getSoundVariant().value().ambientSound().value();
                } else {
                    return null;
                }
            }
        }
    }

    @Override
    public float getSoundVolume() {
        return super.getSoundVolume();
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
    public void handleEntityEvent(byte b) {
        if (b == EXTINGUISH_EVENT_ID) {
            var moveVec = this.getDeltaMovement();

            for (int i = 0; i < 7; i++) {
                this.level().addParticle(
                        ParticleTypes.SMOKE,
                        this.getRandomX(1), this.getRandomY() + 0.5, this.getRandomZ(1),
                        moveVec.x(), moveVec.y(), moveVec.z());
            }
        } else {
            super.handleEntityEvent(b);
        }
    }

    @Override
    public void tick() {
        if (this.isAlive()) {
            if (shakeGoal != null && (shakeGoal.isBurning() || shakeGoal.isPoisoned())) {
                // update shaking for clientSide
                this.shakeAnimO = this.shakeAnim;
                this.shakeAnim += 0.05F;
                if (this.shakeAnimO >= 2) {
                    this.isShaking = false;
                    this.shakeAnimO = 0;
                    this.shakeAnim = 0;
                }
            }
        }
        super.tick();
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        var stack = player.getItemInHand(hand);

        if (this.isTame()) {
            // breed wolves with dog treats
            if (stack.is(SWItems.getDogTreat()) &&
                    !this.level().isClientSide() &&
                    this.getAge() == 0) {
                this.usePlayerItem(player, hand, stack);
                this.setInLove(player);
                return InteractionResult.SUCCESS;
            }

            if (FoodUtils.isFoodItem(stack) && this.getHealth() < SWConfiguration.WOLVES_HEALTH_TAMED.get()) {
                int hp = FoodUtils.getHealPoints(stack);

                if (hp > 0) {
                    if (!player.getAbilities().instabuild) {
                        stack.shrink(1);
                    }

                    this.heal(hp);
                    return InteractionResult.SUCCESS;
                }
            } else if (stack.getItem() instanceof ItemDogTag || stack.getItem() instanceof ItemPetCarrier) {
                return InteractionResult.FAIL;
            } else if (FoodUtils.isBone(stack)) {
                if (this.level().isClientSide()) {
                    WolfFoodConfigScreen.open(this);
                }
                stack.shrink(1);
                return InteractionResult.SUCCESS;
            }
        } else if (stack.is(Items.BONE) && !this.isAngry()) {
            var result = super.mobInteract(player, hand);
            if (this.isTame() && !this.level().isClientSide()) {
                this.setHealth(SWConfiguration.WOLVES_HEALTH_TAMED.get());
            }

            return result;
        }

        return super.mobInteract(player, hand);
    }

    @Override
    public boolean canFallInLove() {
        // prevent mob breeding in vanilla way if custom breeding enabled
        if (SWConfiguration.CUSTOM_BREEDING.get()) {
            return false;
        }
        return super.canFallInLove();
    }

    @Override
    public void setTame(boolean tamed, boolean applyTamingSideEffects) {
        super.setTame(tamed, true);
        //Used only to override default max health at spawn in case it was changed in configs
        if (tamed) {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(SWConfiguration.WOLVES_HEALTH_TAMED.get());
            this.setHealth(SWConfiguration.WOLVES_HEALTH_TAMED.get());
        } else {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(SWConfiguration.WOLVES_HEALTH_WILD.get());
            this.setHealth(SWConfiguration.WOLVES_HEALTH_WILD.get());
        }

        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(SWConfiguration.WOLVES_DAMAGE_TAMED.get());
    }

    @Override
    public SophisticatedWolf getBreedOffspring(ServerLevel serverLevel, AgeableMob entity) {
        var wolf = SWEntities.getSophisticatedWolfType().create(serverLevel, EntitySpawnReason.BREEDING);
        if (wolf != null && entity instanceof SophisticatedWolf wolf1) {
            if (this.random.nextBoolean()) {
                wolf.setVariant(this.getVariant());
            } else {
                wolf.setVariant(wolf1.getVariant());
            }

            if (this.isTame()) {
                wolf.setOwnerReference(this.getOwnerReference());
                wolf.setTame(true, true);
                var dyecolor = this.getCollarColor();
                var dyecolor1 = wolf1.getCollarColor();
                wolf.setCollarColor(DyeColor.getMixedColor(serverLevel, dyecolor, dyecolor1));
            }

            wolf.setSoundVariant(WolfSoundVariants.pickRandomSoundVariant(this.registryAccess(), this.random));
        }

        return wolf;
    }

    @Override
    public boolean canMate(Animal animal) {
        if (animal != this && this.isTame() && animal instanceof SophisticatedWolf wolf) {
            return wolf.isTame() && (!wolf.isOrderedToSit() && this.isInLove() && wolf.isInLove());
        }
        return false;
    }

    //Custom functions below here
    public boolean isInterestingItem(ItemStack stack) {
        if (stack != null && !stack.isEmpty()) {
            if (this.getHealth() < SWConfiguration.WOLVES_HEALTH_TAMED.get() && FoodUtils.isWolfFood(this, stack)) {
                return true;
            } else {
                return stack.getItem().equals(SWItems.getDogTreat()) && this.getAge() == 0;
            }
        }

        return false;
    }

    //checks for creepers nearby
    private boolean creeperAlert() {
        var list = this.level().getEntitiesOfClass(
                Creeper.class, this.getBoundingBox().expandTowards(16, 4, 16));
        if (!list.isEmpty()) {
            this.playSound(this.getSoundVariant().value().growlSound().value(), getSoundVolume(),
                    (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.2F + 1);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean hurtServer(ServerLevel serverLevel, DamageSource damageSource, float amount) {
        if ((this.fleeGoal != null && this.fleeGoal.shouldFlee() && this.getRandom().nextInt(10) != 0) || // discard if flee
                (damageSource.getEntity() != null && damageSource.getEntity().equals(this.getOwner()) && !damageSource.getEntity().isShiftKeyDown()) || //protect from owner
                (SWConfiguration.IMMUNE_TO_CACTI.get() && damageSource.is(DamageTypes.CACTUS))) { // protect from cacti
            return false;
        } else {
            if (damageSource.is(DamageTypes.DROWN) && this.drownGoal != null) {
                this.drownGoal.setActive(true);
            }
            if ((damageSource.is(DamageTypes.IN_FIRE) || damageSource.is(DamageTypes.LAVA)) &&
                    this.burnGoal != null) {
                this.burnGoal.setActive(true);
            }
            return super.hurtServer(serverLevel, damageSource, amount);
        }
    }

    @Override
    public boolean canCollideWith(Entity entity) {
        if (SWConfiguration.WOLVES_WALKS_THROUGH_EACH_OTHER.get() && entity instanceof SophisticatedWolf) {
            return false;
        }
        if (SWConfiguration.WOLVES_WALKS_THROUGH_OWNER.get() && entity instanceof Player &&
                this.isTame() && entity.equals(this.getOwner())) {
            return false;
        }
        return super.canCollideWith(entity);
    }

    @Override
    protected int getFireImmuneTicks() {
        return 5;
    }

    @Override
    protected float getWaterSlowDown() {
        return 1;
    }

    @Override
    public boolean shouldShowName() {
        return (SWConfiguration.ALWAYS_SHOW_WOLF_NAME.get() && this.hasCustomName()) || super.shouldShowName();
    }

    /**
     * Because the implemented A* pathfinding alg doesn't straightfully call out drops from a damagable distance
     * above to be a no no, instead they check based on this function here on how many blocks the wolf can
     * accept to drop, and the default implementation depends on the health,
     * but currently as my experience, it is not good...
     * The wolf will jump even if after that he have 1 hp left... :(
     * I think you want this as Sophisticated Wolves aim to make dogs...
     * ...oops... i may have ptsd while working for DoggyTalents :)), i
     * mean wolves know how to care for themselves And not drop.... like that.
     * I tested this a lot of time in DoggyTalents and it is good...
     */
    @Override
    public int getMaxFallDistance() {
        return 3;
    }

    public void updateFood(ValueInput value) {
        this.updateFood(value.getBooleanOr("RottenMeatAndBones", true),
                value.getBooleanOr("RawMeat", false),
                value.getBooleanOr("RawFish", false),
                value.getBooleanOr("SpecialFish", false),
                value.getBooleanOr("CookedMeat", false),
                value.getBooleanOr("CookedFish", false));
    }

    public void updateFood(boolean rottenMeatAndBones, boolean rawMeat, boolean rawFish,
                           boolean specialFish, boolean cookedMeat, boolean cookedFish) {
        this.getEntityData().set(EAT_ROTTEN_MEAT_AND_BONES, rottenMeatAndBones);
        this.getEntityData().set(EAT_RAW_MEAT, rawMeat);
        this.getEntityData().set(EAT_RAW_FISH, rawFish);
        this.getEntityData().set(EAT_SPECIAL_FISH, specialFish);
        this.getEntityData().set(EAT_COOKED_MEAT, cookedMeat);
        this.getEntityData().set(EAT_COOKED_FISH, cookedFish);
    }

    public void updateTargets(ValueInput value) {
        this.updateTargets(value.getBooleanOr("AttackSkeletons", true),
                value.getBooleanOr("AttackZombies", false),
                value.getBooleanOr("AttackSpiders", false),
                value.getBooleanOr("AttackSlimes", false),
                value.getBooleanOr("AttackNether", false),
                value.getBooleanOr("AttackRaider", false));
    }

    public void updateTargets(boolean attackSkeletons, boolean attackZombies, boolean attackSpiders,
                              boolean attackSlimes, boolean attackNether, boolean attackRaider) {
        this.getEntityData().set(ATTACK_SKELETONS, attackSkeletons);
        this.getEntityData().set(ATTACK_ZOMBIES, attackZombies);
        this.getEntityData().set(ATTACK_SPIDERS, attackSpiders);
        this.getEntityData().set(ATTACK_SLIMES, attackSlimes);
        this.getEntityData().set(ATTACK_NETHER, attackNether);
        this.getEntityData().set(ATTACK_RAIDER, attackRaider);
    }

    public void updateCommands(ValueInput value) {
        this.updateCommands(value.getBooleanOr("FollowOwner", true),
                value.getBooleanOr("GuardZone", false));
    }

    public void updateCommands(boolean followOwner, boolean guardZone) {
        this.getEntityData().set(FOLLOW_OWNER, followOwner);
        this.getEntityData().set(GUARD_ZONE, guardZone);
        this.getEntityData().set(GUARD_X, this.blockPosition().getX());
        this.getEntityData().set(GUARD_Y, this.blockPosition().getY());
        this.getEntityData().set(GUARD_Z, this.blockPosition().getZ());
    }

    public boolean eatRottenMeatAndBones() {
        return this.getEntityData().get(EAT_ROTTEN_MEAT_AND_BONES);
    }

    // food
    public boolean eatRawMeat() {
        return this.getEntityData().get(EAT_RAW_MEAT);
    }

    public boolean eatRawFish() {
        return this.getEntityData().get(EAT_RAW_FISH);
    }

    public boolean eatSpecialFish() {
        return this.getEntityData().get(EAT_SPECIAL_FISH);
    }

    public boolean eatCookedMeat() {
        return this.getEntityData().get(EAT_COOKED_MEAT);
    }

    public boolean eatCookedFish() {
        return this.getEntityData().get(EAT_COOKED_FISH);
    }

    // targets
    public boolean attackSkeletons() {
        return this.getEntityData().get(ATTACK_SKELETONS);
    }

    public boolean attackZombies() {
        return this.getEntityData().get(ATTACK_ZOMBIES);
    }

    public boolean attackSpiders() {
        return this.getEntityData().get(ATTACK_SPIDERS);
    }

    public boolean attackSlimes() {
        return this.getEntityData().get(ATTACK_SLIMES);
    }

    public boolean attackNether() {
        return this.getEntityData().get(ATTACK_NETHER);
    }

    public boolean attackRaider() {
        return this.getEntityData().get(ATTACK_RAIDER);
    }

    // commands
    public boolean followOwner() {
        return this.getEntityData().get(FOLLOW_OWNER);
    }

    public boolean guardZone() {
        return this.getEntityData().get(GUARD_ZONE);
    }

    public int guardX() {
        return this.getEntityData().get(GUARD_X);
    }

    public int guardY() {
        return this.getEntityData().get(GUARD_Y);
    }

    public int guardZ() {
        return this.getEntityData().get(GUARD_Z);
    }

}

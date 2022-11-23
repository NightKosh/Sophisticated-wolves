package sophisticated_wolves.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.common.Tags;
import sophisticated_wolves.util.FoodUtils;
import sophisticated_wolves.api.AEntitySophisticatedWolf;
import sophisticated_wolves.api.EnumWolfSpecies;
import sophisticated_wolves.core.SWConfiguration;
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
    public static final int DEFAULT_TAMED_WOLF_DAMAGE = 5;
    public static final int DISTANCE_TO_TELEPORT_TO_OWNER_SQR = 900;//30^2 blocks
    public static final byte EXTINGUISH_EVENT_ID = 99;

    private static final EntityDataAccessor<Integer> WOLF_SPECIES =
            SynchedEntityData.defineId(SophisticatedWolf.class, EntityDataSerializers.INT);

    protected ShakeIfBurnOrPoisonGoal shakeGoal;
    protected TeleportAtDrowningGoal drowngGoal;

    protected boolean rottenMeatAndBones;
    protected boolean rawMeat;
    protected boolean rawFish;
    protected boolean specialFish;
    protected boolean cookedMeat;
    protected boolean cookedFish;
    protected boolean anyFood;

    public SophisticatedWolf(EntityType<? extends Wolf> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        shakeGoal = new ShakeIfBurnOrPoisonGoal(this);
        drowngGoal = new TeleportAtDrowningGoal(this);

        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(1, new Wolf.WolfPanicGoal(1.5D));
        this.goalSelector.addGoal(2, new AvoidCreeperGoal(this, 8, 6, 3, 1, 1.4)); //new behavior
        this.goalSelector.addGoal(5, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(7, shakeGoal); //new behavior
        this.goalSelector.addGoal(8, new AttackCancelGoal(this)); //new behavior
        this.goalSelector.addGoal(10, new Wolf.WolfAvoidEntityGoal<>(this, Llama.class, 24, 1.5, 1.5));
        this.goalSelector.addGoal(15, new LeapAtTargetGoal(this, 0.4F));
        this.goalSelector.addGoal(20, new MeleeAttackGoal(this, 1, true));
        this.goalSelector.addGoal(22, new MoveCancelAtMiningGoal(this, 6)); //new behavior
        this.goalSelector.addGoal(25, new SWFollowOwnerGoal(this, 1, 10, 2)); //new behavior
        this.goalSelector.addGoal(27, new AvoidFireGoal(this, 1, 1.4)); //new behavior
        this.goalSelector.addGoal(28, drowngGoal); //new behavior
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
                .add(Attributes.ATTACK_DAMAGE, 2)
                .build();
    }

    public static boolean checkSpawnRules(
            EntityType<SophisticatedWolf> entityType, LevelAccessor levelAccessor, MobSpawnType mobSpawnType,
            BlockPos blockPos, RandomSource randomSource) {
        return levelAccessor.getBlockState(blockPos.below()).is(BlockTags.WOLVES_SPAWNABLE_ON) &&
                isBrightEnoughToSpawn(levelAccessor, blockPos);
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
        this.setWolfSpeciesByBiome(accessor.getLevel());
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
            if (!this.isOrderedToSit()) {
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
                this.level.addParticle(
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
                    !this.getLevel().isClientSide() &&
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
                if (this.getLevel().isClientSide()) {
                    WolfFoodConfigScreen.open(this);
                }
                stack.shrink(1);
                return InteractionResult.SUCCESS;
            }
        } else if (stack.is(Items.BONE) && !this.isAngry()) {
            var result = super.mobInteract(player, hand);
            if (this.isTame() && !this.getLevel().isClientSide()) {
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

        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(SWConfiguration.WOLVES_DAMAGE_TAMED.get());
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

    @Override
    public EnumWolfSpecies getSpeciesByBiome(Level level) {
        var biome = level.getBiome(this.blockPosition());

        if (biome.containsTag(Tags.Biomes.IS_SNOWY)) {
            return EnumWolfSpecies.VANILLA;
        } else if (biome.containsTag(Tags.Biomes.IS_CONIFEROUS)) {//TAIGA
            return EnumWolfSpecies.BLACK;
        } else if (biome.containsTag(Tags.Biomes.IS_SPOOKY)) {//DARK_FOREST
            return EnumWolfSpecies.BROWN;
        } else {
            return EnumWolfSpecies.FOREST;
        }
    }

    public void setWolfSpeciesByBiome(Level level) {
        this.updateSpecies(this.getSpeciesByBiome(level));
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
    public boolean hurt(DamageSource damageSource, float amount) {
        if ((damageSource.getEntity() != null &&
                damageSource.getEntity().equals(this.getOwner()) &&
                !damageSource.getEntity().isShiftKeyDown()) ||
                (SWConfiguration.IMMUNE_TO_CACTI.get() && damageSource.equals(DamageSource.CACTUS))) {
            return false;
        } else {
            if (damageSource.equals(DamageSource.DROWN) && this.drowngGoal != null) {
                this.drowngGoal.setDrowning(true);
            }
            return super.hurt(damageSource, amount);
        }
    }

    @Override
    public boolean canCollideWith(Entity entity) {
        if (SWConfiguration.WOLVES_WALKS_THROUGH_EACH_OTHER.get() && entity instanceof SophisticatedWolf) {
            return false;
        }
        if (SWConfiguration.WOLVES_WALKS_THROUGH_OWNER.get() && entity instanceof Player &&
                this.isTame() && this.getOwner().equals(entity)) {
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

}

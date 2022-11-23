package sophisticated_wolves.entity.ai;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import sophisticated_wolves.util.FoodUtils;
import sophisticated_wolves.core.SWConfiguration;
import sophisticated_wolves.entity.SophisticatedWolf;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class FeedGoal extends Goal {

    private final Level level;
    private final SophisticatedWolf pet;

    private LivingEntity owner;
    protected ItemEntity foodEntity;
    private int timeToStopGoal = 0;

    public FeedGoal(SophisticatedWolf wolf) {
        this.pet = wolf;
        this.level = wolf.getLevel();
        this.owner = wolf.getOwner();
    }

    /**
     * Returns whether the Goal should begin execution.
     */
    @Override
    public boolean canUse() {
        if (this.pet.isTame() &&
                !this.pet.isOrderedToSit() &&
                this.pet.getHealth() < SWConfiguration.WOLVES_HEALTH_TAMED.get()) {
            if (this.owner == null) {
                this.owner = this.pet.getOwner();
            }
            var foodList = this.level.getEntitiesOfClass(ItemEntity.class,
                    new AABB(this.pet.getX() - 30, this.pet.getY() - 30, this.pet.getZ() - 30,
                            this.pet.getX() + 30, this.pet.getY() + 30, this.pet.getZ() + 30));
            for (ItemEntity foodEntity : foodList) {
                var stack = foodEntity.getItem();
                if ((FoodUtils.isFoodItem(stack) || FoodUtils.isBone(stack)) &&
                        FoodUtils.isWolfFood(pet, stack) &&
                        //item should be closer than teleportation range
                        this.owner != null && this.owner.distanceToSqr(foodEntity) < SophisticatedWolf.DISTANCE_TO_TELEPORT_TO_OWNER_SQR) {
                    this.foodEntity = foodEntity;
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns whether an in-progress Goal should continue executing
     */
    @Override
    public boolean canContinueToUse() {
        return this.timeToStopGoal <= 100 &&
                this.pet.getHealth() < SWConfiguration.WOLVES_HEALTH_TAMED.get() &&
                this.foodEntity != null && this.foodEntity.isAlive() &&
                //item should be closer than teleportation range
                this.owner.distanceToSqr(foodEntity) < SophisticatedWolf.DISTANCE_TO_TELEPORT_TO_OWNER_SQR;
    }

    /**
     * Updates Goal
     */
    @Override
    public void tick() {
        if (this.foodEntity != null) {
            timeToStopGoal++;
            this.pet.getNavigation().moveTo(this.foodEntity, 1);
            if (this.pet.distanceTo(this.foodEntity) <= 1) {
                this.pet.getLookControl().setLookAt(
                        this.foodEntity.getX(), this.foodEntity.getY(), this.foodEntity.getZ(),
                        0.25F, 0.25F);
                this.pet.heal(FoodUtils.getHealPoints(this.foodEntity.getItem()));
                this.foodEntity.getItem().shrink(1);
                if (this.foodEntity.getItem().isEmpty() || this.pet.getHealth() >= SWConfiguration.WOLVES_HEALTH_TAMED.get()) {
                    if (this.foodEntity.getItem().isEmpty()) {
                        this.foodEntity.remove(Entity.RemovalReason.DISCARDED);
                    }
                    this.stop();
                }
            }
        }
    }

    /**
     * Resets Goal
     */
    @Override
    public void stop() {
        this.foodEntity = null;
        this.timeToStopGoal = 0;
        this.pet.getNavigation().stop();
    }

}

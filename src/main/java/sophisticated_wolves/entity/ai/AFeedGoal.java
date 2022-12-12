package sophisticated_wolves.entity.ai;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import sophisticated_wolves.core.SWConfiguration;
import sophisticated_wolves.entity.SophisticatedWolf;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public abstract class AFeedGoal<T extends Object> extends Goal {

    protected final Level level;
    protected final SophisticatedWolf pet;

    protected LivingEntity owner;
    protected T feedObject;
    private int timeToStopGoal = 0;

    public AFeedGoal(SophisticatedWolf wolf) {
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
            return this.findFeedObject();
        }
        return false;
    }

    protected abstract boolean findFeedObject();

    /**
     * Returns whether an in-progress Goal should continue executing
     */
    @Override
    public boolean canContinueToUse() {
        return !this.pet.isOrderedToSit() &&
                this.timeToStopGoal <= 100 &&
                this.pet.getHealth() < SWConfiguration.WOLVES_HEALTH_TAMED.get() &&
                this.feedObject != null && this.ifFeedObjectAlive() &&
                //item should be closer than teleportation range
                this.getDistanceSqrToFeedObject(this.owner) < SophisticatedWolf.DISTANCE_TO_TELEPORT_TO_OWNER_SQR;
    }

    protected abstract boolean ifFeedObjectAlive();

    protected double getDistanceSqrToFeedObject(LivingEntity entity) {
        return entity.distanceToSqr(
                this.getFeedObjectPosX(),
                this.getFeedObjectPosY(),
                this.getFeedObjectPosZ());
    }

    protected abstract double getFeedObjectPosX();

    protected abstract double getFeedObjectPosY();

    protected abstract double getFeedObjectPosZ();

    /**
     * Updates Goal
     */
    @Override
    public void tick() {
        if (this.feedObject != null) {
            timeToStopGoal++;
            this.moveTo();
            if (this.getDistanceSqrToFeedObject(this.pet) <= 1.3F) {
                this.pet.getLookControl().setLookAt(
                        this.getFeedObjectPosX(), this.getFeedObjectPosY(), this.getFeedObjectPosZ(),
                        0.25F, 0.25F);

                this.feed();
            }
        }
    }

    protected abstract void moveTo();

    protected abstract void feed();

    /**
     * Resets Goal
     */
    @Override
    public void stop() {
        this.feedObject = null;
        this.timeToStopGoal = 0;
        this.pet.getNavigation().stop();
    }

}

package sophisticated_wolves.entity.ai;

import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import sophisticated_wolves.entity.SophisticatedWolf;
import sophisticated_wolves.util.LevelUtils;

/**
 * Sophisticated Wolves
 *
 * @author metroidfood
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class SWFollowOwnerGoal extends FollowOwnerGoal {

    private final SophisticatedWolf pet;
    private final double speedModifier;
    private int timeToRecalcPath;

    public SWFollowOwnerGoal(SophisticatedWolf entity, double speedModifier, float startDistance, float stopDistance) {
        super(entity, speedModifier, startDistance, stopDistance, false);

        this.pet = entity;
        this.speedModifier = speedModifier;
    }

    /**
     * Returns whether the Goal should begin execution.
     */
    @Override
    public boolean canUse() {
        return !this.pet.isLeashed() &&
                !this.pet.isPassenger() &&
                super.canUse() && //true -> owner != null
                !this.owner.onClimbable() &&
                this.pet.getWolfCommands().followOwner();
    }

    /**
     * Returns whether an in-progress Goal should continue executing
     */
    @Override
    public boolean canContinueToUse() {
        return !this.pet.isLeashed() &&
                !this.pet.isPassenger() &&
                super.canContinueToUse() &&
                !this.owner.onClimbable() &&
                this.pet.getWolfCommands().followOwner();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
    public void start() {
        super.start();
        this.timeToRecalcPath = 0;
    }

    @Override
    public void stop() {
        var owner = this.owner;
        super.stop();
        this.owner = owner;
    }

    /**
     * Updates the task
     */
    @Override
    public void tick() {
        this.pet.getLookControl().setLookAt(this.owner, 10, this.pet.getMaxHeadXRot());
        this.timeToRecalcPath--;
        if (this.timeToRecalcPath <= 0) {
            this.timeToRecalcPath = this.adjustedTickDelay(10);
            if (this.pet.distanceToSqr(this.owner) >= SophisticatedWolf.DISTANCE_TO_TELEPORT_TO_OWNER_SQR) {
                teleportToOwner();
            } else {
                this.pet.getNavigation().moveTo(this.owner, this.speedModifier);
            }
        }
    }

    private void teleportToOwner() {
        int xPos = this.owner.blockPosition().getX();
        int zPos = this.owner.blockPosition().getZ();
        int yPos = this.owner.blockPosition().getY();

        for (int dX = -2; dX <= 2; dX++) {
            for (int dZ = -2; dZ <= 2; dZ++) {
                if (LevelUtils.isPositionSafe(pet.getLevel(), xPos + dX, yPos, zPos + dZ)) {
                    this.pet.moveTo(xPos + dX + 0.5F, yPos, zPos + dZ + 0.5F,
                            this.pet.getYRot(), this.pet.getXRot());
                    this.pet.getNavigation().stop();
                    return;
                }
            }
        }
    }

}

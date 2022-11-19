package sophisticated_wolves.entity.ai;

import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.level.Level;
import sophisticated_wolves.util.LevelUtils;

/**
 * Sophisticated Wolves
 *
 * @author metroidfood
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class SWFollowOwnerGoal extends FollowOwnerGoal {

    private final TamableAnimal pet;
    private final double speedModifier;
    private int timeToRecalcPath;

    public SWFollowOwnerGoal(TamableAnimal entity, double speedModifier, float startDistance, float stopDistance) {
        super(entity, speedModifier, startDistance, stopDistance, false);

        this.pet = entity;
        this.speedModifier = speedModifier;
    }

    /**
     * Returns whether the Goal should begin execution.
     */
    @Override
    public boolean canUse() {
        return super.canUse() &&
                this.pet.getOwner() != null &&
                !this.pet.getOwner().onClimbable();
    }

    /**
     * Returns whether an in-progress Goal should continue executing
     */
    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() &&
                this.pet.getOwner() != null &&
                !this.pet.getOwner().onClimbable();
    }


    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void start() {
        super.start();
        this.timeToRecalcPath = 0;
    }

    /**
     * Updates the task
     */
    @Override
    public void tick() {
        var owner = this.pet.getOwner();
        if (owner != null) {
            this.pet.getLookControl().setLookAt(owner, 10, this.pet.getMaxHeadXRot());
            this.timeToRecalcPath--;
            if (this.timeToRecalcPath <= 0 && !this.pet.isInSittingPose() && !this.pet.isLeashed() && !this.pet.isPassenger()) {
                this.timeToRecalcPath = this.adjustedTickDelay(10);
                if (this.pet.distanceToSqr(owner) >= 144) {
                    int xPos = owner.blockPosition().getX();
                    int zPos = owner.blockPosition().getZ();
                    int yPos = owner.blockPosition().getY();

                    for (int dX = -2; dX <= 2; dX++) {
                        for (int dZ = -2; dZ <= 2; dZ++) {
                            if (canTeleport(pet.getLevel(), xPos + dX, yPos, zPos + dZ)) {
                                this.pet.moveTo(xPos + dX + 0.5F, yPos, zPos + dZ + 0.5F,
                                        this.pet.getYRot(), this.pet.getXRot());
                                this.pet.getNavigation().stop();
                                return;
                            }
                        }
                    }
                } else {
                    this.pet.getNavigation().moveTo(owner, this.speedModifier);
                }
            }
        }
    }

    public static boolean canTeleport(Level level, int x, int y, int z) {
        return LevelUtils.isGroundSafe(level, x, y - 1, z) &&
                LevelUtils.isAirSafe(level, x, y, z) &&
                LevelUtils.isAirSafe(level, x, y + 1, z);
    }

}

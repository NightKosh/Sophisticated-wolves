package sophisticated_wolves.entity.ai;

import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;

/**
 * Sophisticated Wolves
 *
 * @author metroidfood
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class MoveCancelGoal extends Goal {

    private final TamableAnimal pet;
    private final PathNavigation petPathfinder;
    private final float dist;

    private Player player;
//TODO it looks like it not working as it supposed. need more tests.
    public MoveCancelGoal(TamableAnimal entity, float par2) {
        this.pet = entity;
        this.petPathfinder = entity.getNavigation();
        this.dist = par2;
    }

    /**
     * Returns whether the Goal should begin execution.
     */
    @Override
    public boolean canUse() {
        if (!this.pet.isTame()) {
            return false;
        }

        var owner = this.pet.getOwner();
        if (owner == null ||
                this.pet.isInSittingPose() ||
                !this.pet.isOnGround() ||
                this.pet.getTarget() != null ||
                this.pet.isInLove() ||
                this.pet.distanceTo(owner) > (dist * dist * 4)) {
            return false;
        }

        if (owner instanceof Player player) {
            this.player = player;
            return this.player.swinging;
        }
        return false;
    }

    /**
     * Returns whether an in-progress Goal should continue executing
     */
    @Override
    public boolean canContinueToUse() {
        return (this.pet.getTarget() == null) &&
                (this.player.swinging) &&
                !this.pet.isInSittingPose() &&
                !this.pet.isInLove();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
    public void start() {
        this.petPathfinder.stop();
    }

    /**
     * Resets the task
     */
    @Override
    public void stop() {
        this.petPathfinder.stop();
    }

    /**
     * Updates the task
     */
    @Override
    public void tick() {

    }

}

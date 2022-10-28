package sophisticated_wolves.entity.ai;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.Level;
import sophisticated_wolves.entity.SophisticatedWolf;

/**
 * Sophisticated Wolves
 *
 * @author metroidfood
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class ShakeGoal extends Goal {

    private final SophisticatedWolf wolf;
    private final Level level;
    private final PathNavigation petPathfinder;

    public ShakeGoal(SophisticatedWolf wolf) {
        this.wolf = wolf;
        this.level = wolf.getLevel();
        this.petPathfinder = this.wolf.getNavigation();
    }

    /**
     * Returns whether the Goal should begin execution.
     */
    @Override
    public boolean canUse() {
        if (!this.wolf.isTame()) {
            return false;
        }

        if (!this.wolf.isOnGround()) {
            return false;
        }

        if (this.wolf.isShaking() && this.wolf.isWet()) {
            //TODO
//            if (this.level.isFlammableWithin(this.wolf.getBoundingBox().contract(0.001, 0.001, 0.001))) {
//                return false;
//            }
            if (this.wolf.isWet()) {
                return false;
            }
            if (this.wolf.isOnFire()) {
                return true;
            }
            return true;
        }
        return false;
    }

    /**
     * Returns whether an in-progress Goal should continue executing
     */
    @Override
    public boolean canContinueToUse() {
        if (this.wolf.isShaking() && this.wolf.isWet()) {
            //TODO
//            if (this.level.isFlammableWithin(this.wolf.getBoundingBox().contract(0.001, 0.001, 0.001))) {
//                return false;
//            }
            if (this.wolf.isWet()) {
                return false;
            }
            if (this.wolf.isOnFire()) {
                return true;
            }
            return true;
        }
        return false;
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

    }

    /**
     * Updates the task
     */
    @Override
    public void tick() {

    }

}

package sophisticated_wolves.entity.ai;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;

/**
 * Sophisticated Wolves
 *
 * @author metroidfood
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class MoveCancelAtMiningGoal extends Goal {

    private final TamableAnimal pet;
    private final PathNavigation pathNavigation;
    private final float dist;

    private LivingEntity owner;

    public MoveCancelAtMiningGoal(TamableAnimal entity, float distance) {
        this.pet = entity;
        this.owner = entity.getOwner();
        this.pathNavigation = entity.getNavigation();
        this.dist = distance * distance * 4;
    }

    /**
     * Returns whether the Goal should begin execution.
     */
    @Override
    public boolean canUse() {
        if (!this.pet.isTame() ||
                this.pet.isInSittingPose() ||
                !this.pet.isOnGround() ||
                this.pet.isInLove() ||
                this.pet.getTarget() != null) {
            return false;
        }

        var owner = this.owner == null ?
                this.pet.getOwner() :
                this.owner;
        this.owner = owner;
        if (owner == null || this.pet.distanceTo(owner) > this.dist) {
            return false;
        }
        return owner.swinging;
    }

    /**
     * Returns whether an in-progress Goal should continue executing
     */
    @Override
    public boolean canContinueToUse() {
        return this.owner.swinging;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
    public void start() {
        this.pathNavigation.stop();
    }

    @Override
    public void tick() {
        this.pathNavigation.stop();
    }

}

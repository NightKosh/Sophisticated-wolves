package sophisticated_wolves.entity.ai;

import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;

/**
 * Sophisticated Wolves
 *
 * @author metroidfood
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class SWOwnerHurtByTargetGoal extends OwnerHurtByTargetGoal {

    protected TamableAnimal theDefendingTameable;
    protected int revengeTime;

    public SWOwnerHurtByTargetGoal(TamableAnimal entity) {
        super(entity);
        this.theDefendingTameable = entity;
    }

    /**
     * Returns whether the Goal should begin execution.
     */
    @Override
    public boolean canUse() {
        var owner = this.theDefendingTameable.getOwner();
        return super.canUse() && owner != null && this.theDefendingTameable.distanceToSqr(owner) < 144;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
    public void start() {
        this.theDefendingTameable.setInSittingPose(false);
        super.start();

        var owner = this.theDefendingTameable.getOwner();
        if (owner != null) {
            this.revengeTime = owner.getLastHurtByMobTimestamp();
        }
    }

}

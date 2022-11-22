package sophisticated_wolves.entity.ai;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;

/**
 * Sophisticated Wolves
 *
 * @author metroidfood
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class SWOwnerHurtByTargetGoal extends OwnerHurtByTargetGoal {

    private final TamableAnimal animal;
    private LivingEntity owner;

    public SWOwnerHurtByTargetGoal(TamableAnimal entity) {
        super(entity);
        this.owner = entity.getOwner();
        this.animal = entity;
    }

    /**
     * Returns whether the Goal should begin execution.
     */
    @Override
    public boolean canUse() {
        if (super.canUse()) {//true -> this.animal.getOwner() != null
            if (this.owner == null) {
                this.owner = this.animal.getOwner();
            }
            return this.animal.distanceToSqr(this.owner) < 144;
        }
        return false;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
    public void start() {
        this.animal.setInSittingPose(false);
        super.start();
    }

}

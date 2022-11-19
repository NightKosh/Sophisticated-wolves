package sophisticated_wolves.entity.ai;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;

/**
 * Sophisticated Wolves
 *
 * @author metroidfood
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class SWOwnerHurtTargetGoal extends OwnerHurtTargetGoal {

    protected TamableAnimal animal;
    protected LivingEntity target;

    public SWOwnerHurtTargetGoal(TamableAnimal animal) {
        super(animal);
        this.animal = animal;
    }

    /**
     * Returns whether the Goal should begin execution.
     */
    @Override
    public boolean canUse() {
        if (super.canUse()) {
            //gets recently hit variable from entityliving that determines if a mob will drop exp
            //only attacks if target was hit recently
            //only attacks if target was attacked by owner
            var owner = (LivingEntity) this.animal.getOwner();
            if (owner != null) {
                this.target = owner.getLastHurtMob();
                return target == null ||
                        (target.getLastHurtByMobTimestamp() > 40 &&
                                (this.target.getLastHurtByMob() == null || this.target.getLastHurtByMob().equals(owner)));
            }
        }
        return false;
    }

}

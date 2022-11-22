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

    private final TamableAnimal animal;
    private LivingEntity owner;

    public SWOwnerHurtTargetGoal(TamableAnimal animal) {
        super(animal);
        this.animal = animal;
    }

    /**
     * Returns whether the Goal should begin execution.
     */
    @Override
    public boolean canUse() {
        if (super.canUse()) {//true -> this.animal.getOwner() != null
            //only attacks if target was hit recently
            //only attacks if target was attacked by owner
            if (this.owner == null) {
                this.owner = this.animal.getOwner();
            }
            var target = this.owner.getLastHurtMob();
            return target == null ||
                    (target.getLastHurtByMobTimestamp() > 40 &&
                            (target.getLastHurtByMob() == null || target.getLastHurtByMob().equals(this.owner)));
        }
        return false;
    }

}

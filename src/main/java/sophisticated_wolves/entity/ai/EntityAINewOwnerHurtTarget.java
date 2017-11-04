package sophisticated_wolves.entity.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.passive.EntityTameable;

/**
 * Sophisticated Wolves
 *
 * @author metroidfood
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class EntityAINewOwnerHurtTarget extends EntityAIOwnerHurtTarget {
    protected EntityTameable theEntityTameable;
    protected EntityLivingBase theTarget;

    public EntityAINewOwnerHurtTarget(EntityTameable entity) {
        super(entity);
        this.theEntityTameable = entity;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    @Override
    public boolean shouldExecute() {
        if (super.shouldExecute()) {
            //gets recently hit variable from entityliving that determines if a mob will drop exp
            //only attacks if target was hit recently
            //only attacks if target was attacked by owner
            EntityLivingBase owner = (EntityLivingBase) this.theEntityTameable.getOwner();
            if (owner != null) {
                this.theTarget = owner.getLastAttackedEntity();
                if (theTarget != null && (theTarget.getRevengeTimer() <= 40 || (this.theTarget.getRevengeTarget() != null && !this.theTarget.getRevengeTarget().equals(owner)))) {
                    return false;
                }
                return true;
            }
        }
        return false;
    }
}

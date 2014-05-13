package sophisticated_wolves.entity.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.passive.EntityTameable;

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
            //gets recentlyhit variable from entityliving that determines if a mob will drop exp
            //only attacks if target was hit recently
            //only attacks if target was attacked by owner
            EntityLivingBase owner = this.theEntityTameable.getOwner();
            if (owner != null) {
                this.theTarget = owner.getLastAttacker();
                if (theTarget.func_142015_aE() <= 40 || !this.theTarget.getAITarget().equals(owner)) {
                    return false;
                }
                return true;
            }
        }
        return false;
    }
}

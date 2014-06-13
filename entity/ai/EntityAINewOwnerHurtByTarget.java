package sophisticated_wolves.entity.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.passive.EntityTameable;

/**
 * Sophisticated Wolves
 *
 * @author metroidfood
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class EntityAINewOwnerHurtByTarget extends EntityAIOwnerHurtByTarget {
    protected EntityTameable theDefendingTameable;
    protected int revengeTime;

    public EntityAINewOwnerHurtByTarget(EntityTameable entity) {
        super(entity);
        this.theDefendingTameable = entity;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    @Override
    public boolean shouldExecute() {
        Entity owner = this.theDefendingTameable.getOwner();
        return super.shouldExecute() && owner != null && this.theDefendingTameable.getDistanceSqToEntity(owner) < 144;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
    public void startExecuting() {
        this.theDefendingTameable.func_70907_r().setSitting(false);
        super.startExecuting();

        EntityLivingBase owner = this.theDefendingTameable.getOwner();
        if (owner != null) {
            this.revengeTime = owner.func_142015_aE();
        }
    }
}

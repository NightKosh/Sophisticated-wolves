package sophisticated_wolves.entity.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.passive.EntityTameable;

public class EntityAISit extends EntityAIBase {
    private EntityTameable theEntity;

    /**
     * If the EntityTameable is sitting.
     */
    private boolean isSitting;

    public EntityAISit(EntityTameable entity) {
        this.theEntity = entity;
        this.setMutexBits(5);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    @Override
    public boolean shouldExecute() {
        if (!this.theEntity.isTamed()) {
            return false;
        } else if (this.theEntity.isInWater()) {
            return false;
        } else if (!this.theEntity.onGround) {
            return false;
        } else {
            EntityLivingBase owner = this.theEntity.getOwner();
            return owner == null ? true : this.isSitting;
            //(this.theEntity.getDistanceSqToEntity(var1) < 144.0D && var1.getAITarget() != null ? false : this.isSitting);
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
    public void startExecuting() {
        this.theEntity.getNavigator().clearPathEntity();
        this.theEntity.setSitting(true);
    }

    /**
     * Resets the task
     */
    @Override
    public void resetTask() {
        this.theEntity.setSitting(false);
    }

    /**
     * Updates the task
     */
    @Override
    public void updateTask() {
        this.theEntity.setSitting(isSitting);
    }

    /**
     * Sets the sitting flag.
     */
    public void setSitting(boolean isSitting) {
        this.isSitting = isSitting;
    }
}

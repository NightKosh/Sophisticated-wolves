package sophisticated_wolves.entity.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathNavigate;

public class EntityAIMoveCancel extends EntityAIBase {
    private EntityTameable thePet;
    private PathNavigate petPathfinder;
    private EntityPlayer thePlayer;
    private float dist;

    public EntityAIMoveCancel(EntityTameable entity, float par2) {
        this.thePet = entity;
        this.petPathfinder = entity.getNavigator();
        this.dist = par2;
        this.setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    @Override
    public boolean shouldExecute() {
        if (!this.thePet.isTamed()) {
            return false;
        }

        EntityLivingBase owner = this.thePet.getOwner();
        if (owner == null) {
            return false;
        }

        if (this.thePet.isSitting()) {
            return false;
        }

        if (!this.thePet.onGround) {
            return false;
        }

        if (this.thePet.getEntityToAttack() != null) {
            return false;
        }

        if (this.thePet.isInLove()) {
            return false;
        }

        if (this.thePet.getDistanceSqToEntity(owner) > (double) (dist * dist * 4)) {
            return false;
        }

        if (owner instanceof EntityPlayer) {
            this.thePlayer = (EntityPlayer) owner;

            if (this.thePlayer.isSwingInProgress || this.thePlayer.isUsingItem()) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    @Override
    public boolean continueExecuting() {
        return (this.thePet.getEntityToAttack() == null) && (this.thePlayer.isSwingInProgress || this.thePlayer.isUsingItem()) && !this.thePet.isSitting() && !this.thePet.isInLove();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
    public void startExecuting() {
        this.petPathfinder.clearPathEntity();
    }

    /**
     * Resets the task
     */
    @Override
    public void resetTask() {
        this.petPathfinder.clearPathEntity();
    }

    /**
     * Updates the task
     */
    @Override
    public void updateTask() {

    }
}

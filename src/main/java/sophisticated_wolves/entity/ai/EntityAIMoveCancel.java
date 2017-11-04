package sophisticated_wolves.entity.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathNavigate;

/**
 * Sophisticated Wolves
 *
 * @author metroidfood
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class EntityAIMoveCancel extends EntityAIBase {
    private EntityTameable pet;
    private PathNavigate petPathfinder;
    private EntityPlayer player;
    private float dist;

    public EntityAIMoveCancel(EntityTameable entity, float par2) {
        this.pet = entity;
        this.petPathfinder = entity.getNavigator();
        this.dist = par2;
        this.setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    @Override
    public boolean shouldExecute() {
        if (!this.pet.isTamed()) {
            return false;
        }

        EntityLivingBase owner = this.pet.getOwner();
        if (owner == null) {
            return false;
        }

        if (this.pet.isSitting()) {
            return false;
        }

        if (!this.pet.onGround) {
            return false;
        }

        if (this.pet.getAttackTarget() != null) {
            return false;
        }

        if (this.pet.isInLove()) {
            return false;
        }

        if (this.pet.getDistance(owner) > (double) (dist * dist * 4)) {
            return false;
        }

        if (owner instanceof EntityPlayer) {
            this.player = (EntityPlayer) owner;
            return this.player.isSwingInProgress || this.player.isHandActive();
        }
        return false;
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    @Override
    public boolean shouldContinueExecuting() {
        return (this.pet.getAttackTarget() == null) && (this.player.isSwingInProgress || this.player.isHandActive()) && !this.pet.isSitting() && !this.pet.isInLove();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
    public void startExecuting() {
        this.petPathfinder.clearPath();
    }

    /**
     * Resets the task
     */
    @Override
    public void resetTask() {
        this.petPathfinder.clearPath();
    }

    /**
     * Updates the task
     */
    @Override
    public void updateTask() {

    }
}

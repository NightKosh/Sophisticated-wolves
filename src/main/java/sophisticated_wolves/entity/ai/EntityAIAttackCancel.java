package sophisticated_wolves.entity.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.pathfinding.PathNavigate;

/**
 * Sophisticated Wolves
 *
 * @author metroidfood
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class EntityAIAttackCancel extends EntityAIBase {
    private EntityTameable pet;
    private EntityLivingBase owner;
    private PathNavigate petPathfinder;

    //SophWolves variables
    private int sneakCounter;

    public EntityAIAttackCancel(EntityTameable entity) {
        this.pet = entity;
        this.petPathfinder = entity.getNavigator();
        this.setMutexBits(1);
        this.sneakCounter = 0;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    @Override
    public boolean shouldExecute() {
        if (!this.pet.isTamed()) {
            return false;
        }

        EntityLivingBase entity = (EntityLivingBase) this.pet.getOwner();
        if (entity == null) {
            return false;
        }

        if (this.pet.isSitting()) {
            return false;
        }

        if (!this.pet.onGround) {
            return false;
        }

        if (entity.isSneaking()) {
            this.owner = entity;
            return true;
        }
        return false;
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    @Override
    public boolean continueExecuting() {
        return this.owner.isSneaking() && !this.pet.isSitting();
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
        this.sneakCounter = 0;
    }

    /**
     * Updates the task
     */
    @Override
    public void updateTask() {
        //adds to sneakCounter if owner is sneaking
        if (this.owner != null) {
            if (this.owner.isSneaking()) {
                this.sneakCounter++;
                if (this.sneakCounter > 30) {
                    this.petPathfinder.clearPathEntity();
                    this.pet.setAttackTarget((EntityLivingBase) null);
                    this.pet.setRevengeTarget((EntityLivingBase) null);

                    this.owner.setRevengeTarget((EntityLivingBase) null);
                    this.sneakCounter = 0;
                }
            } else {
                this.sneakCounter = 0;
            }
        }
    }
}

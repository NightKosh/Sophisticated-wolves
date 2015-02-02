package sophisticated_wolves.entity.ai;

import net.minecraft.entity.Entity;
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
    private EntityTameable thePet;
    private EntityLivingBase theOwner;
    private PathNavigate petPathfinder;

    //SophWolves variables
    private int sneakCounter;

    public EntityAIAttackCancel(EntityTameable entity) {
        this.thePet = entity;
        this.petPathfinder = entity.getNavigator();
        this.setMutexBits(1);
        this.sneakCounter = 0;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    @Override
    public boolean shouldExecute() {
        if (!this.thePet.isTamed()) {
            return false;
        }

        EntityLivingBase entity = (EntityLivingBase) this.thePet.getOwner();
        if (entity == null) {
            return false;
        }

        if (this.thePet.isSitting()) {
            return false;
        }

        if (!this.thePet.onGround) {
            return false;
        }

        if (entity.isSneaking()) {
            this.theOwner = entity;
            return true;
        }
        return false;
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    @Override
    public boolean continueExecuting() {
        return this.theOwner.isSneaking() && !this.thePet.isSitting();
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
        if (this.theOwner != null) {
            if (this.theOwner.isSneaking()) {
                this.sneakCounter++;
                if (this.sneakCounter > 30) {

                    this.petPathfinder.getPathToEntityLiving((Entity) null);
                    this.thePet.setAttackTarget(null);
                    this.theOwner.setRevengeTarget((EntityLivingBase) null);
                    this.thePet.setAttackTarget((EntityLivingBase) null);
                    this.sneakCounter = 0;
                }
            } else {
                this.sneakCounter = 0;
            }
        }
    }
}

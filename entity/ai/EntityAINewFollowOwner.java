package sophisticated_wolves.entity.ai;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.util.MathHelper;

/**
 * Sophisticated Wolves
 *
 * @author metroidfood
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class EntityAINewFollowOwner extends EntityAIFollowOwner {
    private EntityTameable pet;
    private EntityLivingBase owner;
    private double speed;
    private int tick;

    public EntityAINewFollowOwner(EntityTameable entity, double speed, float par4, float par5) {
        super(entity, speed, par4, par5);

        this.pet = entity;
        this.owner = this.pet.getOwner();
        this.speed = speed;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    @Override
    public boolean shouldExecute() {
        return super.shouldExecute() && owner != null && this.owner.isOnLadder();
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    @Override
    public boolean continueExecuting() {
        return super.continueExecuting() && !this.owner.isOnLadder();
    }


    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting() {
        super.startExecuting();
        this.tick = 0;
    }

    /**
     * Updates the task
     */
    @Override
    public void updateTask() {
        this.pet.getLookHelper().setLookPositionWithEntity(this.owner, 10, this.pet.getVerticalFaceSpeed());
        --this.tick;
        if (!this.pet.isSitting() && this.tick <= 0) {
            this.tick = 10;
            if (!this.pet.getNavigator().tryMoveToEntityLiving(this.owner, this.speed) && !this.pet.getLeashed() && this.pet.getDistanceSqToEntity(this.owner) >= 144.0D) {
                int xPos = MathHelper.floor_double(this.owner.posX) - 2;
                int zPos = MathHelper.floor_double(this.owner.posZ) - 2;
                int yPos = MathHelper.floor_double(this.owner.boundingBox.minY);

                for (int dX = 0; dX <= 4; ++dX) {
                    for (int dZ = 0; dZ <= 4; ++dZ) {
                        if ((dX < 1 || dZ < 1 || dX > 3 || dZ > 3) && this.isTeleportSafe(xPos + dX, yPos - 1, zPos + dZ) && this.isAirSafe(xPos + dX, yPos, zPos + dZ) && this.isAirSafe(xPos + dX, yPos + 1, zPos + dZ)) {
                            this.pet.setLocationAndAngles((double) ((float) (xPos + dX) + 0.5F), (double) yPos, (double) ((float) (zPos + dZ) + 0.5F), this.pet.rotationYaw, this.pet.rotationPitch);
                            this.pet.getNavigator().clearPathEntity();
                            return;
                        }
                    }
                }
            }
        }
    }

    private boolean isTeleportSafe(int x, int y, int z) {
        Block block = pet.worldObj.getBlock(x, y, z);
        if (block != null) {
            Material material = block.getMaterial();
            if ((pet.worldObj.isBlockNormalCubeDefault(x, y, z, false) || material.equals(Material.ice) || material.equals(Material.leaves) || material.equals(Material.glass)) &&
                    !material.equals(Material.cactus) && !material.equals(material.plants)) {
                return true;
            }
        }
        return false;
    }

    private boolean isAirSafe(int x, int y, int z) {
        Block block = pet.worldObj.getBlock(x, y, z);
        if (block != null) {
            Material material = block.getMaterial();
            if (!pet.worldObj.isBlockNormalCubeDefault(x, y, z, false) && !material.equals(Material.water) && !material.equals(Material.lava) &&
                    !material.equals(Material.fire) && !material.equals(Material.leaves) && !material.equals(Material.glass) && !material.equals(Material.ice)) {
                return true;
            }
        }
        return false;
    }
}

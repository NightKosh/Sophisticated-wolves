package sophisticated_wolves.entity.ai;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

/**
 * Sophisticated Wolves
 *
 * @author metroidfood
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class EntityAINewFollowOwner extends EntityAIFollowOwner {
    private EntityTameable pet;
    private double speed;
    private int tick;

    public EntityAINewFollowOwner(EntityTameable entity, double speed, float par4, float par5) {
        super(entity, speed, par4, par5);

        this.pet = entity;
        this.speed = speed;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    @Override
    public boolean shouldExecute() {
        return super.shouldExecute() && this.pet.getOwner() != null && !this.pet.getOwner().isOnLadder();
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    @Override
    public boolean shouldContinueExecuting() {
        return super.shouldContinueExecuting() && this.pet.getOwner() != null && !this.pet.getOwner().isOnLadder();
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
        EntityLivingBase owner = this.pet.getOwner();
        this.pet.getLookHelper().setLookPositionWithEntity(owner, 10, this.pet.getVerticalFaceSpeed());
        --this.tick;
        if (!this.pet.isSitting() && this.tick <= 0) {
            this.tick = 10;
            if (!this.pet.getNavigator().tryMoveToEntityLiving(owner, this.speed) && !this.pet.getLeashed() && this.pet.getDistanceSq(owner) >= 144) {
                int xPos = MathHelper.floor(owner.posX);
                int zPos = MathHelper.floor(owner.posZ);
                int yPos = MathHelper.floor(owner.getEntityBoundingBox().minY);

                for (int dX = -2; dX <= 2; dX++) {
                    for (int dZ = -2; dZ <= 2; dZ++) {
                        if (canTeleport(pet.world, xPos + dX, yPos, zPos + dZ)) {
                            this.pet.setLocationAndAngles(xPos + dX + 0.5F, yPos, zPos + dZ + 0.5F, this.pet.rotationYaw, this.pet.rotationPitch);
                            this.pet.getNavigator().clearPath();
                            return;
                        }
                    }
                }
            }
        }
    }

    public static boolean canTeleport(World world, int x, int y, int z) {
        return isTeleportSafe(world, x, y - 1, z) &&
                isAirSafe(world, x, y, z) &&
                isAirSafe(world, x, y + 1, z);
    }

    private static boolean isTeleportSafe(World world, int x, int y, int z) {
        IBlockState blockState = world.getBlockState(new BlockPos(x, y, z));
        if (blockState != null) {
            Material material = blockState.getMaterial();
            if ((blockState.getBlock().isNormalCube(blockState) || material.equals(Material.ICE) || material.equals(Material.LEAVES) ||
                    material.equals(Material.GLASS) ||
                    material.equals(Material.WATER) && world.getBlockState(new BlockPos(x, y, z).up()).getMaterial().equals(Material.AIR)) &&
                    !material.equals(Material.CACTUS) && !material.equals(material.PLANTS)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isAirSafe(World world, int x, int y, int z) {
        IBlockState blockState = world.getBlockState(new BlockPos(x, y, z));
        if (blockState != null) {
            Material material = blockState.getBlock().getMaterial(blockState);
            if (!blockState.getBlock().isNormalCube(blockState) && !material.equals(Material.WATER) && !material.equals(Material.LAVA) &&
                    !material.equals(Material.FIRE) && !material.equals(Material.LEAVES) && !material.equals(Material.GLASS) && !material.equals(Material.ICE)) {
                return true;
            }
        }
        return false;
    }
}

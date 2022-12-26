package sophisticated_wolves.util;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.AABB;

/**
 * Sophisticated Wolves
 *
 * @author metroidfood
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class LevelUtils {

    public static boolean containsAnyInFire(Level level, AABB aabb) {
        int minX = Mth.floor(aabb.minX);
        int maxX = Mth.ceil(aabb.maxX);
        int minY = Mth.floor(aabb.minY);
        int maxY = Mth.ceil(aabb.maxY);
        int minZ = Mth.floor(aabb.minZ);
        int maxZ = Mth.ceil(aabb.maxZ);
        var mutableBlockPos = new BlockPos.MutableBlockPos();

        for(int x = minX; x < maxX; ++x) {
            for(int y = minY; y < maxY; ++y) {
                for(int z = minZ; z < maxZ; ++z) {
                    var block = level.getBlockState(mutableBlockPos.set(x, y, z)).getBlock();
                    if (block == Blocks.FIRE || block == Blocks.SOUL_FIRE ||
                            block == Blocks.CAMPFIRE || block == Blocks.SOUL_CAMPFIRE ||
                            block == Blocks.LAVA) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public static void teleportTo(Mob mob, LivingEntity entity) {
        teleportTo(mob, entity.blockPosition().getX(), entity.blockPosition().getZ(), entity.blockPosition().getY());
    }

    public static void teleportTo(Mob mob, int xPos, int zPos, int yPos) {
        for (int dX = -2; dX <= 2; dX++) {
            for (int dZ = -2; dZ <= 2; dZ++) {
                if (LevelUtils.isPositionSafe(mob.getLevel(), xPos + dX, yPos, zPos + dZ)) {
                    mob.moveTo(xPos + dX + 0.5F, yPos, zPos + dZ + 0.5F,
                            mob.getYRot(), mob.getXRot());
                    mob.getNavigation().stop();
                    return;
                }
            }
        }
    }

    public static boolean isPositionSafe(Level level, int x, int y, int z) {
        return LevelUtils.isGroundSafe(level, x, y - 1, z) &&
                LevelUtils.isAirSafe(level, x, y, z) &&
                LevelUtils.isAirSafe(level, x, y + 1, z);
    }

    public static boolean isGroundSafe(Level level, int x, int y, int z) {
        var blockState = level.getBlockState(new BlockPos(x, y, z));
        var material = blockState.getMaterial();
        var block = blockState.getBlock();

        return (material.isSolid() ||
                material.equals(Material.WATER) && level.getBlockState(new BlockPos(x, y, z).above())
                        .getMaterial().equals(Material.AIR)) &&
                block != Blocks.MAGMA_BLOCK &&
                block != Blocks.CAMPFIRE &&
                block != Blocks.SOUL_CAMPFIRE &&
                !material.equals(Material.CACTUS) &&
                !material.equals(Material.PLANT);
    }

    public static boolean isAirSafe(Level level, int x, int y, int z) {
        var blockState = level.getBlockState(new BlockPos(x, y, z));
        var material = blockState.getMaterial();
        return !material.isSolid() &&
                !material.equals(Material.WATER) &&
                !material.equals(Material.LAVA) &&
                !material.equals(Material.FIRE) &&
                !material.equals(Material.LEAVES) &&
                !material.equals(Material.GLASS) &&
                !material.equals(Material.ICE);
    }

}

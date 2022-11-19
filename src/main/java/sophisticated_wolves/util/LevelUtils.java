package sophisticated_wolves.util;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
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
                    if (block == Blocks.FIRE || block == Blocks.SOUL_FIRE || block == Blocks.LAVA) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public static boolean isGroundSafe(Level level, int x, int y, int z) {
        var blockState = level.getBlockState(new BlockPos(x, y, z));
        var material = blockState.getMaterial();

        return (material.isSolid() ||
                material.equals(Material.ICE) ||
                material.equals(Material.LEAVES) ||
                material.equals(Material.GLASS) ||
                material.equals(Material.WATER) && level.getBlockState(new BlockPos(x, y, z).above())
                        .getMaterial().equals(Material.AIR)) &&
                !material.equals(Material.CACTUS)
                && !material.equals(Material.PLANT);
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

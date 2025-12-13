package sophisticated_wolves.util;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
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

        for (int x = minX; x < maxX; ++x) {
            for (int y = minY; y < maxY; ++y) {
                for (int z = minZ; z < maxZ; ++z) {
                    if (WalkNodeEvaluator.isBurningBlock(level.getBlockState(mutableBlockPos.set(x, y, z)))) {
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
                if (LevelUtils.isPositionSafe(mob.level(), xPos + dX, yPos, zPos + dZ)) {
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
        var pos = new BlockPos(x, y, z);
        var state = level.getBlockState(pos);
        var block = state.getBlock();

        return (state.blocksMotion() || block == Blocks.WATER && level.getBlockState(pos.above()).isAir())
                && block != Blocks.MAGMA_BLOCK
                && block != Blocks.CAMPFIRE
                && block != Blocks.SOUL_CAMPFIRE
                && !state.is(Blocks.CACTUS)
                && !state.is(BlockTags.CROPS);
    }

    public static boolean isAirSafe(Level level, int x, int y, int z) {
        var state = level.getBlockState(new BlockPos(x, y, z));

        return state.isAir() ||
                (!state.blocksMotion()//not Solid block
                        && state.getFluidState().isEmpty()
                        && !state.is(BlockTags.FIRE)
                        && !state.is(BlockTags.LEAVES)
                        && !state.is(BlockTags.IMPERMEABLE)//GLASS
                        && !state.is(BlockTags.ICE));
    }

}

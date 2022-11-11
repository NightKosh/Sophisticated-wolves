package sophisticated_wolves.entity.ai;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Material;
import sophisticated_wolves.entity.SophisticatedWolf;
import sophisticated_wolves.util.DogUtil;

/**
 * Sophisticated Wolves
 *
 * @author metroidfood
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class SWFollowOwnerGoal extends FollowOwnerGoal {

    private final SophisticatedWolf pet;
    private final double speedModifier;
    private int tick;

    public SWFollowOwnerGoal(SophisticatedWolf entity, double speedModifier, float startDistance, float stopDistance) {
        super(entity, speedModifier, startDistance, stopDistance, false);

        this.pet = entity;
        this.speedModifier = speedModifier;
    }

    /**
     * Returns whether the Goal should begin execution.
     */
    @Override
    public boolean canUse() {
        return super.canUse() &&
                this.pet.getOwner() != null &&
                !this.pet.getOwner().onClimbable();
    }

    /**
     * Returns whether an in-progress Goal should continue executing
     */
    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() &&
                this.pet.getOwner() != null &&
                !this.pet.getOwner().onClimbable();
    }


    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void start() {
        super.start();
        this.tick = 0;
    }

    /**
     * Updates the task
     */
    @Override
    public void tick() {
        this.pet.getLookControl().setLookAt(this.pet.getOwner(), 10.0F, this.pet.getMaxHeadXRot());
        if (--this.tick <= 0) {
            this.tick = 10;
            if (!this.pet.isLeashed() && !this.pet.isPassenger()) {
                if (this.pet.distanceToSqr(this.pet.getOwner()) >= 144.0D) {
                    DogUtil.guessAndTryToTeleportToOwner(pet, 4);
                } else {
                    this.pet.getNavigation().moveTo(this.pet.getOwner(), this.speedModifier);
                }
            }
        }
    }

    public static boolean canTeleport(Level level, int x, int y, int z) {
        return isTeleportSafe(level, x, y - 1, z) &&
                isAirSafe(level, x, y, z) &&
                isAirSafe(level, x, y + 1, z);
    }

    private static boolean isTeleportSafe(Level level, int x, int y, int z) {
        var blockState = level.getBlockState(new BlockPos(x, y, z));
        var material = blockState.getMaterial();
        //TODO
        //if ((blockState.getBlock().isNormalCube(blockState) ||
        return (material.equals(Material.ICE) ||
                material.equals(Material.LEAVES) ||
                material.equals(Material.GLASS) ||
                material.equals(Material.WATER) && level.getBlockState(new BlockPos(x, y, z).above())
                        .getMaterial().equals(Material.AIR)) &&
                !material.equals(Material.CACTUS)
                && !material.equals(Material.PLANT);
    }

    private static boolean isAirSafe(Level level, int x, int y, int z) {
        var blockState = level.getBlockState(new BlockPos(x, y, z));
        var material = blockState.getMaterial();
        //TODO
        //if (!blockState.getBlock().isNormalCube(blockState) &&
        return !material.equals(Material.WATER) &&
                !material.equals(Material.LAVA) &&
                !material.equals(Material.FIRE) &&
                !material.equals(Material.LEAVES) &&
                !material.equals(Material.GLASS) &&
                !material.equals(Material.ICE);
    }

}

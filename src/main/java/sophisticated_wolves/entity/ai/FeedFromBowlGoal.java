package sophisticated_wolves.entity.ai;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import sophisticated_wolves.core.SWConfiguration;
import sophisticated_wolves.block_entity.BlockEntityDogBowl;

import java.util.Map;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class FeedFromBowlGoal extends Goal {

    private final TamableAnimal pet;
    private final Level level;

    protected BlockEntityDogBowl dogBowl;

    public FeedFromBowlGoal(TamableAnimal animal) {
        this.pet = animal;
        this.level = animal.getLevel();
    }

    @Override
    public boolean canUse() {
        if (!this.pet.isTame() ||
                this.pet.isInSittingPose() ||
                this.pet.getHealth() >= SWConfiguration.WOLVES_HEALTH_TAMED.get()) {
            return false;
        } else {
            var x = this.pet.getX();
            var y = this.pet.getY();
            var z = this.pet.getZ();
            if (getBowlTe(this.pet.getOnPos()) ||
                    getBowlTe(new BlockPos(x + 16, y, z)) ||
                    getBowlTe(new BlockPos(x - 16, y, z)) ||
                    getBowlTe(new BlockPos(x, y, z + 16)) ||
                    getBowlTe(new BlockPos(x, y, z + 16)) ||
                    getBowlTe(new BlockPos(x + 16, y, z + 16)) ||
                    getBowlTe(new BlockPos(x + 16, y, z - 16)) ||
                    getBowlTe(new BlockPos(x - 16, y, z + 16)) ||
                    getBowlTe(new BlockPos(x- 16, y, z - 16))) {
                return true;
            }
        }
        return false;
    }

    private boolean getBowlTe(BlockPos pos) {
        Map<BlockPos, BlockEntity> beMap = this.level.getChunkAt(pos).getBlockEntities();
        for (Map.Entry<BlockPos, BlockEntity> beEntry : beMap.entrySet()) {
            if (beEntry != null && beEntry.getValue() instanceof BlockEntityDogBowl bowl) {
                var x = beEntry.getKey().getX();
                var y = beEntry.getKey().getY();
                var z = beEntry.getKey().getZ();
                if (this.pet.distanceToSqr(x, y, z) < 50 && bowl.getFoodAmount() > 0) {
                    this.dogBowl = bowl;
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        return this.pet.getHealth() < SWConfiguration.WOLVES_HEALTH_TAMED.get() &&
                this.dogBowl != null && this.dogBowl.getFoodAmount() > 0;
    }

    @Override
    public void tick() {
        if (this.dogBowl != null) {
            this.pet.getNavigation().moveTo(
                    this.pet.getNavigation().createPath(this.dogBowl.getBlockPos(), 0),
                    1);
            var x = this.dogBowl.getBlockPos().getX();
            var y = this.dogBowl.getBlockPos().getY();
            var z = this.dogBowl.getBlockPos().getZ();
            if (this.pet.distanceToSqr(x, y, z) <= 1.3F) {
                this.pet.getLookControl().setLookAt(x, y, z, 0.25F, 0.25F);
                this.pet.heal(1);

                var owner = this.pet.getOwner();
                if (owner != null && owner instanceof Player player) {
                    if (!player.getAbilities().instabuild) {
                        this.dogBowl.addFood(-1);
                    }
                } else {
                    this.dogBowl.addFood(-1);
                }

                if (this.dogBowl.getFoodAmount() <= 0 ||
                        this.pet.getHealth() >= SWConfiguration.WOLVES_HEALTH_TAMED.get()) {
                    this.dogBowl = null;
                    this.pet.getNavigation().stop();
                }
            }
        }
    }

    @Override
    public void stop() {
        this.dogBowl = null;
        this.pet.getNavigation().stop();
    }

}

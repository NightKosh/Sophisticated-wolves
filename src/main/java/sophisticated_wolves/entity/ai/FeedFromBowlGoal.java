package sophisticated_wolves.entity.ai;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import sophisticated_wolves.SWConfiguration;
import sophisticated_wolves.tile_entity.TileEntityDogBowl;

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

    protected TileEntityDogBowl dogBowl;

    public FeedFromBowlGoal(TamableAnimal animal) {
        this.pet = animal;
        this.level = animal.getLevel();
    }

    @Override
    public boolean canUse() {
        if (!this.pet.isTame() ||
                this.pet.isInSittingPose() ||
                this.pet.getHealth() >= SWConfiguration.wolvesHealthTamed) {
            return false;
        } else {
            if (getBowlTe(this.pet.getOnPos()) ||
                    getBowlTe(new BlockPos(this.pet.getX() + 16, this.pet.getY(), this.pet.getZ())) ||
                    getBowlTe(new BlockPos(this.pet.getX() - 16, this.pet.getY(), this.pet.getZ())) ||
                    getBowlTe(new BlockPos(this.pet.getX(), this.pet.getY(), this.pet.getZ() + 16)) ||
                    getBowlTe(new BlockPos(this.pet.getX(), this.pet.getY(), this.pet.getZ() + 16)) ||
                    getBowlTe(new BlockPos(this.pet.getX() + 16, this.pet.getY(), this.pet.getZ() + 16)) ||
                    getBowlTe(new BlockPos(this.pet.getX() + 16, this.pet.getY(), this.pet.getZ() - 16)) ||
                    getBowlTe(new BlockPos(this.pet.getX() - 16, this.pet.getY(), this.pet.getZ() + 16)) ||
                    getBowlTe(new BlockPos(this.pet.getX() - 16, this.pet.getY(), this.pet.getZ() - 16))) {
                return true;
            }
        }
        return false;
    }

    private boolean getBowlTe(BlockPos pos) {
        //TODO
//        Map<BlockPos, TileEntity> teMap = this.level.getChunkFromBlockCoords(pos).getTileEntityMap();
//        for (Map.Entry<BlockPos, TileEntity> teEntry : teMap.entrySet()) {
//            if (teEntry != null && teEntry.getValue() instanceof TileEntityDogBowl bowl &&
//                    this.pet.distanceToSqr(teEntry.getKey()) < 50) {
//                if (bowl.getFoodAmount() > 0) {
//                    this.dogBowl = bowl;
//                    return true;
//                }
//            }
//        }
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        return this.pet.getHealth() < SWConfiguration.wolvesHealthTamed && this.dogBowl != null && this.dogBowl.getFoodAmount() > 0;
    }

    @Override
    public void tick() {
        if (this.dogBowl != null) {
            //TODO
//            this.pet.getNavigation().moveTo(this.pet.getNavigation().createPath(
//                    this.dogBowl.getPos().getX(), this.dogBowl.getPos().getY(), this.dogBowl.getPos().getZ()), 1);
//            if (this.pet.distanceToSqr(this.dogBowl.getPos()) <= 1.3F) {
//                this.pet.getLookControl().setLookAt(
//                        this.dogBowl.getPos().getX(), this.dogBowl.getPos().getY(), this.dogBowl.getPos().getZ(),
//                        0.25F, 0.25F);
//                this.pet.heal(1);
//
//                var owner = this.pet.getOwner();
//                if (owner != null && owner instanceof Player player) {
//                    if (!player.getAbilities().instabuild) {
//                        this.dogBowl.addFood(-1);
//                    }
//                } else {
//                    this.dogBowl.addFood(-1);
//                }
//
//                if (this.dogBowl.getFoodAmount() <= 0 || this.pet.getHealth() >= SWConfiguration.wolvesHealthTamed) {
//                    this.dogBowl = null;
//                    this.pet.getNavigation().stop();
//                }
//            }
        }
    }

    @Override
    public void stop() {
        this.dogBowl = null;
        this.pet.getNavigation().stop();
    }

}

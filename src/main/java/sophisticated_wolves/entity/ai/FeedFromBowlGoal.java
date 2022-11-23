package sophisticated_wolves.entity.ai;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import sophisticated_wolves.core.SWConfiguration;
import sophisticated_wolves.block_entity.BlockEntityDogBowl;
import sophisticated_wolves.entity.SophisticatedWolf;

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

    private LivingEntity owner;
    private int timeToStopGoal = 0;
    private BlockEntityDogBowl dogBowl;

    public FeedFromBowlGoal(TamableAnimal animal) {
        this.pet = animal;
        this.level = animal.getLevel();
        this.owner = animal.getOwner();
    }

    @Override
    public boolean canUse() {
        if (this.pet.isTame() &&
                !this.pet.isOrderedToSit() &&
                this.pet.getHealth() < SWConfiguration.WOLVES_HEALTH_TAMED.get()) {
            if (this.owner == null) {
                this.owner = this.pet.getOwner();
            }
            var x = this.pet.getX();
            var y = this.pet.getY();
            var z = this.pet.getZ();
            //TODO store bowls as list and look in this list first
            return getBowlTe(this.pet.blockPosition()) ||
                    getBowlTe(new BlockPos(x + 16, y, z)) ||
                    getBowlTe(new BlockPos(x - 16, y, z)) ||
                    getBowlTe(new BlockPos(x, y, z + 16)) ||
                    getBowlTe(new BlockPos(x, y, z + 16)) ||
                    getBowlTe(new BlockPos(x + 16, y, z + 16)) ||
                    getBowlTe(new BlockPos(x + 16, y, z - 16)) ||
                    getBowlTe(new BlockPos(x - 16, y, z + 16)) ||
                    getBowlTe(new BlockPos(x - 16, y, z - 16));
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
                if (bowl.getFoodAmount() > 0 &&
                        this.pet.distanceToSqr(x, y, z) < 50 &&
                        //item should be closer than teleportation range
                        this.owner != null && this.owner.distanceToSqr(x, y, z) < SophisticatedWolf.DISTANCE_TO_TELEPORT_TO_OWNER_SQR) {
                    this.dogBowl = bowl;
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        return this.timeToStopGoal <= 100 &&
                this.pet.getHealth() < SWConfiguration.WOLVES_HEALTH_TAMED.get() &&
                this.dogBowl != null && !this.dogBowl.isRemoved() && this.dogBowl.getFoodAmount() > 0 &&
                //item should be closer than teleportation range
                this.owner.distanceToSqr(
                        this.dogBowl.getBlockPos().getX(),
                        this.dogBowl.getBlockPos().getY(),
                        this.dogBowl.getBlockPos().getZ()) < SophisticatedWolf.DISTANCE_TO_TELEPORT_TO_OWNER_SQR;
    }

    @Override
    public void tick() {
        if (this.dogBowl != null) {
            timeToStopGoal++;
            this.pet.getNavigation().moveTo(
                    this.pet.getNavigation().createPath(this.dogBowl.getBlockPos(), 0),
                    1);
            var x = this.dogBowl.getBlockPos().getX();
            var y = this.dogBowl.getBlockPos().getY();
            var z = this.dogBowl.getBlockPos().getZ();
            if (this.pet.distanceToSqr(x, y, z) <= 1.3F) {
                this.pet.getLookControl().setLookAt(x, y, z, 0.25F, 0.25F);
                this.pet.heal(1);

                this.dogBowl.addFood(-1);
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
        this.timeToStopGoal = 0;
        this.pet.getNavigation().stop();
    }

}

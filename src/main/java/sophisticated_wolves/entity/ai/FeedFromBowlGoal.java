package sophisticated_wolves.entity.ai;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import sophisticated_wolves.block_entity.BlockEntityDogBowl;
import sophisticated_wolves.core.SWConfiguration;
import sophisticated_wolves.entity.SophisticatedWolf;

import java.util.HashMap;
import java.util.Map;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class FeedFromBowlGoal extends AFeedGoal<BlockEntityDogBowl> {

    //TODO replace by R-tree ?
    private static final Map<BlockPos, BlockEntity> BOWLS = new HashMap<>();

    public FeedFromBowlGoal(SophisticatedWolf wolf) {
        super(wolf);
    }

    @Override
    protected boolean findFeedObject() {
        return tryToGetBowl(BOWLS, false) || tryToGetBowlForNearestChunks(this.pet.blockPosition());
    }

    @Override
    protected boolean ifFeedObjectAlive() {
        return !this.feedObject.isRemoved() && this.feedObject.getFoodAmount() > 0;
    }

    @Override
    protected double getFeedObjectPosX() {
        return this.feedObject.getBlockPos().getX();
    }

    @Override
    protected double getFeedObjectPosY() {
        return this.feedObject.getBlockPos().getY();
    }

    @Override
    protected double getFeedObjectPosZ() {
        return this.feedObject.getBlockPos().getZ();
    }

    @Override
    protected void moveTo() {
        this.pet.getNavigation().moveTo(
                this.pet.getNavigation().createPath(this.feedObject.getBlockPos(), 0),
                1);
    }

    @Override
    protected void feed() {
        this.pet.heal(1);

        this.feedObject.addFood(-1);
        if (this.feedObject.getFoodAmount() <= 0 ||
                this.pet.getHealth() >= SWConfiguration.WOLVES_HEALTH_TAMED.get()) {
            this.stop();
        }
    }

    private boolean tryToGetBowlForNearestChunks(BlockPos pos) {
        var x = pos.getX();
        var y = pos.getY();
        var z = pos.getZ();
        return tryToGetBowl(pos) ||
                tryToGetBowl(new BlockPos(x + 16, y, z)) ||
                tryToGetBowl(new BlockPos(x - 16, y, z)) ||
                tryToGetBowl(new BlockPos(x, y, z + 16)) ||
                tryToGetBowl(new BlockPos(x, y, z + 16)) ||
                tryToGetBowl(new BlockPos(x + 16, y, z + 16)) ||
                tryToGetBowl(new BlockPos(x + 16, y, z - 16)) ||
                tryToGetBowl(new BlockPos(x - 16, y, z + 16)) ||
                tryToGetBowl(new BlockPos(x - 16, y, z - 16));
    }

    private boolean tryToGetBowl(BlockPos pos) {
        return tryToGetBowl(this.level.getChunkAt(pos).getBlockEntities(), true);
    }

    private boolean tryToGetBowl(Map<BlockPos, BlockEntity> beMap, boolean isExternalMap) {
        for (var beEntry : beMap.entrySet()) {
            if (beEntry != null && beEntry.getValue() instanceof BlockEntityDogBowl bowl) {
                var x = beEntry.getKey().getX();
                var y = beEntry.getKey().getY();
                var z = beEntry.getKey().getZ();
                if (bowl.getFoodAmount() > 0 &&
                        //bowl should be closer than teleportation range
                        this.owner != null &&
                        this.owner.distanceToSqr(x, y, z) < SophisticatedWolf.DISTANCE_TO_TELEPORT_TO_OWNER_SQR) {
                    if (isExternalMap) {
                        BOWLS.put(bowl.getBlockPos(), bowl);
                    }
                    this.feedObject = bowl;
                    return true;
                }
            }
        }
        return false;
    }


}

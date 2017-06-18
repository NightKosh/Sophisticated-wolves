package sophisticated_wolves.entity.ai;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import sophisticated_wolves.tile_entity.TileEntityDogBowl;

import java.util.Map;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class AIFeedFromBowl extends EntityAIBase {

    private EntityTameable pet;
    private World world;
    protected TileEntityDogBowl dogBowl;

    public AIFeedFromBowl(EntityTameable pet) {
        this.pet = pet;
        this.world = pet.world;
    }

    @Override
    public boolean shouldExecute() {
        if (this.pet.isSitting() || this.pet.getHealth() >= 20) {
            return false;
        } else {
            if (getBowlTe(this.pet.getPosition())) {
                return true;
            } else if (getBowlTe(new BlockPos(this.pet.getPosition().getX() + 16, this.pet.getPosition().getY(), this.pet.getPosition().getZ()))) {
                return true;
            } else if (getBowlTe(new BlockPos(this.pet.getPosition().getX() - 16, this.pet.getPosition().getY(), this.pet.getPosition().getZ()))) {
                return true;
            } else if (getBowlTe(new BlockPos(this.pet.getPosition().getX(), this.pet.getPosition().getY(), this.pet.getPosition().getZ() + 16))) {
                return true;
            } else if (getBowlTe(new BlockPos(this.pet.getPosition().getX(), this.pet.getPosition().getY(), this.pet.getPosition().getZ() + 16))) {
                return true;
            } else if (getBowlTe(new BlockPos(this.pet.getPosition().getX() + 16, this.pet.getPosition().getY(), this.pet.getPosition().getZ() + 16))) {
                return true;
            }  else if (getBowlTe(new BlockPos(this.pet.getPosition().getX() + 16, this.pet.getPosition().getY(), this.pet.getPosition().getZ() - 16))) {
                return true;
            } else if (getBowlTe(new BlockPos(this.pet.getPosition().getX() - 16, this.pet.getPosition().getY(), this.pet.getPosition().getZ() + 16))) {
                return true;
            } else if (getBowlTe(new BlockPos(this.pet.getPosition().getX() - 16, this.pet.getPosition().getY(), this.pet.getPosition().getZ() - 16))) {
                return true;
            }
        }
        return false;
    }

    private boolean getBowlTe(BlockPos pos) {
        Map<BlockPos, TileEntity> teMap = this.world.getChunkFromBlockCoords(pos).getTileEntityMap();
        for (Map.Entry<BlockPos, TileEntity> teEntry : teMap.entrySet()) {
            if (teEntry != null && teEntry.getValue() instanceof TileEntityDogBowl &&
                    this.pet.getDistanceSq(teEntry.getKey()) < 50) {
                this.dogBowl = (TileEntityDogBowl) teEntry.getValue();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean continueExecuting() {
        return this.pet.getHealth() < 20 && this.dogBowl != null;
    }

    @Override
    public void updateTask() {
        if (this.dogBowl != null) {
            this.pet.getNavigator().setPath(this.pet.getNavigator().getPathToXYZ(this.dogBowl.getPos().getX(), this.dogBowl.getPos().getY(), this.dogBowl.getPos().getZ()), 1);
            if (this.pet.getDistanceSq(this.dogBowl.getPos()) <= 1) {
                this.pet.getLookHelper().setLookPosition(this.dogBowl.getPos().getX(), this.dogBowl.getPos().getY(), this.dogBowl.getPos().getZ(), 0.25F, 0.25F);
                this.pet.heal(1);
                this.dogBowl.addFood(-1);
                if (this.dogBowl.getFoodAmount() == 0 || this.pet.getHealth() >= 20) {
                    this.dogBowl = null;
                    this.pet.getNavigator().clearPathEntity();
                }
            }
        }
    }

    @Override
    public void resetTask() {
        this.dogBowl = null;
        this.pet.getNavigator().clearPathEntity();
    }
}

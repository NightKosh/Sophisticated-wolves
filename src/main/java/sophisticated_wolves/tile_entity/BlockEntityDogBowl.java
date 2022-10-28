package sophisticated_wolves.tile_entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import sophisticated_wolves.block.BlockDogBowl;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class BlockEntityDogBowl extends BlockEntity {

    private int foodAmount;

    public BlockEntityDogBowl(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState state) {
        super(blockEntityType, blockPos, state);
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);

        compound.putInt("FoodAmount", foodAmount);
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);

        foodAmount = compound.getInt("FoodAmount");
    }

    public int getFoodAmount() {
        return foodAmount;
    }

    public void setFoodAmount(int foodAmount) {
        this.foodAmount = foodAmount;
        if (this.foodAmount > 100) {
            this.foodAmount = 100;
        } else if (this.foodAmount < 0) {
            this.foodAmount = 0;
        }
        this.amountOfFoodChanged();
    }

    public void addFood(int foodAmount) {
        this.setFoodAmount(this.foodAmount + foodAmount);
    }

    private void amountOfFoodChanged() {
        //TODO
//        BlockDogBowl.EnumDogBowl bowlType = BlockDogBowl.EnumDogBowl.getTypeByFood(foodAmount);
//
//        if (this.getBlockMetadata() != bowlType.ordinal()) {
//            this.getLevel().setBlockState(this.getPos(), SWBlocks.getDefaultState().withProperty(BlockDogBowl.VARIANT, bowlType));
//        } else if (!this.getLevel().isClientSide()) {
//            BlockState state =  this.getLevel().getBlockState(this.getPos());
//            this.getLevel().notifyBlockUpdate(this.getPos(), state, state, 3);
//        }
    }

//TODO remove ?
//    @Override
//    public boolean shouldRefresh(Level level, BlockPos pos, BlockState oldState, BlockState newSate) {
//        return oldState.getBlock() != newSate.getBlock();
//    }
//
//    @Override
//    public boolean receiveClientEvent(int par1, int par2) {
//        return true;
//    }
//
//    @Override
//    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
//        readFromNBT(packet.getNbtCompound());
//    }
//
//    @Override
//    public SPacketUpdateTileEntity getUpdatePacket() {
//        return new SPacketUpdateTileEntity(this.pos, 1, this.getUpdateTag());
//    }

    @Override
    public CompoundTag getUpdateTag() {
        var tag = new CompoundTag();
        this.saveAdditional(tag);
        return tag;
    }

}

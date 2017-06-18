package sophisticated_wolves.tile_entity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import sophisticated_wolves.SWBlocks;
import sophisticated_wolves.block.BlockDogBowl;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class TileEntityDogBowl extends TileEntity {

    private int foodAmount;

    public TileEntityDogBowl() {
    }

    public TileEntityDogBowl(World world) {
        this.world = world;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound = super.writeToNBT(compound);

        compound.setInteger("FoodAmount", foodAmount);

        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        foodAmount = compound.getInteger("FoodAmount");
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
        BlockDogBowl.EnumDogBowl bowlType = BlockDogBowl.EnumDogBowl.getTypeByFood(foodAmount);

        if (this.getBlockMetadata() != bowlType.ordinal()) {
            this.getWorld().setBlockState(this.getPos(), SWBlocks.DOG_BOWL.getDefaultState().withProperty(BlockDogBowl.VARIANT, bowlType));
        } else if (!this.getWorld().isRemote) {
            IBlockState state =  this.getWorld().getBlockState(this.getPos());
            this.getWorld().notifyBlockUpdate(this.getPos(), state, state, 3);
        }
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
        return false;
    }

    @Override
    public boolean receiveClientEvent(int par1, int par2) {
        return true;
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
        readFromNBT(packet.getNbtCompound());
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(this.pos, 1, this.getUpdateTag());
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return this.writeToNBT(new NBTTagCompound());
    }
}

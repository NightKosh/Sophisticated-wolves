package sophisticated_wolves.tile_entity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
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
        this.amountOfFoodChanged();
    }

    public void addFood(int foodAmount) {
        this.setFoodAmount(this.foodAmount + foodAmount);
    }

    private void amountOfFoodChanged() {
        if (foodAmount == BlockDogBowl.EnumDogBowl.EMPTY.getAmountOfFood()) {
            this.getWorld().setBlockState(this.getPos(), SWBlocks.DOG_BOWL.getDefaultState().withProperty(BlockDogBowl.VARIANT, BlockDogBowl.EnumDogBowl.EMPTY));
        } else if (foodAmount <= BlockDogBowl.EnumDogBowl.FILLED1.getAmountOfFood()) {
            this.getWorld().setBlockState(this.getPos(), SWBlocks.DOG_BOWL.getDefaultState().withProperty(BlockDogBowl.VARIANT, BlockDogBowl.EnumDogBowl.FILLED1));
        } else if (foodAmount <= BlockDogBowl.EnumDogBowl.FILLED2.getAmountOfFood()) {
            this.getWorld().setBlockState(this.getPos(), SWBlocks.DOG_BOWL.getDefaultState().withProperty(BlockDogBowl.VARIANT, BlockDogBowl.EnumDogBowl.FILLED2));
        } else if (foodAmount <= BlockDogBowl.EnumDogBowl.FILLED3.getAmountOfFood()) {
            this.getWorld().setBlockState(this.getPos(), SWBlocks.DOG_BOWL.getDefaultState().withProperty(BlockDogBowl.VARIANT, BlockDogBowl.EnumDogBowl.FILLED3));
        } else {
            this.getWorld().setBlockState(this.getPos(), SWBlocks.DOG_BOWL.getDefaultState().withProperty(BlockDogBowl.VARIANT, BlockDogBowl.EnumDogBowl.FILLED4));
        }
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

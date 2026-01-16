package sophisticated_wolves.block_entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import sophisticated_wolves.block.BlockDogBowl;
import sophisticated_wolves.core.SWBlockEntities;
import sophisticated_wolves.gui.menu.DogBowlContainerMenu;

import javax.annotation.Nonnull;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class BlockEntityDogBowl extends BlockEntity implements MenuProvider {

    private int foodAmount;

    public BlockEntityDogBowl(BlockPos blockPos, BlockState state) {
        this(SWBlockEntities.DOG_BOWL.get(), blockPos, state);
    }

    public BlockEntityDogBowl(BlockEntityType type, BlockPos blockPos, BlockState state) {
        super(type, blockPos, state);
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

        this.setChanged();

        if (!this.level.isClientSide()) {
            this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 3);
        }
    }

    private void amountOfFoodChanged() {
        this.getLevel().setBlockAndUpdate(
                this.getBlockPos(),
                this.getBlockState().setValue(
                        BlockDogBowl.FOOD_LEVEL,
                        BlockDogBowl.EnumDogBowl.getTypeByFood(foodAmount).ordinal()));
    }

    @Override
    protected void saveAdditional(ValueOutput out) {
        super.saveAdditional(out);
        out.putInt("FoodAmount", foodAmount);
    }

    @Override
    protected void loadAdditional(ValueInput in) {
        super.loadAdditional(in);
        foodAmount = in.getIntOr("FoodAmount", 0);
    }

    @Nonnull
    @Override
    public CompoundTag getUpdateTag(@Nonnull HolderLookup.Provider provider) {
        var tag = new CompoundTag();
        tag.putInt("FoodAmount", this.foodAmount);
        return tag;
    }

    @Override
    public void handleUpdateTag(ValueInput in) {
        loadAdditional(in);
    }

    @Nonnull
    @Override
    public Component getDisplayName() {
        return Component.empty();
    }

    @Override
    public AbstractContainerMenu createMenu(int containerId, @Nonnull Inventory inventory, @Nonnull Player player) {
        return new DogBowlContainerMenu(containerId, inventory, this);
    }
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

}

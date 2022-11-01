package sophisticated_wolves.block_entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import sophisticated_wolves.block.BlockDogBowl;
import sophisticated_wolves.core.SWBlockEntities;
import sophisticated_wolves.gui.menu.DogBowlContainerMenu;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class BlockEntityDogBowl extends BlockEntity implements MenuProvider {

    private int foodAmount;

    public BlockEntityDogBowl(BlockPos blockPos, BlockState state) {
        super(SWBlockEntities.DOG_BOWL.get(), blockPos, state);

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
        var bowlType = BlockDogBowl.EnumDogBowl.getTypeByFood(foodAmount);

        if (this.getBlockState().getValue(BlockDogBowl.FOOD_LEVEL) != bowlType.ordinal()) {
            this.getLevel().setBlockAndUpdate(
                    this.getBlockPos(),
                    this.getBlockState().setValue(BlockDogBowl.FOOD_LEVEL, bowlType.ordinal()));
        }
    }

    @Override
    public CompoundTag getUpdateTag() {
        var tag = new CompoundTag();
        this.saveAdditional(tag);
        return tag;
    }

    @Override
    public Component getDisplayName() {
        return Component.empty();
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
        return new DogBowlContainerMenu(containerId, inventory, this);
    }

}

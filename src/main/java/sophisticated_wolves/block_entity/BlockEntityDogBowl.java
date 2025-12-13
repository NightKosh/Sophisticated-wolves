package sophisticated_wolves.block_entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
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
        super(SWBlockEntities.DOG_BOWL.get(), blockPos, state);

    }

    @Override
    public void saveAdditional(@Nonnull CompoundTag compound, @Nonnull HolderLookup.Provider registries) {
        super.saveAdditional(compound, registries);

        compound.putInt("FoodAmount", foodAmount);
    }

    @Override
    public void loadAdditional(@Nonnull CompoundTag tag, @Nonnull HolderLookup.Provider provider) {
        super.loadAdditional(tag, provider);

        foodAmount = tag.getInt("FoodAmount");
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
        this.getLevel().setBlockAndUpdate(
                this.getBlockPos(),
                this.getBlockState().setValue(
                        BlockDogBowl.FOOD_LEVEL,
                        BlockDogBowl.EnumDogBowl.getTypeByFood(foodAmount).ordinal()));
    }

    @Nonnull
    @Override
    public CompoundTag getUpdateTag(@Nonnull HolderLookup.Provider provider) {
        var tag = new CompoundTag();
        this.saveAdditional(tag, provider);
        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag, @Nonnull HolderLookup.Provider provider) {
        foodAmount = tag.getInt("FoodAmount");
    }

    @Nonnull
    @Override
    public Component getDisplayName() {
        return Component.empty();
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, @Nonnull Inventory inventory, @Nonnull Player player) {
        return new DogBowlContainerMenu(containerId, inventory, this);
    }

}

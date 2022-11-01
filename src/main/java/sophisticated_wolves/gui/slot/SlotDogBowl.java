package sophisticated_wolves.gui.slot;

import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import sophisticated_wolves.FoodHelper;
import sophisticated_wolves.block.BlockDogBowl;
import sophisticated_wolves.block_entity.BlockEntityDogBowl;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class SlotDogBowl extends Slot {

    private BlockEntityDogBowl bodBowl;

    public SlotDogBowl(BlockEntityDogBowl bowl, int slotNum, int xPos, int yPos) {
        super(new SimpleContainer(1), slotNum, xPos, yPos);
        this.bodBowl = bowl;
    }

    @Override
    public void set(ItemStack stack) {
        if (mayPlace(stack)) {
            bodBowl.addFood(getFoodAmount(stack));
        }
    }

    @Override
    public ItemStack getItem() {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean hasItem() {
        return false;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return bodBowl.getFoodAmount() < BlockDogBowl.EnumDogBowl.FULL.getAmountOfFood() &&
                (FoodHelper.isFoodItem(stack) || FoodHelper.isBone(stack));
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    private int getFoodAmount(ItemStack stack) {
        return FoodHelper.getHealPoints(stack);
    }

}

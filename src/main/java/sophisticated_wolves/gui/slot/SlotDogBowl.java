package sophisticated_wolves.gui.slot;

import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import sophisticated_wolves.FoodHelper;
import sophisticated_wolves.tile_entity.TileEntityDogBowl;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class SlotDogBowl extends Slot {

    private TileEntityDogBowl te;

    public SlotDogBowl(TileEntityDogBowl te, int slotNum, int xPos, int yPos) {
        super(null, slotNum, xPos, yPos);
        this.te = te;
    }

    @Override
    public void putStack(ItemStack stack) {
        if (isItemValid(stack)) {
            int amountOfFood = getFoodAmount(stack);
            te.addFood(amountOfFood);
        }
    }

    @Override
    public ItemStack getStack() {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean getHasStack() {
        return false;
    }

    @Override
    public void onSlotChange(ItemStack stack1, ItemStack stack2) {
    }

    @Override
    public void onSlotChanged() {
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return te.getFoodAmount() < 100 && (FoodHelper.isFoodItem(stack) || FoodHelper.isBone(stack));
    }

    @Override
    public int getSlotStackLimit() {
        return 1;
    }

    public int getFoodAmount(ItemStack stack) {
        if (isItemValid(stack)) {
            return FoodHelper.getHealPoints(stack);
        } else {
            return 0;
        }
    }
}

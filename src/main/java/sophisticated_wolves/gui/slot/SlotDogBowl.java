package sophisticated_wolves.gui.slot;

import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import sophisticated_wolves.MessageHandler;
import sophisticated_wolves.packets.DogBowlMessageToServer;
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
//        if (isItemValid(stack) && !te.getWorld().isRemote) {
            int amountOfFood = getFoodAmount(stack);
            MessageHandler.networkWrapper.sendToServer(new DogBowlMessageToServer(te, amountOfFood));
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
        return te.getFoodAmount() < 100 && stack.getItem() instanceof ItemFood || stack.getItem().getUnlocalizedName().equals("item.bone");
    }

    @Override
    public int getSlotStackLimit() {
        return 1;
    }

    public int getFoodAmount(ItemStack stack) {
        if (isItemValid(stack)) {
            if (stack.getItem().getUnlocalizedName().equals("item.bone")) {
                return 1;
            } else {
                ItemFood foodItem = (ItemFood) stack.getItem();

                //checks static FurnaceRecipes for cooked version of held food
                ItemStack cookedStack = FurnaceRecipes.instance().getSmeltingResult(stack);
                if (cookedStack != null && cookedStack.getItem() instanceof ItemFood) {
                    ItemFood foodCooked = (ItemFood) cookedStack.getItem();
                    if (foodCooked.getHealAmount(cookedStack) > foodItem.getHealAmount(stack)) {
                        foodItem = (ItemFood) cookedStack.getItem(); //sets ID to cooked version of food if it exists
                    }
                }

                return foodItem.getHealAmount(stack);
            }
        } else {
            return 0;
        }
    }
}

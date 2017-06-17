package sophisticated_wolves;

import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class FoodHelper {

    public static boolean isFoodItem(ItemStack stack) {
        return stack.getItem() instanceof ItemFood;
    }

    public static boolean isWolfFood(ItemStack stack) {
        return ((ItemFood) stack.getItem()).isWolfsFavoriteMeat() || stack.getItem().equals(Items.COOKED_FISH) ||
                stack.getItem().equals(Items.FISH) || isBone(stack);
    }

    public static boolean isBone(ItemStack stack) {
        return stack.getItem().getUnlocalizedName().equals("item.bone");
    }

    public static int getHealPoints(ItemStack stack) {
        if (FoodHelper.isBone(stack)) {
            return 1;
        } else if (isFoodItem(stack)) {
            ItemFood foodItem = (ItemFood) stack.getItem();

            //checks static FurnaceRecipes for cooked version of held food
            ItemStack cookedStack = FurnaceRecipes.instance().getSmeltingResult(stack);
            if (cookedStack != null && cookedStack.getItem() instanceof ItemFood) {
                ItemFood foodCooked = (ItemFood) cookedStack.getItem();
                if (foodCooked.getHealAmount(cookedStack) > foodItem.getHealAmount(stack)) {
                    foodItem = (ItemFood) cookedStack.getItem(); //sets ID to cooked version of food if it exists
                }
            }

            if (isWolfFood(stack)) {
                return foodItem.getHealAmount(stack);
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }
}

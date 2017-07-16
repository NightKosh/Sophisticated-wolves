package sophisticated_wolves;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import sophisticated_wolves.entity.EntitySophisticatedWolf;

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
        return isBone(stack) || ((ItemFood) stack.getItem()).isWolfsFavoriteMeat() ||
                stack.getItem().equals(Items.COOKED_FISH) || stack.getItem().equals(Items.FISH) || isBone(stack);
    }

    public static boolean isWolfFood(EntitySophisticatedWolf wolf, ItemStack stack) {
        if (wolf.isAnyFood()) {
            return isWolfFood(stack);
        } else {
            return wolf.isRottenMeatAndBones() && (isBone(stack) || isFoodType(stack, Items.ROTTEN_FLESH)) ||
                    wolf.isRawFish() && isFoodType(stack, Items.FISH) && (stack.getItemDamage() == 0 || stack.getItemDamage() == 1) ||
                    wolf.isSpecialFish() && isFoodType(stack, Items.FISH) && (stack.getItemDamage() == 2 || stack.getItemDamage() == 3) ||
                    wolf.isCookedFish() && isFoodType(stack, Items.COOKED_FISH) ||
                    wolf.isRawMeat() && (isFoodType(stack, Items.CHICKEN) || isFoodType(stack, Items.BEEF) ||
                            isFoodType(stack, Items.PORKCHOP) || isFoodType(stack, Items.MUTTON) || isFoodType(stack, Items.RABBIT)) ||
                    wolf.isCookedMeat() && (isFoodType(stack, Items.COOKED_CHICKEN) || isFoodType(stack, Items.COOKED_BEEF) ||
                            isFoodType(stack, Items.COOKED_PORKCHOP) || isFoodType(stack, Items.COOKED_MUTTON) || isFoodType(stack, Items.COOKED_RABBIT));
        }
    }

    public static boolean isBone(ItemStack stack) {
        return isFoodType(stack, Items.BONE);
    }

    protected static boolean isFoodType(ItemStack stack, Item item) {
        return stack.getItem().equals(item);
    }

    protected static boolean isFoodType(ItemStack stack, String name) {
        return stack.getItem().getUnlocalizedName().equals("item." + name);
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

package sophisticated_wolves;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import sophisticated_wolves.entity.SophisticatedWolf;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class FoodHelper {

    public static boolean isFoodItem(ItemStack stack) {
        return stack.getFoodProperties(null) != null;
    }

    public static boolean isWolfFood(ItemStack stack) {
        return isBone(stack) ||
                stack.getFoodProperties(null) != null && stack.getFoodProperties(null).isMeat() ||
                stack.getItem().equals(Items.COOKED_COD) ||
                stack.getItem().equals(Items.COOKED_SALMON) ||
                stack.getItem().equals(Items.COD) ||
                stack.getItem().equals(Items.SALMON) ||
                stack.getItem().equals(Items.PUFFERFISH) ||
                stack.getItem().equals(Items.TROPICAL_FISH);
    }

    public static boolean isWolfFood(SophisticatedWolf wolf, ItemStack stack) {
        if (wolf.isAnyFood()) {
            return isWolfFood(stack);
        } else {
            return wolf.isRottenMeatAndBones() && (isBone(stack) || isFoodType(stack, Items.ROTTEN_FLESH)) ||
                    wolf.isRawMeat() && (
                            isFoodType(stack, Items.CHICKEN) || isFoodType(stack, Items.BEEF) ||
                            isFoodType(stack, Items.PORKCHOP) || isFoodType(stack, Items.MUTTON) ||
                            isFoodType(stack, Items.RABBIT)) ||
                    wolf.isCookedMeat() && (
                            isFoodType(stack, Items.COOKED_CHICKEN) || isFoodType(stack, Items.COOKED_BEEF) ||
                            isFoodType(stack, Items.COOKED_PORKCHOP) || isFoodType(stack, Items.COOKED_MUTTON) ||
                            isFoodType(stack, Items.COOKED_RABBIT)) ||
                    wolf.isRawFish() && (
                            isFoodType(stack, Items.COD) || isFoodType(stack, Items.SALMON)) ||
                    wolf.isCookedFish() && (
                            isFoodType(stack, Items.COOKED_COD) || isFoodType(stack, Items.COOKED_SALMON)) ||
                    wolf.isSpecialFish() && (
                            isFoodType(stack, Items.PUFFERFISH) || isFoodType(stack, Items.TROPICAL_FISH));
        }
    }

    public static boolean isBone(ItemStack stack) {
        return Items.BONE.equals(stack.getItem());
    }

    protected static boolean isFoodType(ItemStack stack, Item item) {
        return item.equals(stack.getItem());
    }

    public static int getHealPoints(ItemStack stack) {
        if (FoodHelper.isBone(stack)) {
            return 1;
        } else if (isFoodItem(stack)) {
            if (isWolfFood(stack)) {
                return stack.getFoodProperties(null).getNutrition();
            } else {
                return 0;
            }
        }
        return 0;
    }

}

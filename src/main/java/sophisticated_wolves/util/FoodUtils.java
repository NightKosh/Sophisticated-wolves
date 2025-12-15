package sophisticated_wolves.util;

import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.ItemTags;
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
public class FoodUtils {

    public static boolean isFoodItem(ItemStack stack) {
        return stack.has(DataComponents.FOOD);
    }

    public static boolean isWolfFood(ItemStack stack) {
        return isBone(stack) ||
                stack.is(ItemTags.WOLF_FOOD) ||
                stack.getItem().equals(Items.COOKED_COD) ||
                stack.getItem().equals(Items.COOKED_SALMON) ||
                stack.getItem().equals(Items.COD) ||
                stack.getItem().equals(Items.SALMON) ||
                stack.getItem().equals(Items.PUFFERFISH) ||
                stack.getItem().equals(Items.TROPICAL_FISH);
    }

    public static boolean isWolfFood(SophisticatedWolf wolf, ItemStack stack) {
        return wolf.eatRottenMeatAndBones() && (isBone(stack) || isFoodType(stack, Items.ROTTEN_FLESH)) ||
                wolf.eatRawMeat() && (
                        isFoodType(stack, Items.CHICKEN) || isFoodType(stack, Items.BEEF) ||
                                isFoodType(stack, Items.PORKCHOP) || isFoodType(stack, Items.MUTTON) ||
                                isFoodType(stack, Items.RABBIT)) ||
                wolf.eatCookedMeat() && (
                        isFoodType(stack, Items.COOKED_CHICKEN) || isFoodType(stack, Items.COOKED_BEEF) ||
                                isFoodType(stack, Items.COOKED_PORKCHOP) || isFoodType(stack, Items.COOKED_MUTTON) ||
                                isFoodType(stack, Items.COOKED_RABBIT)) ||
                wolf.eatRawFish() && (
                        isFoodType(stack, Items.COD) || isFoodType(stack, Items.SALMON)) ||
                wolf.eatCookedFish() && (
                        isFoodType(stack, Items.COOKED_COD) || isFoodType(stack, Items.COOKED_SALMON)) ||
                wolf.eatSpecialFish() && (
                        isFoodType(stack, Items.PUFFERFISH) || isFoodType(stack, Items.TROPICAL_FISH));
    }

    public static boolean isBone(ItemStack stack) {
        return Items.BONE.equals(stack.getItem());
    }

    protected static boolean isFoodType(ItemStack stack, Item item) {
        return item.equals(stack.getItem());
    }

    public static int getHealPoints(ItemStack stack) {
        if (FoodUtils.isBone(stack)) {
            return 1;
        } else if (isFoodItem(stack)) {
            if (isWolfFood(stack)) {
                return stack.get(DataComponents.FOOD).nutrition();
            } else {
                return 0;
            }
        }
        return 0;
    }

}

package sophisticated_wolves.entity;

import net.minecraft.nbt.CompoundTag;

/**
 * Sophisticated Wolves
 *
 * @author metroidfood
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public record WolfFood(boolean rottenMeatAndBones, boolean rawMeat, boolean rawFish, boolean specialFish,
                       boolean cookedMeat, boolean cookedFish, boolean anyFood) {

    public WolfFood() {
        this(false, false, false, false, false, false, false);
    }

    public WolfFood(boolean rottenMeatAndBones, boolean rawMeat, boolean rawFish,
                    boolean specialFish, boolean cookedMeat, boolean cookedFish) {
        this(rottenMeatAndBones, rawMeat, rawFish, specialFish, cookedMeat, cookedFish,
                rottenMeatAndBones && rawMeat && rawFish && specialFish && cookedMeat && cookedFish);
    }

    public void saveData(CompoundTag tag) {
        var allowedFoodTag = new CompoundTag();
        allowedFoodTag.putBoolean("RottenMeatAndBones", this.rottenMeatAndBones);
        allowedFoodTag.putBoolean("RawMeat", this.rawMeat);
        allowedFoodTag.putBoolean("RawFish", this.rawFish);
        allowedFoodTag.putBoolean("SpecialFish", this.specialFish);
        allowedFoodTag.putBoolean("CookedMeat", this.cookedMeat);
        allowedFoodTag.putBoolean("CookedFish", this.cookedFish);
        tag.put("AllowedFood", allowedFoodTag);
    }

    public static WolfFood getFromTag(CompoundTag tag) {
        if (tag.contains("AllowedFood")) {
            var allowedFoodTag = tag.getCompound("AllowedFood");
            return new WolfFood(
                    getFromTag(allowedFoodTag, "RottenMeatAndBones"),
                    getFromTag(allowedFoodTag, "RawMeat"),
                    getFromTag(allowedFoodTag, "RawFish"),
                    getFromTag(allowedFoodTag, "SpecialFish"),
                    getFromTag(allowedFoodTag, "CookedMeat"),
                    getFromTag(allowedFoodTag, "CookedFish"));
        }
        return new WolfFood();
    }

    private static boolean getFromTag(CompoundTag tag, String name) {
        return tag.contains(name) && tag.getBoolean(name);
    }

}

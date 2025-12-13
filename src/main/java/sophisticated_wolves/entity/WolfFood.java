package sophisticated_wolves.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import sophisticated_wolves.util.CompoundTagUtils;

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

    public static CompoundTag toTag(WolfFood wolfFood) {
        var tag = new CompoundTag();
        wolfFood.saveData(tag);
        return tag;
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
                    CompoundTagUtils.getFromTag(allowedFoodTag, "RottenMeatAndBones"),
                    CompoundTagUtils.getFromTag(allowedFoodTag, "RawMeat"),
                    CompoundTagUtils.getFromTag(allowedFoodTag, "RawFish"),
                    CompoundTagUtils.getFromTag(allowedFoodTag, "SpecialFish"),
                    CompoundTagUtils.getFromTag(allowedFoodTag, "CookedMeat"),
                    CompoundTagUtils.getFromTag(allowedFoodTag, "CookedFish"));
        }
        return new WolfFood();
    }

    public void saveData(FriendlyByteBuf buffer) {
        buffer.writeBoolean(this.rottenMeatAndBones);
        buffer.writeBoolean(this.rawMeat);
        buffer.writeBoolean(this.rawFish);
        buffer.writeBoolean(this.specialFish);
        buffer.writeBoolean(this.cookedMeat);
        buffer.writeBoolean(this.cookedFish);
    }

    public static WolfFood getFromByteBuf(FriendlyByteBuf buffer) {
        return new WolfFood(
                buffer.readBoolean(),
                buffer.readBoolean(),
                buffer.readBoolean(),
                buffer.readBoolean(),
                buffer.readBoolean(),
                buffer.readBoolean());
    }

}

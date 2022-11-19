package sophisticated_wolves.item.pet_carrier;

import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.CatVariant;
import net.minecraft.world.entity.player.Player;
import sophisticated_wolves.api.pet_carrier.PetCarrier;

import java.util.ArrayList;
import java.util.List;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class CatPetCarrier extends PetCarrier<Cat> {

    public enum EnumCatType {
        TABBY(CatVariant.TABBY),
        BLACK(CatVariant.BLACK),
        RED(CatVariant.RED),
        SIAMESE(CatVariant.SIAMESE),
        BRITISH_SHORTHAIR(CatVariant.BRITISH_SHORTHAIR),
        CALICO(CatVariant.CALICO),
        PERSIAN(CatVariant.PERSIAN),
        RAGDOLL(CatVariant.RAGDOLL),
        WHITE(CatVariant.WHITE),
        JELLIE(CatVariant.JELLIE),
        ALL_BLACK(CatVariant.ALL_BLACK);

        private final CatVariant catVariant;

        EnumCatType(CatVariant catVariant) {
            this.catVariant = catVariant;
        }

        public static EnumCatType getByNum(int num) {
            if (num >= 0 && num < EnumCatType.values().length) {
                return EnumCatType.values()[num];
            } else {
                return TABBY;
            }
        }

        public static EnumCatType getByCatType(CatVariant catVariant) {
            for (var catType : EnumCatType.values()) {
                if (catType.getCatVariant().equals(catVariant)) {
                    return catType;
                }
            }
            return TABBY;
        }

        public CatVariant getCatVariant() {
            return catVariant;
        }

    }

    @Override
    public Class getPetClass() {
        return Cat.class;
    }

    @Override
    public String getPetNameLocalizationKey() {
        return "entity.minecraft.cat";
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.CAT;
    }

    @Override
    public List<Component> getInfo(CompoundTag infoTag) {
        if (infoTag.contains("CatType")) {
            return List.of(Component.translatable("sophisticated_wolves.carrier.type")
                    .append(" - ")
                    .append(Component.translatable(
                            "sophisticated_wolves.cat_type." + EnumCatType.getByNum(infoTag.getInt("CatType"))
                                    .toString().toLowerCase())));
        }
        return null;
    }

    @Override
    public CompoundTag getInfo(Cat cat) {
        var tag = new CompoundTag();
        tag.putInt("CatType", EnumCatType.getByCatType(cat.getCatVariant()).ordinal());

        return tag;
    }

    @Override
    public void doAtSpawn(Cat cat, Player player) {
        cat.setOwnerUUID(player.getUUID());
        cat.setTame(true);
    }

    @Override
    public List<CompoundTag> getDefaultPetCarriers() {
        var list = new ArrayList<CompoundTag>();
        for (EnumCatType species : EnumCatType.values()) {
            var infoTag = new CompoundTag();
            infoTag.putInt("CatType", species.ordinal());

            var entityTag = new CompoundTag();
            entityTag.putString("variant", Registry.CAT_VARIANT.getKey(species.getCatVariant()).toString());

            list.add(getDefaultPetCarrier(infoTag, entityTag));
        }

        return list;
    }

}

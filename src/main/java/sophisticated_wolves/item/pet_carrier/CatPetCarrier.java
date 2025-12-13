package sophisticated_wolves.item.pet_carrier;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
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

    private static final String CAT_TYPE = "CatType";
    private static final String CAT_VARIANT = "variant";

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

        private final ResourceKey<CatVariant> key;
        private final CatVariant variant;

        EnumCatType(ResourceKey<CatVariant> key) {
            this.key = key;
            this.variant = BuiltInRegistries.CAT_VARIANT.getOrThrow(key);
        }

        public static EnumCatType getByNum(int num) {
            if (num >= 0 && num < EnumCatType.values().length) {
                return EnumCatType.values()[num];
            } else {
                return TABBY;
            }
        }

        public static EnumCatType getByCatVariant(CatVariant catVariant) {
            for (var catType : values()) {
                if (catType.variant.equals(catVariant)) {
                    return catType;
                }
            }
            return TABBY;
        }

        public CatVariant getVariant() {
            return variant;
        }

        public ResourceKey<CatVariant> getKey() {
            return key;
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
        if (infoTag.contains(CAT_TYPE)) {
            return List.of(Component.translatable("sophisticated_wolves.carrier.type")
                    .append(" - ")
                    .append(Component.translatable(
                            "sophisticated_wolves.cat_type." + EnumCatType.getByNum(infoTag.getInt(CAT_TYPE))
                                    .toString().toLowerCase())));
        }
        return null;
    }

    @Override
    public CompoundTag getInfo(Cat cat) {
        var tag = new CompoundTag();
        tag.putInt(CAT_TYPE, EnumCatType.getByCatVariant(cat.getVariant().value()).ordinal());

        return tag;
    }

    @Override
    public void doAtSpawn(Cat cat, Player player) {
        cat.setOwnerUUID(player.getUUID());
        cat.setTame(true, true);
    }

    @Override
    public List<CompoundTag> getDefaultPetCarriers() {
        var list = new ArrayList<CompoundTag>();
        for (var catType : EnumCatType.values()) {
            var infoTag = new CompoundTag();
            infoTag.putInt(CAT_TYPE, catType.ordinal());

            var entityTag = new CompoundTag();
            entityTag.putString(CAT_VARIANT, catType.getKey().location().toString());

            list.add(getDefaultPetCarrier(infoTag, entityTag));
        }

        return list;
    }

}

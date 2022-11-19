package sophisticated_wolves.item.pet_carrier;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Fox;
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
public class FoxPetCarrier extends PetCarrier<Fox> {

    public enum EnumFoxType {
        RED(Fox.Type.RED),
        SNOW(Fox.Type.SNOW);

        private final Fox.Type foxType;

        EnumFoxType(Fox.Type foxType) {
            this.foxType = foxType;
        }

        public static EnumFoxType getByNum(int num) {
            if (num >= 0 && num < values().length) {
                return values()[num];
            } else {
                return RED;
            }
        }

        public static EnumFoxType getByFoxType(Fox.Type foxType) {
            for (var type : values()) {
                if (type.getFoxType().equals(foxType)) {
                    return type;
                }
            }
            return RED;
        }

        public Fox.Type getFoxType() {
            return foxType;
        }

    }

    @Override
    public Class getPetClass() {
        return Fox.class;
    }

    @Override
    public String getPetNameLocalizationKey() {
        return "entity.minecraft.fox";
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.FOX;
    }

    @Override
    public List<Component> getInfo(CompoundTag infoTag) {
        if (infoTag.contains("FoxType")) {
            return List.of(Component.translatable("sophisticated_wolves.carrier.type")
                    .append(" - ")
                    .append(Component.translatable(
                            "sophisticated_wolves.fox_type." + EnumFoxType.getByNum(infoTag.getInt("FoxType"))
                                    .toString().toLowerCase())));
        }
        return null;
    }

    @Override
    public CompoundTag getInfo(Fox fox) {
        var tag = new CompoundTag();
        tag.putInt("FoxType", EnumFoxType.getByFoxType(fox.getFoxType()).ordinal());

        return tag;
    }

    @Override
    public void doAtSpawn(Fox fox, Player player) {
        fox.addTrustedUUID(player.getUUID());
    }

    @Override
    public List<CompoundTag> getDefaultPetCarriers() {
        var list = new ArrayList<CompoundTag>();
        for (EnumFoxType species : EnumFoxType.values()) {
            var infoTag = new CompoundTag();
            infoTag.putInt("FoxType", species.ordinal());

            var entityTag = new CompoundTag();
            entityTag.putString("Type", species.getFoxType().getName());

            list.add(getDefaultPetCarrier(infoTag, entityTag));
        }

        return list;
    }

}

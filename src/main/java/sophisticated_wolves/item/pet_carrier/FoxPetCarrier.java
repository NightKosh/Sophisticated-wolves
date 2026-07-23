package sophisticated_wolves.item.pet_carrier;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.animal.fox.Fox;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.storage.TagValueInput;
import net.minecraft.world.level.storage.TagValueOutput;
import sophisticated_wolves.api.pet_carrier.PetCarrier;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class FoxPetCarrier extends PetCarrier<Fox> {

    private static final String FOX_TYPE = "FoxType";

    public enum EnumFoxType {
        RED(Fox.Variant.RED),
        SNOW(Fox.Variant.SNOW);

        private final Fox.Variant foxType;

        EnumFoxType(Fox.Variant foxType) {
            this.foxType = foxType;
        }

        public static EnumFoxType getByNum(int num) {
            if (num >= 0 && num < values().length) {
                return values()[num];
            } else {
                return RED;
            }
        }

        public static EnumFoxType getByFoxType(Fox.Variant foxType) {
            for (var type : values()) {
                if (type.getFoxType().equals(foxType)) {
                    return type;
                }
            }
            return RED;
        }

        public Fox.Variant getFoxType() {
            return foxType;
        }

    }

    @Override
    public Class<Fox> getPetClass() {
        return Fox.class;
    }

    @Override
    public String getPetNameLocalizationKey() {
        return "entity.minecraft.fox";
    }

    @Override
    public void readPetData(Fox pet, CompoundTag tag) {
        pet.readAdditionalSaveData(TagValueInput.create(
                ProblemReporter.DISCARDING,
                pet.level().registryAccess(),
                tag));
    }

    @Nullable
    @Override
    public CompoundTag addPetData(Fox pet) {
        var output = TagValueOutput.createWithContext(
                ProblemReporter.DISCARDING,
                pet.level().registryAccess());
        pet.addAdditionalSaveData(output);
        return output.buildResult();
    }

    @Override
    public EntityType<Fox> getEntityType() {
        return EntityTypes.FOX;
    }

    @Override
    public List<Component> getInfo(CompoundTag infoTag) {
        if (infoTag.contains(FOX_TYPE)) {
            return List.of(Component.translatable("sophisticated_wolves.carrier.type")
                    .append(" - ")
                    .append(Component.translatable(
                            "sophisticated_wolves.fox_type." + EnumFoxType.getByNum(infoTag.getIntOr(FOX_TYPE, 0))
                                    .toString().toLowerCase())));
        }
        return null;
    }

    @Override
    public CompoundTag getInfo(Fox fox) {
        var tag = new CompoundTag();
        tag.putInt(FOX_TYPE, fox.getVariant().getId());

        return tag;
    }

    @Override
    public void doAtSpawn(Fox fox, Player player) {
        fox.addTrustedEntity(player);
    }

    @Override
    public List<CompoundTag> getDefaultPetCarriers() {
        var list = new ArrayList<CompoundTag>();
        for (var species : EnumFoxType.values()) {
            var infoTag = new CompoundTag();
            infoTag.putInt(FOX_TYPE, species.ordinal());

            var entityTag = new CompoundTag();
            entityTag.putString("Type", species.getFoxType().getSerializedName());

            list.add(getDefaultPetCarrier(infoTag, entityTag));
        }

        return list;
    }

}

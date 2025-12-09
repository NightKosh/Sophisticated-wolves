package sophisticated_wolves.item.pet_carrier;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Parrot;
import sophisticated_wolves.api.pet_carrier.PetCarrier;

import java.util.ArrayList;
import java.util.List;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class ParrotPetCarrier extends PetCarrier<Parrot> {

    public static final int[] PARROTS_SPECIES = {0, 1, 2, 3, 4};
    //0 = red, 1 = blue, 2 = green, 3 = cyan, 4 = silver.

    @Override
    public Class getPetClass() {
        return Parrot.class;
    }

    @Override
    public String getPetNameLocalizationKey() {
        return "entity.minecraft.parrot";
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.PARROT;
    }

    @Override
    public List<Component> getInfo(CompoundTag infoTag) {
        if (infoTag.contains("Variant")) {
            return List.of(Component.translatable("sophisticated_wolves.carrier.type")
                    .append(" - ")
                    .append(Component.translatable("sophisticated_wolves.parrot_type." + infoTag.getInt("Variant"))));
        }
        return null;
    }

    @Override
    public CompoundTag getInfo(Parrot parrot) {
        var tag = new CompoundTag();
        tag.putInt("Variant", parrot.getVariant().getId());

        return tag;
    }

    @Override
    public List<CompoundTag> getDefaultPetCarriers() {
        var list = new ArrayList<CompoundTag>();
        for (int species : PARROTS_SPECIES) {
            var infoTag = new CompoundTag();
            infoTag.putInt("Variant", species);

            var entityTag = new CompoundTag();
            entityTag.putInt("Variant", species);

            list.add(getDefaultPetCarrier(infoTag, entityTag));
        }

        return list;
    }

}

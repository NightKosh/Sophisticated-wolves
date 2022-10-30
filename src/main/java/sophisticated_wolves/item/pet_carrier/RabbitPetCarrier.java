package sophisticated_wolves.item.pet_carrier;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Rabbit;
import sophisticated_wolves.api.pet_carrier.PetCarrier;

import java.util.ArrayList;
import java.util.List;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class RabbitPetCarrier extends PetCarrier {

    public static final int[] RABBITS_SPECIES = {0, 1, 2, 3, 4, 5, 99};

    @Override
    public Class getPetClass() {
        return Rabbit.class;
    }

    @Override
    public String getPetNameLocalizationKey() {
        return "entity.minecraft.rabbit";
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.RABBIT;
    }

    @Override
    public List<Component> getInfo(CompoundTag infoTag) {
        if (infoTag.contains("RabbitType")) {
            var list = new ArrayList<Component>(1);
            list.add(Component.translatable("carrier.rabbit_type")
                    .append(" - ")
                    .append(Component.translatable("rabbit_type." + infoTag.getInt("RabbitType"))));
            return list;
        }
        return null;
    }

    @Override
    public CompoundTag getInfo(LivingEntity pet) {
        var tag = new CompoundTag();
        tag.putInt("RabbitType", ((Rabbit) pet).getRabbitType());

        return tag;
    }

    @Override
    public List<CompoundTag> getDefaultPetCarriers() {
        var list = new ArrayList<CompoundTag>();
        for (int species : RABBITS_SPECIES) {
            var infoTag = new CompoundTag();
            infoTag.putInt("RabbitType", species);

            var entityTag = new CompoundTag();
            entityTag.putInt("RabbitType", species);

            list.add(getDefaultPetCarrier(infoTag, entityTag));
        }

        return list;
    }

}

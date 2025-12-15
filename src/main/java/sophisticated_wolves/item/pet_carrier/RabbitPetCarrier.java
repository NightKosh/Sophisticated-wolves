package sophisticated_wolves.item.pet_carrier;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.rabbit.Rabbit;
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
public class RabbitPetCarrier extends PetCarrier<Rabbit> {

    private static final String RABBIT_TYPE = "RabbitType";

    public static final int[] RABBITS_SPECIES = {0, 1, 2, 3, 4, 5, 99};

    @Override
    public Class<Rabbit> getPetClass() {
        return Rabbit.class;
    }

    @Override
    public String getPetNameLocalizationKey() {
        return "entity.minecraft.rabbit";
    }

    @Override
    public EntityType<Rabbit> getEntityType() {
        return EntityType.RABBIT;
    }

    @Override
    public void readPetData(Rabbit pet, CompoundTag tag) {
        pet.readAdditionalSaveData(TagValueInput.create(
                ProblemReporter.DISCARDING,
                pet.level().registryAccess(),
                tag));
    }

    @Nullable
    @Override
    public CompoundTag addPetData(Rabbit pet) {
        var output = TagValueOutput.createWithContext(
                ProblemReporter.DISCARDING,
                pet.level().registryAccess());
        pet.addAdditionalSaveData(output);
        return output.buildResult();
    }

    @Override
    public List<Component> getInfo(CompoundTag infoTag) {
        if (infoTag.contains(RABBIT_TYPE)) {
            return List.of(Component.translatable("sophisticated_wolves.carrier.type")
                    .append(" - ")
                    .append(Component.translatable("sophisticated_wolves.rabbit_type." + infoTag.getIntOr(RABBIT_TYPE, 0))));
        }
        return null;
    }

    @Override
    public CompoundTag getInfo(Rabbit rabbit) {
        var tag = new CompoundTag();
        tag.putInt(RABBIT_TYPE, rabbit.getVariant().id());

        return tag;
    }

    @Override
    public List<CompoundTag> getDefaultPetCarriers() {
        var list = new ArrayList<CompoundTag>();
        for (int species : RABBITS_SPECIES) {
            var infoTag = new CompoundTag();
            infoTag.putInt(RABBIT_TYPE, species);

            var entityTag = new CompoundTag();
            entityTag.putInt(RABBIT_TYPE, species);

            list.add(getDefaultPetCarrier(infoTag, entityTag));
        }

        return list;
    }

}

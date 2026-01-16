package sophisticated_wolves.item.pet_carrier;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.parrot.Parrot;
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
public class ParrotPetCarrier extends PetCarrier<Parrot> {

    private static final String VARIANT = "Variant";

    public static final int[] PARROTS_SPECIES = {0, 1, 2, 3, 4};
    //0 = red, 1 = blue, 2 = green, 3 = cyan, 4 = silver.

    @Override
    public Class<Parrot> getPetClass() {
        return Parrot.class;
    }

    @Override
    public String getPetNameLocalizationKey() {
        return "entity.minecraft.parrot";
    }

    @Override
    public EntityType<Parrot> getEntityType() {
        return EntityType.PARROT;
    }

    @Override
    public void readPetData(Parrot pet, CompoundTag tag) {
        pet.readAdditionalSaveData(TagValueInput.create(
                ProblemReporter.DISCARDING,
                pet.level().registryAccess(),
                tag));
    }

    @Nullable
    @Override
    public CompoundTag addPetData(Parrot pet) {
        var output = TagValueOutput.createWithContext(
                ProblemReporter.DISCARDING,
                pet.level().registryAccess());
        pet.addAdditionalSaveData(output);
        return output.buildResult();
    }

    @Override
    public List<Component> getInfo(CompoundTag infoTag) {
        if (infoTag.contains(VARIANT)) {
            return List.of(Component.translatable("sophisticated_wolves.carrier.type")
                    .append(" - ")
                    .append(Component.translatable("sophisticated_wolves.parrot_type." + infoTag.getIntOr(VARIANT, 0))));
        }
        return null;
    }

    @Override
    public CompoundTag getInfo(Parrot parrot) {
        var tag = new CompoundTag();
        tag.putInt(VARIANT, parrot.getVariant().getId());

        return tag;
    }

    @Override
    public List<CompoundTag> getDefaultPetCarriers() {
        var list = new ArrayList<CompoundTag>();
        for (int species : PARROTS_SPECIES) {
            var infoTag = new CompoundTag();
            infoTag.putInt(VARIANT, species);

            var entityTag = new CompoundTag();
            entityTag.putInt(VARIANT, species);

            list.add(getDefaultPetCarrier(infoTag, entityTag));
        }

        return list;
    }

    @Override
    public void doAtSpawn(Parrot parrot, Player player) {
        parrot.setOwner(player);
        parrot.setTame(true, true);
    }

}

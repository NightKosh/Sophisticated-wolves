package sophisticated_wolves.item.pet_carrier;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.animal.chicken.Chicken;
import net.minecraft.world.entity.animal.chicken.ChickenVariant;
import net.minecraft.world.entity.animal.chicken.ChickenVariants;
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
public class ChickenPetCarrier extends PetCarrier<Chicken> {

    private static final String CHICKEN_TYPE = "ChickenType";
    private static final String CHICKEN_VARIANT = "variant";

    public enum EnumChickenType {
        TEMPERATE(ChickenVariants.TEMPERATE),
        WARM(ChickenVariants.WARM),
        COLD(ChickenVariants.COLD);

        private final ResourceKey<ChickenVariant> key;

        EnumChickenType(ResourceKey<ChickenVariant> key) {
            this.key = key;
        }

        public ResourceKey<ChickenVariant> getKey() {
            return key;
        }

    }

    @Override
    public Class<Chicken> getPetClass() {
        return Chicken.class;
    }

    @Override
    public String getPetNameLocalizationKey() {
        return "entity.minecraft.chicken";
    }

    @Override
    public EntityType<Chicken> getEntityType() {
        return EntityTypes.CHICKEN;
    }

    @Override
    public void readPetData(Chicken chicken, CompoundTag tag) {
        chicken.readAdditionalSaveData(TagValueInput.create(
                ProblemReporter.DISCARDING,
                chicken.level().registryAccess(),
                tag));
    }

    @Nullable
    @Override
    public CompoundTag addPetData(Chicken chicken) {
        var output = TagValueOutput.createWithContext(
                ProblemReporter.DISCARDING,
                chicken.level().registryAccess());
        chicken.addAdditionalSaveData(output);
        return output.buildResult();
    }

    @Override
    public List<Component> getInfo(CompoundTag infoTag) {
        if (infoTag.contains(CHICKEN_TYPE)) {
            return List.of(Component.translatable("sophisticated_wolves.carrier.type")
                    .append(" - ")
                    .append(Component.translatable(
                            "sophisticated_wolves.chicken_type." +
                                    infoTag.getStringOr(CHICKEN_TYPE, "minecraft:temperate_chicken")
                                            .split(":", 2)[1])));
        }
        return null;
    }

    @Override
    public CompoundTag getInfo(Chicken chicken) {
        var tag = new CompoundTag();
        tag.putString(CHICKEN_TYPE, chicken.getVariant().getKey().identifier().toString());

        return tag;
    }

    @Override
    public List<CompoundTag> getDefaultPetCarriers() {
        var list = new ArrayList<CompoundTag>();
        for (var chickenType : EnumChickenType.values()) {
            String variant = chickenType.getKey().identifier().toString();
            var infoTag = new CompoundTag();
            infoTag.putString(CHICKEN_TYPE, variant);

            var entityTag = new CompoundTag();
            entityTag.putString(CHICKEN_VARIANT, variant);

            list.add(getDefaultPetCarrier(infoTag, entityTag));
        }

        return list;
    }

}

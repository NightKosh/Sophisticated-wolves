package sophisticated_wolves.item.pet_carrier;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.feline.Cat;
import net.minecraft.world.entity.animal.feline.CatVariant;
import net.minecraft.world.entity.animal.feline.CatVariants;
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
public class CatPetCarrier extends PetCarrier<Cat> {

    private static final String CAT_TYPE = "CatType";
    private static final String CAT_VARIANT = "variant";

    public enum EnumCatType {
        TABBY(CatVariants.TABBY),
        BLACK(CatVariants.BLACK),
        RED(CatVariants.RED),
        SIAMESE(CatVariants.SIAMESE),
        BRITISH_SHORTHAIR(CatVariants.BRITISH_SHORTHAIR),
        CALICO(CatVariants.CALICO),
        PERSIAN(CatVariants.PERSIAN),
        RAGDOLL(CatVariants.RAGDOLL),
        WHITE(CatVariants.WHITE),
        JELLIE(CatVariants.JELLIE),
        ALL_BLACK(CatVariants.ALL_BLACK);

        private final ResourceKey<CatVariant> key;

        EnumCatType(ResourceKey<CatVariant> key) {
            this.key = key;
        }

        public ResourceKey<CatVariant> getKey() {
            return key;
        }

    }

    @Override
    public Class<Cat> getPetClass() {
        return Cat.class;
    }

    @Override
    public String getPetNameLocalizationKey() {
        return "entity.minecraft.cat";
    }

    @Override
    public void readPetData(Cat pet, CompoundTag tag) {
        pet.readAdditionalSaveData(TagValueInput.create(
                ProblemReporter.DISCARDING,
                pet.level().registryAccess(),
                tag));
    }

    @Nullable
    @Override
    public CompoundTag addPetData(Cat pet) {
        var output = TagValueOutput.createWithContext(
                ProblemReporter.DISCARDING,
                pet.level().registryAccess());
        pet.addAdditionalSaveData(output);
        return output.buildResult();
    }

    @Override
    public EntityType<Cat> getEntityType() {
        return EntityType.CAT;
    }

    @Override
    public List<Component> getInfo(CompoundTag infoTag) {
        if (infoTag.contains(CAT_TYPE)) {
            return List.of(Component.translatable("sophisticated_wolves.carrier.type")
                    .append(" - ")
                    .append(Component.translatable(
                            "sophisticated_wolves.cat_type." + infoTag.getStringOr(CAT_TYPE, "minecraft:tabby")
                                    .split(":", 2)[1])));
        }
        return null;
    }

    @Override
    public CompoundTag getInfo(Cat cat) {
        var tag = new CompoundTag();
        tag.putString(CAT_TYPE, cat.getVariant().getKey().identifier().toString());

        return tag;
    }

    @Override
    public void doAtSpawn(Cat cat, Player player) {
        cat.setOwner(player);
        cat.setTame(true, true);
    }

    @Override
    public List<CompoundTag> getDefaultPetCarriers() {
        var list = new ArrayList<CompoundTag>();
        for (var catType : EnumCatType.values()) {
            String variant = catType.getKey().identifier().toString();
            var infoTag = new CompoundTag();
            infoTag.putString(CAT_TYPE, variant);

            var entityTag = new CompoundTag();
            entityTag.putString(CAT_VARIANT, variant);

            list.add(getDefaultPetCarrier(infoTag, entityTag));
        }

        return list;
    }

}

package sophisticated_wolves.item.pet_carrier;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.animal.wolf.Wolf;
import net.minecraft.world.entity.animal.wolf.WolfVariant;
import net.minecraft.world.entity.animal.wolf.WolfVariants;
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
public class WolfPetCarrier extends PetCarrier<Wolf> {

    public static final String HAS_ARMOR = "HasArmor";
    public static final String WOLF_TYPE = "WolfType";
    public static final String WOLF_VARIANT = "variant";

    public enum EnumWolfType {
        PALE(WolfVariants.PALE),
        SPOTTED(WolfVariants.SPOTTED),
        SNOWY(WolfVariants.SNOWY),
        BLACK(WolfVariants.BLACK),
        ASHEN(WolfVariants.ASHEN),
        RUSTY(WolfVariants.RUSTY),
        WOODS(WolfVariants.WOODS),
        CHESTNUT(WolfVariants.CHESTNUT),
        STRIPED(WolfVariants.STRIPED);

        private final ResourceKey<WolfVariant> key;

        EnumWolfType(ResourceKey<WolfVariant> key) {
            this.key = key;
        }

        public ResourceKey<WolfVariant> getKey() {
            return key;
        }

    }

    @Override
    public Class<Wolf> getPetClass() {
        return Wolf.class;
    }

    @Override
    public String getPetNameLocalizationKey() {
        return "entity.minecraft.wolf";
    }

    @Override
    public void readPetData(Wolf pet, CompoundTag tag) {
        pet.readAdditionalSaveData(TagValueInput.create(
                ProblemReporter.DISCARDING,
                pet.level().registryAccess(),
                tag));
    }

    @Nullable
    @Override
    public CompoundTag addPetData(Wolf pet) {
        var output = TagValueOutput.createWithContext(
                ProblemReporter.DISCARDING,
                pet.level().registryAccess());
        pet.addAdditionalSaveData(output);
        return output.buildResult();
    }

    @Override
    public List<Component> getInfo(CompoundTag infoTag) {
        if (infoTag.contains(WOLF_TYPE)) {
            List<Component> components = new ArrayList<>();
            if (infoTag.contains(HAS_ARMOR) && infoTag.getBooleanOr(HAS_ARMOR, false)) {
                components.add(Component.translatable("sophisticated_wolves.carrier.has_armor"));
            }

            components.add(Component.translatable("sophisticated_wolves.carrier.type")
                    .append(" - ")
                    .append(Component.translatable(
                            "sophisticated_wolves.wolf_type." + infoTag.getStringOr(WOLF_TYPE, "minecraft:pale")
                                    .split(":", 2)[1])));
            return components;
        }
        return null;
    }

    @Override
    public CompoundTag getInfo(Wolf wolf) {
        var tag = new CompoundTag();
        tag.putString(WOLF_TYPE, wolf.getVariant().getKey().identifier().toString());
        tag.putBoolean(HAS_ARMOR, wolf.isWearingBodyArmor());

        return tag;
    }

    @Override
    public List<CompoundTag> getDefaultPetCarriers() {
        var list = new ArrayList<CompoundTag>();
        for (var wolfType : EnumWolfType.values()) {
            String variant = wolfType.getKey().identifier().toString();
            var infoTag = new CompoundTag();
            infoTag.putString(WOLF_TYPE, variant);

            var entityTag = new CompoundTag();
            entityTag.putString(WOLF_VARIANT, variant);

            list.add(getDefaultPetCarrier(infoTag, entityTag));
        }

        return list;
    }

    @Override
    public void doAtSpawn(Wolf wolf, Player player) {
        wolf.setOwner(player);
        wolf.setTame(true, true);
    }

    @Override
    public EntityType<Wolf> getEntityType() {
        return EntityTypes.WOLF;
    }

}

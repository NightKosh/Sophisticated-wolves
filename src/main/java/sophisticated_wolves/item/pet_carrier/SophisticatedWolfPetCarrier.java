package sophisticated_wolves.item.pet_carrier;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.storage.TagValueInput;
import net.minecraft.world.level.storage.TagValueOutput;
import sophisticated_wolves.api.pet_carrier.PetCarrier;
import sophisticated_wolves.core.SWEntities;
import sophisticated_wolves.entity.SophisticatedWolf;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static sophisticated_wolves.item.pet_carrier.WolfPetCarrier.*;
import static sophisticated_wolves.item.pet_carrier.WolfPetCarrier.EnumWolfType;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class SophisticatedWolfPetCarrier extends PetCarrier<SophisticatedWolf> {

    @Override
    public Class<SophisticatedWolf> getPetClass() {
        return SophisticatedWolf.class;
    }

    @Override
    public String getPetNameLocalizationKey() {
        return SWEntities.SW_NAME;
    }

    @Override
    public EntityType<SophisticatedWolf> getEntityType() {
        return SWEntities.getSophisticatedWolfType();
    }

    @Override
    public void readPetData(SophisticatedWolf pet, CompoundTag tag) {
        pet.readAdditionalSaveData(TagValueInput.create(
                ProblemReporter.DISCARDING,
                pet.level().registryAccess(),
                tag));
    }

    @Nullable
    @Override
    public CompoundTag addPetData(SophisticatedWolf pet) {
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
    public CompoundTag getInfo(SophisticatedWolf wolf) {
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
    public void doAtSpawn(SophisticatedWolf wolf, Player player) {
        wolf.setOwner(player);
        wolf.setTame(true, true);
        wolf.updateCommands(true, false);
    }

}

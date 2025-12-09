package sophisticated_wolves.item.pet_carrier;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import sophisticated_wolves.api.EnumWolfSpecies;
import sophisticated_wolves.api.pet_carrier.PetCarrier;
import sophisticated_wolves.compatibility.Compatibility;
import sophisticated_wolves.core.SWEntities;
import sophisticated_wolves.entity.SophisticatedWolf;

import java.util.ArrayList;
import java.util.List;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class SophisticatedWolfPetCarrier extends PetCarrier<SophisticatedWolf> {

    private static final String WOLF_TYPE = "WolfType";

    @Override
    public Class getPetClass() {
        return SophisticatedWolf.class;
    }

    @Override
    public String getPetNameLocalizationKey() {
        return SWEntities.SW_NAME;
    }

    @Override
    public EntityType getEntityType() {
        return SWEntities.getSophisticatedWolfType();
    }

    @Override
    public List<Component> getInfo(CompoundTag infoTag) {
        if (infoTag.contains(WOLF_TYPE)) {
            return List.of(Component.translatable("sophisticated_wolves.carrier.type")
                    .append(" - ")
                    .append(Component.translatable(
                            EnumWolfSpecies.getSpeciesByNum(infoTag.getInt(WOLF_TYPE)).getSpeciesStr())));
        }
        return null;
    }

    @Override
    public CompoundTag getInfo(SophisticatedWolf wolf) {
        var tag = new CompoundTag();
        tag.putInt(WOLF_TYPE, wolf.getSpecies().ordinal());

        return tag;
    }

    @Override
    public CompoundTag getAdditionalData(SophisticatedWolf wolf) {
        var tag = new CompoundTag();
        if (Compatibility.IS_WOLF_ARMOR_INSTALLED) {
            //TODO remove?
            //CompatibilityWolfArmor.storeWolfItems(wolf, tag);
        }
        return tag;
    }

    @Override
    public void setAdditionalData(SophisticatedWolf wolf, CompoundTag tag) {
        if (Compatibility.IS_WOLF_ARMOR_INSTALLED) {
            //TODO remove?
            //CompatibilityWolfArmor.getWolfItems(wolf, tag);
        }
    }

    @Override
    public void doAtSpawn(SophisticatedWolf wolf, Player player) {
        wolf.setOwnerUUID(player.getUUID());
        wolf.setTame(true);
        wolf.updateCommands(true, false);
    }

    @Override
    public List<CompoundTag> getDefaultPetCarriers() {
        var list = new ArrayList<CompoundTag>();
        for (EnumWolfSpecies species : EnumWolfSpecies.values()) {
            var infoTag = new CompoundTag();
            infoTag.putInt(WOLF_TYPE, species.ordinal());

            var entityTag = new CompoundTag();
            entityTag.putInt("Species", species.ordinal());

            list.add(getDefaultPetCarrier(infoTag, entityTag));
        }

        return list;
    }

}

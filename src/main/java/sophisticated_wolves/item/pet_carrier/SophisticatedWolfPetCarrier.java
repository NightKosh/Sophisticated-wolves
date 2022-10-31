package sophisticated_wolves.item.pet_carrier;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
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
public class SophisticatedWolfPetCarrier extends PetCarrier {

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
        if (infoTag.contains("WolfType")) {
            var list = new ArrayList<Component>(1);
            list.add(Component.translatable("carrier.dog_type")
                    .append(" - ")
                    .append(Component.translatable(
                            "wolf_type." + EnumWolfSpecies.getSpeciesByNum(infoTag.getInt("WolfType"))
                                    .toString()
                                    .toLowerCase())));
            return list;
        }
        return null;
    }

    @Override
    public CompoundTag getInfo(LivingEntity pet) {
        var tag = new CompoundTag();
        tag.putInt("WolfType", ((SophisticatedWolf) pet).getSpecies().ordinal());

        return tag;
    }

    @Override
    public CompoundTag getAdditionalData(LivingEntity pet) {
        var tag = new CompoundTag();
        if (Compatibility.IS_WOLF_ARMOR_INSTALLED) {
            //TODO
//            CompatibilityWolfArmor.storeWolfItems((Wolf) pet, tag);
        }
        return tag;
    }

    @Override
    public void setAdditionalData(Entity pet, CompoundTag tag) {
        if (Compatibility.IS_WOLF_ARMOR_INSTALLED) {
            //TODO
//            CompatibilityWolfArmor.getWolfItems((Wolf) pet, tag);
        }
    }

    @Override
    public List<CompoundTag> getDefaultPetCarriers() {
        var list = new ArrayList<CompoundTag>();
        for (EnumWolfSpecies species : EnumWolfSpecies.values()) {
            var infoTag = new CompoundTag();
            infoTag.putInt("WolfType", species.ordinal());

            var entityTag = new CompoundTag();
            entityTag.putInt("Species", species.ordinal());

            list.add(getDefaultPetCarrier(infoTag, entityTag));
        }

        return list;
    }

}

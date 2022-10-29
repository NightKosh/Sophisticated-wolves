package sophisticated_wolves.item.pet_carrier;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import sophisticated_wolves.api.pet_carrier.PetCarrier;
import sophisticated_wolves.compatibility.Compatibility;
import sophisticated_wolves.compatibility.CompatibilityWolfArmor;

import java.util.ArrayList;
import java.util.List;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class WolfPetCarrier extends PetCarrier {

    @Override
    public Class getPetClass() {
        return Wolf.class;
    }

    @Override
    public String getPetNameLocalizationKey() {
        return "entity.minecraft.wolf";
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
    public void setAdditionalData(LivingEntity pet, CompoundTag tag) {
        if (Compatibility.IS_WOLF_ARMOR_INSTALLED) {
            //TODO
//            CompatibilityWolfArmor.getWolfItems((Wolf) pet, tag);
        }
    }

    @Override
    public Mob spawnPet(Level level, Player player) {
        return EntityType.WOLF.create(level);
    }

    @Override
    public List<CompoundTag> getDefaultPetCarriers() {
        var list = new ArrayList<CompoundTag>();
        list.add(getDefaultPetCarrier(null, null));

        return list;
    }

}

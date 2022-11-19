package sophisticated_wolves.item.pet_carrier;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import sophisticated_wolves.api.pet_carrier.PetCarrier;
import sophisticated_wolves.compatibility.Compatibility;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class WolfPetCarrier extends PetCarrier<Wolf> {

    @Override
    public Class getPetClass() {
        return Wolf.class;
    }

    @Override
    public String getPetNameLocalizationKey() {
        return "entity.minecraft.wolf";
    }

    @Override
    public CompoundTag getAdditionalData(Wolf wolf) {
        var tag = new CompoundTag();
        if (Compatibility.IS_WOLF_ARMOR_INSTALLED) {
            //TODO remove?
            //CompatibilityWolfArmor.storeWolfItems(wolf, tag);
        }
        return tag;
    }

    @Override
    public void setAdditionalData(Wolf wolf, CompoundTag tag) {
        if (Compatibility.IS_WOLF_ARMOR_INSTALLED) {
            //TODO remove?
            //CompatibilityWolfArmor.getWolfItems(wolf, tag);
        }
    }

    @Override
    public void doAtSpawn(Wolf wolf, Player player) {
        wolf.setOwnerUUID(player.getUUID());
        wolf.setTame(true);
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.WOLF;
    }

}

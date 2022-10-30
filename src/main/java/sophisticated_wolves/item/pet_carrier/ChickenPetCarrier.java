package sophisticated_wolves.item.pet_carrier;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Chicken;
import sophisticated_wolves.api.pet_carrier.PetCarrier;

import java.util.ArrayList;
import java.util.List;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class ChickenPetCarrier extends PetCarrier {

    @Override
    public Class getPetClass() {
        return Chicken.class;
    }

    @Override
    public String getPetNameLocalizationKey() {
        return "entity.minecraft.chicken";
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.CHICKEN;
    }

    @Override
    public List<CompoundTag> getDefaultPetCarriers() {
        var list = new ArrayList<CompoundTag>();
        list.add(getDefaultPetCarrier(null, null));

        return list;
    }

}

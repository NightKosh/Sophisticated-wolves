package sophisticated_wolves.item.pet_carrier;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.chicken.Chicken;
import sophisticated_wolves.api.pet_carrier.PetCarrier;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class ChickenPetCarrier extends PetCarrier<Chicken> {

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
        return EntityType.CHICKEN;
    }

}

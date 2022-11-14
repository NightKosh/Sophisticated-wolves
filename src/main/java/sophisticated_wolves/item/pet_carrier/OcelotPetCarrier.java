package sophisticated_wolves.item.pet_carrier;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Ocelot;
import sophisticated_wolves.api.pet_carrier.PetCarrier;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class OcelotPetCarrier extends PetCarrier<Ocelot> {

    @Override
    public Class getPetClass() {
        return Ocelot.class;
    }

    @Override
    public String getPetNameLocalizationKey() {
        return "entity.minecraft.ocelot";
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.OCELOT;
    }

}

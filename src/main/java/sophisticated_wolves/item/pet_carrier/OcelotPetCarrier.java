package sophisticated_wolves.item.pet_carrier;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.animal.feline.Ocelot;
import sophisticated_wolves.api.pet_carrier.PetCarrier;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class OcelotPetCarrier extends PetCarrier<Ocelot> {

    @Override
    public Class<Ocelot> getPetClass() {
        return Ocelot.class;
    }

    @Override
    public String getPetNameLocalizationKey() {
        return "entity.minecraft.ocelot";
    }

    @Override
    public EntityType<Ocelot> getEntityType() {
        return EntityTypes.OCELOT;
    }

}

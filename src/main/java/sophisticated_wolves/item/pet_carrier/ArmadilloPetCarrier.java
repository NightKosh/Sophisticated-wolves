package sophisticated_wolves.item.pet_carrier;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.armadillo.Armadillo;
import sophisticated_wolves.api.pet_carrier.PetCarrier;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class ArmadilloPetCarrier extends PetCarrier<Armadillo> {

    @Override
    public Class<Armadillo> getPetClass() {
        return Armadillo.class;
    }

    @Override
    public String getPetNameLocalizationKey() {
        return "entity.minecraft.armadillo";
    }

    @Override
    public EntityType<Armadillo> getEntityType() {
        return EntityType.ARMADILLO;
    }

}

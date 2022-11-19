package sophisticated_wolves.item.pet_carrier;

import sophisticated_wolves.api.pet_carrier.PetCarrierHandler;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public final class PetCarrierHelper extends PetCarrierHandler {

    private PetCarrierHelper() {
    }

    public static void addPetCarriers() {
        addPet(new WolfPetCarrier());
        addPet(new SophisticatedWolfPetCarrier());
        addPet(new CatPetCarrier());
        addPet(new ChickenPetCarrier());
        addPet(new RabbitPetCarrier());
        addPet(new ParrotPetCarrier());
        addPet(new OcelotPetCarrier());
        addPet(new FoxPetCarrier());
    }

}

package sophisticated_wolves.item.pet_carrier;

import net.minecraft.world.entity.Entity;
import sophisticated_wolves.api.pet_carrier.IPetCarrierHandler;
import sophisticated_wolves.api.pet_carrier.PetCarrier;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class PetCarrierHelper implements IPetCarrierHandler {

    public static final PetCarrierHelper INSTANCE = new PetCarrierHelper();

    public static final Map<String, PetCarrier<Entity>> PETS_MAP = new LinkedHashMap<>();

    private PetCarrierHelper() {
    }

    @Override
    public void addPet(PetCarrier petCarrier) {
        PETS_MAP.put(petCarrier.getPetClass().getSimpleName(), petCarrier);
    }

    public void addPetCarriers() {
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

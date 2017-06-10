package sophisticated_wolves.item.pet_carrier;

import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntityWolf;
import sophisticated_wolves.api.pet_carrier.IPetCarrierHandler;
import sophisticated_wolves.api.pet_carrier.PetCarrier;
import sophisticated_wolves.entity.EntitySophisticatedWolf;

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

    public static final Map<String, PetCarrier> PETS_MAP = new LinkedHashMap<>();

    private PetCarrierHelper() {}

    @Override
    public void addPet(Class petClass, PetCarrier petCarrier) {
        PETS_MAP.put(petClass.getSimpleName(), petCarrier);
    }

    public void addPetCarriers() {
        addPet(EntityWolf.class, new WolfPetCarrier());
        addPet(EntitySophisticatedWolf.class, new SophisticatedWolfPetCarrier());
        addPet(EntityOcelot.class, new CatPetCarrier());
        addPet(EntityChicken.class, new ChickenPetCarrier());
        addPet(EntityRabbit.class, new RabbitPetCarrier());
    }
}

package sophisticated_wolves.item.pet_carrier;

import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntityWolf;
import sophisticated_wolves.entity.EntitySophisticatedWolf;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class PetCarrierHelper {

    public static final Map<String, PetCarrier> PETS_MAP = new LinkedHashMap<>();

    public static void addPet(Class petClass, PetCarrier petCarrier) {
        PETS_MAP.put(petClass.getSimpleName(), petCarrier);
    }

    public static void addPetCarriers() {
        PetCarrierHelper.addPet(EntityWolf.class, new WolfPetCarrier());
        PetCarrierHelper.addPet(EntitySophisticatedWolf.class, new SophisticatedWolfPetCarrier());
        PetCarrierHelper.addPet(EntityOcelot.class, new CatPetCarrier());
        PetCarrierHelper.addPet(EntityChicken.class, new ChickenPetCarrier());
        PetCarrierHelper.addPet(EntityRabbit.class, new RabbitPetCarrier());
    }
}

package sophisticated_wolves.item.pet_carrier;

import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.Ocelot;
import net.minecraft.world.entity.animal.Parrot;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.entity.animal.Wolf;
import sophisticated_wolves.api.pet_carrier.IPetCarrierHandler;
import sophisticated_wolves.api.pet_carrier.PetCarrier;
import sophisticated_wolves.entity.SophisticatedWolf;

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

    private PetCarrierHelper() {
    }

    @Override
    public void addPet(Class petClass, PetCarrier petCarrier) {
        PETS_MAP.put(petClass.getSimpleName(), petCarrier);
    }

    public void addPetCarriers() {
        addPet(Wolf.class, new WolfPetCarrier());
        addPet(SophisticatedWolf.class, new SophisticatedWolfPetCarrier());
//TODO
//        addPet(Ocelot.class, new CatPetCarrier());
        addPet(Cat.class, new CatPetCarrier());
        addPet(Chicken.class, new ChickenPetCarrier());
        addPet(Rabbit.class, new RabbitPetCarrier());
        addPet(Parrot.class, new ParrotPetCarrier());
    }

}

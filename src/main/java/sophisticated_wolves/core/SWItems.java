package sophisticated_wolves.core;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import sophisticated_wolves.api.ModInfo;
import sophisticated_wolves.item.ItemDogTag;
import sophisticated_wolves.item.ItemDogTreat;
import sophisticated_wolves.item.ItemWhistle;
import sophisticated_wolves.item.pet_carrier.ItemPetCarrier;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class SWItems {

    public static final DeferredRegister<Item> ITEMS_REGISTER =
            DeferredRegister.create(ForgeRegistries.ITEMS, ModInfo.ID);

    private static final RegistryObject<Item> DOG_TAG = ITEMS_REGISTER.register("dog_tag", ItemDogTag::new);
    private static final RegistryObject<Item> DOG_TREAT = ITEMS_REGISTER.register("dog_treat", ItemDogTreat::new);
    private static final RegistryObject<Item> WHISTLE = ITEMS_REGISTER.register("whistle", ItemWhistle::new);
    private static final RegistryObject<Item> PET_CARRIER = ITEMS_REGISTER.register("pet_carrier", ItemPetCarrier::new);

    //TODO DOG_EGG -> ItemWolfEgg;

    public static void register(IEventBus eventBus) {
        ITEMS_REGISTER.register(eventBus);
    }

    public static Item getDogTag() {
        return DOG_TAG.get();
    }

    public static Item getDogTreat() {
        return DOG_TREAT.get();
    }

    public static Item getWhistle() {
        return WHISTLE.get();
    }

    public static Item getPetCarrier() {
        return PET_CARRIER.get();
    }

}

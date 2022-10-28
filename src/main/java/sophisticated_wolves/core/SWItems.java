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

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, ModInfo.ID);

    private static final RegistryObject<Item> DOG_TAG = ITEMS.register("dog_tag", ItemDogTag::new);
    private static final RegistryObject<Item> DOG_TREAT = ITEMS.register("dog_treat", ItemDogTreat::new);
    private static final RegistryObject<Item> WHISTLE = ITEMS.register("whistle", ItemWhistle::new);
    private static final RegistryObject<Item> PET_CARRIER = ITEMS.register("pet_carrier", ItemPetCarrier::new);

    //TODO DOG_EGG -> ItemWolfEgg;

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
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

package sophisticated_wolves.core;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import sophisticated_wolves.api.ModInfo;
import sophisticated_wolves.item.ItemDogTag;
import sophisticated_wolves.item.ItemDogTreat;
import sophisticated_wolves.item.ItemPetCarrier;
import sophisticated_wolves.item.ItemWhistle;

import static net.minecraft.resources.Identifier.fromNamespaceAndPath;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class SWItems {

    public static final ResourceKey<Item> DOG_TAG_RK = ResourceKey.create(Registries.ITEM, fromNamespaceAndPath(ModInfo.ID, "dog_tag"));
    public static final ResourceKey<Item> DOG_TREAT_RK = ResourceKey.create(Registries.ITEM, fromNamespaceAndPath(ModInfo.ID, "dog_treat"));
    public static final ResourceKey<Item> WHISTLE_RK = ResourceKey.create(Registries.ITEM, fromNamespaceAndPath(ModInfo.ID, "whistle"));
    public static final ResourceKey<Item> PET_CARRIER_RK = ResourceKey.create(Registries.ITEM, fromNamespaceAndPath(ModInfo.ID, "pet_carrier"));

    public static final DeferredRegister<Item> ITEMS_REGISTER =
            DeferredRegister.create(Registries.ITEM, ModInfo.ID);

    private static final DeferredHolder<Item, Item> DOG_TAG = ITEMS_REGISTER.register("dog_tag", ItemDogTag::new);
    private static final DeferredHolder<Item, Item> DOG_TREAT = ITEMS_REGISTER.register("dog_treat", ItemDogTreat::new);
    private static final DeferredHolder<Item, Item> WHISTLE = ITEMS_REGISTER.register("whistle", ItemWhistle::new);
    private static final DeferredHolder<Item, Item> PET_CARRIER = ITEMS_REGISTER.register("pet_carrier", ItemPetCarrier::new);


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

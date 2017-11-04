package sophisticated_wolves;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import sophisticated_wolves.api.ModInfo;
import sophisticated_wolves.item.ItemDogTag;
import sophisticated_wolves.item.ItemDogTreat;
import sophisticated_wolves.item.ItemWhistle;
import sophisticated_wolves.item.ItemWolfEgg;
import sophisticated_wolves.item.pet_carrier.ItemPetCarrier;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
@GameRegistry.ObjectHolder(ModInfo.ID)
public class SWItems {

    public static final Item DOG_TAG = new ItemDogTag();
    public static final Item DOG_TREAT = new ItemDogTreat();
    public static final Item WHISTLE = new ItemWhistle();
    public static final Item PET_CARRIER = new ItemPetCarrier();
    public static final Item DOG_EGG = new ItemWolfEgg();

    @Mod.EventBusSubscriber(modid = ModInfo.ID)
    public static class RegistrationHandler {

        @SubscribeEvent
        public static void registerItems(final RegistryEvent.Register<Item> event) {
            final IForgeRegistry<Item> registry = event.getRegistry();
            registry.registerAll(DOG_TAG);
            registry.registerAll(DOG_TREAT);
            registry.registerAll(WHISTLE);
            registry.registerAll(PET_CARRIER);
            registry.registerAll(DOG_EGG);
        }
    }
}

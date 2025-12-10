package sophisticated_wolves.event;

import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import sophisticated_wolves.api.ModInfo;
import sophisticated_wolves.core.SWConfiguration;
import sophisticated_wolves.core.SWItems;
import sophisticated_wolves.item.ItemDogTag;
import sophisticated_wolves.item.ItemDogTreat;
import sophisticated_wolves.item.ItemPetCarrier;

import static com.mojang.text2speech.Narrator.LOGGER;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
@Mod.EventBusSubscriber(modid = ModInfo.ID)
public class EventsItem {

    @SubscribeEvent
    public static void entityInteract(PlayerInteractEvent.EntityInteract event) {
        var item = event.getItemStack();
        if (item.is(SWItems.getDogTag())) {
            if (SWConfiguration.DEBUG_MODE.get()) {
                LOGGER.info("PlayerInteractEvent.EntityInteract event triggered with dog tag item");
            }
            ItemDogTag.useItemOnOtherPets(event.getTarget(), event.getEntity(), item);
        } else if (item.is(SWItems.getPetCarrier())) {
            if (SWConfiguration.DEBUG_MODE.get()) {
                LOGGER.info("PlayerInteractEvent.EntityInteract event triggered with pet carrier item");
            }
            ItemPetCarrier.useItemOnOtherPets(event.getTarget(), event.getEntity(), item, event.getHand());
        } else if (item.is(SWItems.getDogTreat())) {
            if (SWConfiguration.DEBUG_MODE.get()) {
                LOGGER.info("PlayerInteractEvent.EntityInteract event triggered with dog treat item");
            }
            ItemDogTreat.useItemOnWolf(event.getTarget(), event.getEntity(), item);
        }
    }

}

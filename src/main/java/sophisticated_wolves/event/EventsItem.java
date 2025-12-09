package sophisticated_wolves.event;

import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import sophisticated_wolves.api.ModInfo;
import sophisticated_wolves.core.SWItems;
import sophisticated_wolves.item.ItemDogTag;
import sophisticated_wolves.item.ItemDogTreat;
import sophisticated_wolves.item.ItemPetCarrier;

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
            ItemDogTag.useItemOnOtherPets(event.getTarget(), event.getEntity(), item);
        } else if (item.is(SWItems.getPetCarrier())) {
            ItemPetCarrier.useItemOnOtherPets(event.getTarget(), event.getEntity(), item, event.getHand());
        } else if (item.is(SWItems.getDogTreat())) {
            ItemDogTreat.useItemOnWolf(event.getTarget(), event.getEntity(), item);
        }
    }

}

package sophisticated_wolves.event;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import sophisticated_wolves.api.ModInfo;
import sophisticated_wolves.core.SWMenu;
import sophisticated_wolves.gui.screen.DogBowlScreen;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
@EventBusSubscriber(modid = ModInfo.ID, value = Dist.CLIENT)
public class SWScreensEvents {

    @SubscribeEvent
    public static void clientSetup(RegisterMenuScreensEvent event) {
        event.register(SWMenu.DOG_BOWL.get(), DogBowlScreen::new);
    }

}

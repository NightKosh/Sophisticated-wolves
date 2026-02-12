package sophisticated_wolves.event;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import sophisticated_wolves.api.ModInfo;
import sophisticated_wolves.api.SophisticatedWolvesAPI;
import sophisticated_wolves.core.SWConfiguration;
import sophisticated_wolves.core.SWEntities;

import static sophisticated_wolves.SophisticatedWolvesMod.LOGGER;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
@EventBusSubscriber(modid = ModInfo.ID)
public class SWEventsCompatibility {

    @SubscribeEvent
    public static void onFMLCommonSetupEvent(FMLCommonSetupEvent event) {
        if (SWConfiguration.DEBUG_MODE.get()) {
            LOGGER.info("FMLCommonSetupEvent event triggered");
        }
        SophisticatedWolvesAPI.SOPHISTICATED_WOLF_TYPE = SWEntities.getSophisticatedWolfType();
    }

}

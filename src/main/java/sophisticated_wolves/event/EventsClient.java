package sophisticated_wolves.event;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import sophisticated_wolves.api.ModInfo;
import sophisticated_wolves.client.renderer.SophisticatedWolfRenderer;
import sophisticated_wolves.core.SWEntities;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
@EventBusSubscriber(modid = ModInfo.ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class EventsClient {

    @SubscribeEvent
    public static void entityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(SWEntities.getSophisticatedWolfType(), SophisticatedWolfRenderer::new);
    }

}

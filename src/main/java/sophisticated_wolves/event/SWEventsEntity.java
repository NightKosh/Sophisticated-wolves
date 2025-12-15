package sophisticated_wolves.event;

import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import sophisticated_wolves.api.ModInfo;
import sophisticated_wolves.core.SWConfiguration;
import sophisticated_wolves.core.SWEntities;
import sophisticated_wolves.entity.SophisticatedWolf;

import static sophisticated_wolves.SophisticatedWolvesMod.LOGGER;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
@EventBusSubscriber(modid = ModInfo.ID)
public class SWEventsEntity {

    @SubscribeEvent
    public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(SWEntities.getSophisticatedWolfType(), SophisticatedWolf.createAttributeSupplier());
    }

    @SubscribeEvent
    public static void registerSpawnPlacement(RegisterSpawnPlacementsEvent event) {
        if (SWConfiguration.DEBUG_MODE.get()) {
            LOGGER.info("RegisterSpawnPlacementsEvent event triggered");
        }
        event.register(SWEntities.getSophisticatedWolfType(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                SophisticatedWolf::checkSpawnRules,
                RegisterSpawnPlacementsEvent.Operation.OR);
    }

}

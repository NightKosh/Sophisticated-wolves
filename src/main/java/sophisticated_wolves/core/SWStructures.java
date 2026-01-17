package sophisticated_wolves.core;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;
import sophisticated_wolves.api.ModInfo;

import java.util.ArrayList;

import static net.minecraft.resources.Identifier.withDefaultNamespace;
import static sophisticated_wolves.SophisticatedWolvesMod.LOGGER;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
@EventBusSubscriber(modid = ModInfo.ID)
public class SWStructures {

    private static final ResourceKey<StructureProcessorList> EMPTY_PROCESSOR_LIST_KEY =
            ResourceKey.create(Registries.PROCESSOR_LIST, withDefaultNamespace("empty"));

    private static void addBuildingToPool(
            Registry<StructureTemplatePool> templatePoolRegistry,
            Registry<StructureProcessorList> processorListRegistry,
            Identifier poolRL,
            String nbtPieceRL,
            int weight) {
        var poolHolder = templatePoolRegistry.get(poolRL);
        if (poolHolder.isPresent()) {
            var pool = poolHolder.get().value();
            var emptyProcessorList = processorListRegistry.get(EMPTY_PROCESSOR_LIST_KEY.identifier());
            if (emptyProcessorList.isPresent()) {
                var piece = SinglePoolElement.legacy(nbtPieceRL, emptyProcessorList.get())
                        .apply(StructureTemplatePool.Projection.RIGID);

                for (int i = 0; i < weight; i++) {
                    pool.templates.add(piece);
                }

                var listOfPieceEntries = new ArrayList<>(pool.rawTemplates);
                listOfPieceEntries.add(new Pair<>(piece, weight));
                pool.rawTemplates = listOfPieceEntries;
            }
        }
    }

    @SubscribeEvent
    public static void addNewVillageBuilding(final ServerAboutToStartEvent event) {
        if (SWConfiguration.DEBUG_MODE.get()) {
            LOGGER.info("ServerAboutToStartEvent event triggered");
        }
//        var templatePoolRegistry = event.getServer()
//                .registryAccess()
//                .lookupOrThrow(Registries.TEMPLATE_POOL);
//
//        var processorListRegistry = event.getServer()
//                .registryAccess()
//                .lookupOrThrow(Registries.PROCESSOR_LIST);
//
//        addBuildingToPool(templatePoolRegistry, processorListRegistry,
//                withDefaultNamespace("village/plains/houses"),
//                "sophisticated_wolves:village/plains/dog_house", 100);

//        addBuildingToPool(templatePoolRegistry, processorListRegistry,
//                withDefaultNamespace("village/savanna/houses"),
//                "sophisticated_wolves:village/savanna_kennels", 10);
//
//        addBuildingToPool(templatePoolRegistry, processorListRegistry,
//                withDefaultNamespace("village/desert/houses"),
//                "sophisticated_wolves:village/desert_kennels", 10);
//
//        addBuildingToPool(templatePoolRegistry, processorListRegistry,
//                withDefaultNamespace("village/taiga/houses"),
//                "sophisticated_wolves:village/taiga_kennels", 10);
//
//        addBuildingToPool(templatePoolRegistry, processorListRegistry,
//                withDefaultNamespace("village/snowy/houses"),
//                "sophisticated_wolves:village/snowy_kennels", 10);
    }

}

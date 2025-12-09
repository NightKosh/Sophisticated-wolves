package sophisticated_wolves.core;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import sophisticated_wolves.api.ModInfo;

import java.util.ArrayList;

import static net.minecraft.resources.ResourceLocation.fromNamespaceAndPath;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
@Mod.EventBusSubscriber(modid = ModInfo.ID)
public class SWStructures {

    private static final ResourceKey<StructureProcessorList> EMPTY_PROCESSOR_LIST_KEY =
            ResourceKey.create(Registries.PROCESSOR_LIST, fromNamespaceAndPath("minecraft", "empty"));

    private static void addBuildingToPool(
            Registry<StructureTemplatePool> templatePoolRegistry,
            Registry<StructureProcessorList> processorListRegistry,
            ResourceLocation poolRL,
            String nbtPieceRL,
            int weight) {
        var pool = templatePoolRegistry.get(poolRL);
        if (pool != null) {
            var emptyProcessorList = processorListRegistry.getHolderOrThrow(EMPTY_PROCESSOR_LIST_KEY);
            var piece = SinglePoolElement.legacy(nbtPieceRL, emptyProcessorList)
                    .apply(StructureTemplatePool.Projection.RIGID);

            for (int i = 0; i < weight; i++) {
                pool.templates.add(piece);
            }

            var listOfPieceEntries = new ArrayList<>(pool.rawTemplates);
            listOfPieceEntries.add(new Pair<>(piece, weight));
            pool.rawTemplates = listOfPieceEntries;
        }
    }

    @SubscribeEvent
    public static void addNewVillageBuilding(final ServerAboutToStartEvent event) {
        Registry<StructureTemplatePool> templatePoolRegistry =
                event.getServer().registryAccess().registryOrThrow(Registries.TEMPLATE_POOL);
        Registry<StructureProcessorList> processorListRegistry =
                event.getServer().registryAccess().registryOrThrow(Registries.PROCESSOR_LIST);

        addBuildingToPool(templatePoolRegistry, processorListRegistry,
                fromNamespaceAndPath("minecraft", "village/plains/houses"),
                "sophisticated_wolves:village/plains_kennels", 1);

        addBuildingToPool(templatePoolRegistry, processorListRegistry,
                fromNamespaceAndPath("minecraft", "village/taiga/houses"),
                "sophisticated_wolves:village/taiga_kennels", 1);
    }

}

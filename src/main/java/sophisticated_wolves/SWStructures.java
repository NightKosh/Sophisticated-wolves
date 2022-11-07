package sophisticated_wolves;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
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

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
@Mod.EventBusSubscriber(modid = ModInfo.ID)
public class SWStructures {

    private static final ResourceKey<StructureProcessorList> EMPTY_PROCESSOR_LIST_KEY = ResourceKey.create(
            Registry.PROCESSOR_LIST_REGISTRY, new ResourceLocation("minecraft", "empty"));

    /**
     * Adds the building to the targeted pool.
     * We will call this in addNewVillageBuilding method further down to add to every village.
     * <p>
     * Note: This is an additive operation which means multiple mods can do this and they stack with each other safely.
     */
    private static void addBuildingToPool(
            Registry<StructureTemplatePool> templatePoolRegistry,
            Registry<StructureProcessorList> processorListRegistry,
            ResourceLocation poolRL,
            String nbtPieceRL,
            int weight) {
        // Grabs the processor list we want to use along with our piece.
        // This is a requirement as using the ProcessorLists.EMPTY field will cause the game to throw errors.
        // The reason why is the empty processor list in the world's registry is not the same instance as
        // in that field once the world is started up.
        var emptyProcessorList = processorListRegistry.getHolderOrThrow(EMPTY_PROCESSOR_LIST_KEY);

        var pool = templatePoolRegistry.get(poolRL);
        if (pool != null) {
            // Grabs the nbt piece and creates a SinglePoolElement of it that we can add to a structure's pool.
            // Use .legacy( for villages/outposts and .single( for everything else
            var piece = SinglePoolElement.legacy(nbtPieceRL, emptyProcessorList)
                    .apply(StructureTemplatePool.Projection.RIGID);

            for (int i = 0; i < weight; i++) {
                pool.templates.add(piece);
            }

            // This list of pairs of pieces and weights is not used by vanilla by default but another mod may need it for efficiency.
            // So lets add to this list for completeness. We need to make a copy of the array as it can be an immutable list.
            var listOfPieceEntries = new ArrayList<>(pool.rawTemplates);
            listOfPieceEntries.add(new Pair<>(piece, weight));
            pool.rawTemplates = listOfPieceEntries;
        }
    }

    /**
     * We use FMLServerAboutToStartEvent as the dynamic registry exists now and all JSON worldgen files were parsed.
     */
    @SubscribeEvent
    public static void addNewVillageBuilding(final ServerAboutToStartEvent event) {
        var templatePoolRegistry = event.getServer().registryAccess().registry(Registry.TEMPLATE_POOL_REGISTRY)
                .orElseThrow();
        var processorListRegistry = event.getServer().registryAccess().registry(Registry.PROCESSOR_LIST_REGISTRY)
                .orElseThrow();

        addBuildingToPool(templatePoolRegistry, processorListRegistry,
                new ResourceLocation("minecraft:village/plains/houses"),
                "sophisticated_wolves:village/plains_kennels", 500);

        addBuildingToPool(templatePoolRegistry, processorListRegistry,
                new ResourceLocation("minecraft:village/desert/houses"),
                "sophisticated_wolves:desert_kennels", 500);
    }

}

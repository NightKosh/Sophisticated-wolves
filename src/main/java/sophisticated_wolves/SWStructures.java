package sophisticated_wolves;

import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import sophisticated_wolves.village.ComponentVillagePetsSeller;
import sophisticated_wolves.village.VillageHandlerPetsSeller;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class SWStructures {

    public static void preInit() {
        if (SWConfiguration.enablePetsSeller) {
            MapGenStructureIO.registerStructureComponent(ComponentVillagePetsSeller.class, "SWVillagePetsSeller");
        }
    }

    public static void registration() {
        if (SWConfiguration.enablePetsSeller) {
            VillageHandlerPetsSeller villageUndertakerHandler = new VillageHandlerPetsSeller();
            VillagerRegistry.instance().registerVillageCreationHandler(villageUndertakerHandler);
        }
    }
}

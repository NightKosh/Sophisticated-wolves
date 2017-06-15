package sophisticated_wolves;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import sophisticated_wolves.api.ModInfo;
import sophisticated_wolves.entity.EntitySophisticatedWolf;

import java.util.Set;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class SWEntity {

    public static final String SW_NAME = "SWWolf";

    public static void registration() {
        EntityRegistry.registerModEntity(Resources.BROWN_WOLF, EntitySophisticatedWolf.class, SW_NAME, 0, ModInfo.ID, 100, 1, true);
        if (SWConfiguration.respawningWolves) {
            Set<Biome> biomeSet = BiomeDictionary.getBiomes(BiomeDictionary.Type.FOREST);
            Biome[] biomeArray = new Biome[biomeSet.size()];
            biomeSet.toArray(biomeArray);
            EntityRegistry.addSpawn(EntitySophisticatedWolf.class, SWConfiguration.spawnProbability, SWConfiguration.spawnMinCount, SWConfiguration.spawnMaxCount, EnumCreatureType.MONSTER, biomeArray);
        }
    }
}

package sophisticated_wolves;

import net.minecraftforge.fml.common.registry.GameRegistry;
import sophisticated_wolves.tile_entity.TileEntityDogBowl;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class SWTileEntity {

    public static void registration() {
        GameRegistry.registerTileEntity(TileEntityDogBowl.class, "DogBowlTE");
    }
}

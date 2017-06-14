package sophisticated_wolves;

import net.minecraftforge.fml.common.registry.GameRegistry;
import sophisticated_wolves.block.BlockDogBowl;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class SWBlocks {

    public static final BlockDogBowl DOG_BOWL = new BlockDogBowl();

    public static void registration() {
        GameRegistry.register(DOG_BOWL);
    }
}

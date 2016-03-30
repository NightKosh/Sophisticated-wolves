package sophisticated_wolves;

import net.minecraft.world.World;
import sophisticated_wolves.api.AEntitySophisticatedWolf;
import sophisticated_wolves.api.IEntityHandler;
import sophisticated_wolves.entity.EntitySophisticatedWolf;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class APIEntityHandler implements IEntityHandler {

    public AEntitySophisticatedWolf getNewSophisticatedWolf(World world) {
        return new EntitySophisticatedWolf(world);
    }
}

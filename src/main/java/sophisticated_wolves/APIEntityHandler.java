package sophisticated_wolves;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.level.Level;
import sophisticated_wolves.api.AEntitySophisticatedWolf;
import sophisticated_wolves.api.IEntityHandler;
import sophisticated_wolves.entity.SophisticatedWolf;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class APIEntityHandler implements IEntityHandler {

    public AEntitySophisticatedWolf getNewSophisticatedWolf(EntityType<? extends Wolf> entityType, Level level) {
        return new SophisticatedWolf(entityType, level);
    }

}

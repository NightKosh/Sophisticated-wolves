package sophisticated_wolves.proxy;

import net.minecraft.entity.passive.EntityTameable;
import sophisticated_wolves.entity.EntitySophisticatedWolf;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class CommonProxy {

    public void registerRenderers() {
    }

    public String getLocalizedString(String str) {
        return str;
    }

    public void openPetGui(EntityTameable pet) {}

    public void openFoodGui(EntitySophisticatedWolf pet) {}
}

package sophisticated_wolves.item;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;

/**
 * GraveStone mod
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class WolfEggColor implements IItemColor {

    @Override
    public int getColorFromItemstack(ItemStack item, int tintIndex) {
        if ((tintIndex & 1) == 0) {
            return ItemWolfEgg.EnumEggs.SOPHISTICATED_WOLF.getBackgroundColor();
        } else {
            return ItemWolfEgg.EnumEggs.SOPHISTICATED_WOLF.getForegroundColor();
        }
    }
}

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
        int itemDamage = item.getItemDamage();
        if (itemDamage >= 0 && itemDamage < ItemWolfEgg.EnumEggs.values().length) {
            if ((tintIndex & 1) == 0) {
                return ItemWolfEgg.EnumEggs.getById(itemDamage).getBackgroundColor();
            } else {
                return ItemWolfEgg.EnumEggs.getById(itemDamage).getForegroundColor();
            }
        }
        return 0xFFFFFF;
    }
}

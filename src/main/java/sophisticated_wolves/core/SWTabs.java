package sophisticated_wolves.core;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class SWTabs {

    public static final CreativeModeTab TAB = new CreativeModeTab("tab_sophisticated_wolves") {

        @Override
        public ItemStack makeIcon() {
            return new ItemStack(SWItems.getDogTreat());
        }

    };

}

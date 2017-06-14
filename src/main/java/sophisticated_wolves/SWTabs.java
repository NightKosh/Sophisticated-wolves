package sophisticated_wolves;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class SWTabs {
    public static CreativeTabs tab;

    public static void registration() {
        tab = new CreativeTabs("tabSophisticatedWolves") {
            @Override
            public ItemStack getIconItemStack() {
                return new ItemStack(SWItems.DOG_TREAT);
            }

            @Override
            @SideOnly(Side.CLIENT)
            public ItemStack getTabIconItem() {
                return new ItemStack(SWItems.DOG_TREAT);
            }
        };
    }
}

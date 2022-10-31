package sophisticated_wolves.item.item_block;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import sophisticated_wolves.core.SWBlocks;
import sophisticated_wolves.core.SWTabs;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class ItemBlockDogBowl extends BlockItem {

    public ItemBlockDogBowl() {
        super(SWBlocks.getDogBowl(), new Item.Properties().tab(SWTabs.TAB));
    }

}

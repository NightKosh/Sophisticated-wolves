package sophisticated_wolves.item.item_block;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import sophisticated_wolves.core.SWBlocks;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class ItemBlockKennel extends BlockItem {

    public ItemBlockKennel() {
        super(SWBlocks.getKennel(), new Item.Properties().stacksTo(64));
    }

}

package sophisticated_wolves.item.item_block;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import sophisticated_wolves.SWBlocks;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class ItemBlockKennel extends ItemBlock {

    public ItemBlockKennel(Block block) {
        super(block);
        this.setHasSubtypes(true);
        this.setRegistryName(SWBlocks.KENNEL.getRegistryName());
    }

    @Override
    public int getMetadata(int damageValue) {
        return damageValue;
    }
}

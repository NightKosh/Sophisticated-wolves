package sophisticated_wolves.item.ItemBlock;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import sophisticated_wolves.SWBlocks;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class ItemBlockDogBowl extends ItemBlock {

    public ItemBlockDogBowl(Block block) {
        super(block);
        this.setHasSubtypes(true);
        this.setRegistryName(SWBlocks.DOG_BOWL.getRegistryName());
    }

    @Override
    public int getMetadata(int damageValue) {
        return damageValue;
    }
}

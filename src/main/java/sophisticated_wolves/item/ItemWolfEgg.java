package sophisticated_wolves.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import sophisticated_wolves.core.SWEntities;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class ItemWolfEgg extends ForgeSpawnEggItem {

    private static final int BACKGROUND_COLOR = 14144467;
    private static final int HIGHLIGHT_COLOR = 13545366;

    public ItemWolfEgg() {
        super(SWEntities.SOPHISTICATED_WOLF, BACKGROUND_COLOR, HIGHLIGHT_COLOR, new Item.Properties().stacksTo(64));
    }

}
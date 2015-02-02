package sophisticated_wolves;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import sophisticated_wolves.item.ItemDogTag;
import sophisticated_wolves.item.ItemDogTreat;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class SWItems {

    public static Item dogTag;
    public static Item dogTreat;

    private SWItems() {

    }

    public static void itemsRegistration() {
        dogTag = new ItemDogTag();
        GameRegistry.registerItem(dogTag, "SWDogTag");

        dogTreat = new ItemDogTreat();
        GameRegistry.registerItem(dogTreat, "SWDogTreat");
    }
}
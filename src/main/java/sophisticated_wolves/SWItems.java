package sophisticated_wolves;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import sophisticated_wolves.item.ItemDogTag;
import sophisticated_wolves.item.ItemDogTreat;
import sophisticated_wolves.item.ItemGSMonsterPlacer;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class SWItems {

    public static Item dogTag;
    public static Item dogTreat;
    public static Item dogEgg;

    private SWItems() {

    }

    public static final String DOG_TAG = "SWDogTag";
    public static final String DOG_TREAT = "SWDogTreat";
    public static final String DOG_EGG = "SWDogEgg";

    public static void itemsRegistration() {
        dogTag = new ItemDogTag();
        GameRegistry.registerItem(dogTag, DOG_TAG);

        dogTreat = new ItemDogTreat();
        GameRegistry.registerItem(dogTreat, DOG_TREAT);

        dogEgg = new ItemGSMonsterPlacer();
        GameRegistry.registerItem(dogEgg, DOG_EGG);

        SophisticatedWolvesMod.proxy.modelsRegistration();
    }
}

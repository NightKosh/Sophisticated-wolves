package sophisticated_wolves;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.registry.GameRegistry;
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

    public static final String DOG_TAG = "SWDogTag";
    public static final String DOG_TREAT = "SWDogTreat";

    public static void itemsRegistration() {
        dogTag = new ItemDogTag();
        GameRegistry.registerItem(dogTag, DOG_TAG);
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(dogTag, 0, Resources.dogTagModel);

        dogTreat = new ItemDogTreat();
        GameRegistry.registerItem(dogTreat, DOG_TREAT);
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(dogTreat, 0, Resources.dogTreatModel);
    }
}

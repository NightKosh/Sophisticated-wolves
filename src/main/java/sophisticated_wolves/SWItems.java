package sophisticated_wolves;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import sophisticated_wolves.api.ModInfo;
import sophisticated_wolves.item.ItemDogTag;
import sophisticated_wolves.item.ItemDogTreat;
import sophisticated_wolves.item.ItemWolfEgg;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class SWItems {

    private static final String MOD_NAME = ModInfo.ID.toLowerCase();

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
//        GameRegistry.registerItem(dogTag, DOG_TAG);
        GameRegistry.register(dogTag, new ResourceLocation(MOD_NAME + ":" + DOG_TAG));
//        GameRegistry.register(dogTag.setRegistryName(DOG_TAG));

        dogTreat = new ItemDogTreat();
        GameRegistry.register(dogTreat, new ResourceLocation(MOD_NAME + ":" + DOG_TREAT.toLowerCase()));

        dogEgg = new ItemWolfEgg();
        GameRegistry.register(dogEgg, new ResourceLocation(MOD_NAME + ":" + DOG_EGG));

        SophisticatedWolvesMod.proxy.modelsRegistration();
    }
}

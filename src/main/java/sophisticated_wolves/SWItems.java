package sophisticated_wolves;

import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import sophisticated_wolves.api.ModInfo;
import sophisticated_wolves.item.ItemBlock.ItemBlockDogBowl;
import sophisticated_wolves.item.ItemDogTag;
import sophisticated_wolves.item.ItemDogTreat;
import sophisticated_wolves.item.ItemWolfEgg;
import sophisticated_wolves.item.pet_carrier.ItemPetCarrier;

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
    public static Item petCarrier;
    public static Item dogEgg;

    public static final ItemBlock DOG_BOWL_IB = new ItemBlockDogBowl(SWBlocks.DOG_BOWL);

    private SWItems() {

    }

    public static final String DOG_TAG = "SWDogTag";
    public static final String DOG_TREAT = "SWDogTreat";
    public static final String PET_CARRIER = "SWPetCarrier";
    public static final String DOG_EGG = "SWDogEgg";

    public static void itemsRegistration() {
        dogTag = new ItemDogTag();
        GameRegistry.register(dogTag, new ResourceLocation(MOD_NAME + ":" + DOG_TAG));

        dogTreat = new ItemDogTreat();
        GameRegistry.register(dogTreat, new ResourceLocation(MOD_NAME + ":" + DOG_TREAT.toLowerCase()));

        petCarrier = new ItemPetCarrier();
        GameRegistry.register(petCarrier, new ResourceLocation(MOD_NAME + ":" + PET_CARRIER.toLowerCase()));

        dogEgg = new ItemWolfEgg();
        GameRegistry.register(dogEgg, new ResourceLocation(MOD_NAME + ":" + DOG_EGG));

        GameRegistry.register(DOG_BOWL_IB);
    }
}

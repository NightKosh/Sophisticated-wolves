package sophisticated_wolves;

import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;
import sophisticated_wolves.item.ItemDogTag;
import sophisticated_wolves.item.ItemDogTreat;
import sophisticated_wolves.item.ItemWolfEgg;
import sophisticated_wolves.item.item_block.ItemBlockDogBowl;
import sophisticated_wolves.item.pet_carrier.ItemPetCarrier;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class SWItems {

    public static final Item DOG_TAG = new ItemDogTag();
    public static final Item DOG_TREAT = new ItemDogTreat();
    public static final Item PET_CARRIER = new ItemPetCarrier();
    public static final Item DOG_EGG = new ItemWolfEgg();

    public static final ItemBlock DOG_BOWL_IB = new ItemBlockDogBowl(SWBlocks.DOG_BOWL);

    public static void registration() {
        GameRegistry.register(DOG_TAG);

        GameRegistry.register(DOG_TREAT);

        GameRegistry.register(PET_CARRIER);

        GameRegistry.register(DOG_EGG);

        GameRegistry.register(DOG_BOWL_IB);
    }
}

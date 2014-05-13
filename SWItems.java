package sophisticated_wolves;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import sophisticated_wolves.item.ItemDogTag;
import sophisticated_wolves.item.ItemDogTreat;

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

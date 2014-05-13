package sophisticated_wolves;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class Recipes {

    private Recipes() {

    }

    public static void recipesRegistration() {
        GameRegistry.addRecipe(new ItemStack(SWItems.dogTag), "s", "l", 's', Items.string, 'l', Items.leather);
        GameRegistry.addRecipe(new ItemStack(SWItems.dogTreat), "wpw", 'w', Items.wheat, 'p', Items.porkchop);
        GameRegistry.addRecipe(new ItemStack(SWItems.dogTreat), "wpw", 'w', Items.wheat, 'p', Items.cooked_porkchop);
    }
}

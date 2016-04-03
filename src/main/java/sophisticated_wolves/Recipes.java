package sophisticated_wolves;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class Recipes {

    private Recipes() {

    }

    public static void recipesRegistration() {
        GameRegistry.addRecipe(new ItemStack(SWItems.dogTag, 5), "s", "l", 's', Items.string, 'l', Items.leather);

        GameRegistry.addRecipe(new ItemStack(SWItems.dogTreat, 2), "wpw", 'w', Items.wheat, 'p', Items.porkchop);
        GameRegistry.addRecipe(new ItemStack(SWItems.dogTreat, 2), "wpw", 'w', Items.wheat, 'p', Items.cooked_porkchop);

        GameRegistry.addRecipe(new ItemStack(SWItems.dogTreat, 2), "wpw", 'w', Items.wheat, 'p', Items.beef);
        GameRegistry.addRecipe(new ItemStack(SWItems.dogTreat, 2), "wpw", 'w', Items.wheat, 'p', Items.cooked_beef);

        GameRegistry.addRecipe(new ItemStack(SWItems.dogTreat, 2), "wpw", 'w', Items.wheat, 'p', Items.chicken);
        GameRegistry.addRecipe(new ItemStack(SWItems.dogTreat, 2), "wpw", 'w', Items.wheat, 'p', Items.cooked_chicken);

        GameRegistry.addRecipe(new ItemStack(SWItems.dogTreat, 2), "wpw", 'w', Items.wheat, 'p', Items.rabbit);
        GameRegistry.addRecipe(new ItemStack(SWItems.dogTreat, 2), "wpw", 'w', Items.wheat, 'p', Items.cooked_rabbit);

        GameRegistry.addRecipe(new ItemStack(SWItems.dogTreat, 2), "wpw", 'w', Items.wheat, 'p', Items.mutton);
        GameRegistry.addRecipe(new ItemStack(SWItems.dogTreat, 2), "wpw", 'w', Items.wheat, 'p', Items.cooked_mutton);
    }
}

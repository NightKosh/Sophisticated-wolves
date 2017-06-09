package sophisticated_wolves;

import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
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
        GameRegistry.addRecipe(new ItemStack(SWItems.dogTag, 5), "s", "l", 's', Items.STRING, 'l', Items.LEATHER);

        GameRegistry.addRecipe(new ItemStack(SWItems.dogTreat, 2), "wpw", 'w', Items.WHEAT, 'p', Items.PORKCHOP);
        GameRegistry.addRecipe(new ItemStack(SWItems.dogTreat, 2), "wpw", 'w', Items.WHEAT, 'p', Items.COOKED_PORKCHOP);

        GameRegistry.addRecipe(new ItemStack(SWItems.dogTreat, 2), "wpw", 'w', Items.WHEAT, 'p', Items.BEEF);
        GameRegistry.addRecipe(new ItemStack(SWItems.dogTreat, 2), "wpw", 'w', Items.WHEAT, 'p', Items.COOKED_BEEF);

        GameRegistry.addRecipe(new ItemStack(SWItems.dogTreat, 2), "wpw", 'w', Items.WHEAT, 'p', Items.CHICKEN);
        GameRegistry.addRecipe(new ItemStack(SWItems.dogTreat, 2), "wpw", 'w', Items.WHEAT, 'p', Items.COOKED_CHICKEN);

        GameRegistry.addRecipe(new ItemStack(SWItems.dogTreat, 2), "wpw", 'w', Items.WHEAT, 'p', Items.RABBIT);
        GameRegistry.addRecipe(new ItemStack(SWItems.dogTreat, 2), "wpw", 'w', Items.WHEAT, 'p', Items.COOKED_RABBIT);

        GameRegistry.addRecipe(new ItemStack(SWItems.dogTreat, 2), "wpw", 'w', Items.WHEAT, 'p', Items.MUTTON);
        GameRegistry.addRecipe(new ItemStack(SWItems.dogTreat, 2), "wpw", 'w', Items.WHEAT, 'p', Items.COOKED_MUTTON);

        GameRegistry.addRecipe(new ItemStack(SWItems.petCarrier), " i ", "bwb", "sss",
                'i', Items.IRON_INGOT, 'b', Blocks.IRON_BARS, 'w', new ItemStack(Blocks.WOOL, 1, EnumDyeColor.WHITE.ordinal()),
                's', Blocks.STONE_SLAB);
    }
}

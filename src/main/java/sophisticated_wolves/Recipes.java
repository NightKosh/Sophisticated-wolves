package sophisticated_wolves;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import sophisticated_wolves.api.ModInfo;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class Recipes {

    public static void recipesRegistration() {
        ResourceLocation group = new ResourceLocation(ModInfo.ID);
        GameRegistry.addShapedRecipe(new ResourceLocation(ModInfo.ID, "dogtag"), group, new ItemStack(SWItems.DOG_TAG, 5), "s", "l", 's', Items.STRING, 'l', Items.LEATHER);

        GameRegistry.addShapedRecipe(new ResourceLocation(ModInfo.ID, "dogtreatpork1"), group, new ItemStack(SWItems.DOG_TREAT, 1), "wpw", 'w', Items.WHEAT, 'p', Items.PORKCHOP);
        GameRegistry.addShapedRecipe(new ResourceLocation(ModInfo.ID, "dogtreatpork2"), group, new ItemStack(SWItems.DOG_TREAT, 2), "wpw", 'w', Items.WHEAT, 'p', Items.COOKED_PORKCHOP);

        GameRegistry.addShapedRecipe(new ResourceLocation(ModInfo.ID, "dogtreatbeef1"), group, new ItemStack(SWItems.DOG_TREAT, 1), "wpw", 'w', Items.WHEAT, 'p', Items.BEEF);
        GameRegistry.addShapedRecipe(new ResourceLocation(ModInfo.ID, "dogtreatbeef2"), group, new ItemStack(SWItems.DOG_TREAT, 2), "wpw", 'w', Items.WHEAT, 'p', Items.COOKED_BEEF);

        GameRegistry.addShapedRecipe(new ResourceLocation(ModInfo.ID, "dogtreatchicken1"), group, new ItemStack(SWItems.DOG_TREAT, 1), "wpw", 'w', Items.WHEAT, 'p', Items.CHICKEN);
        GameRegistry.addShapedRecipe(new ResourceLocation(ModInfo.ID, "dogtreatchicken2"), group, new ItemStack(SWItems.DOG_TREAT, 2), "wpw", 'w', Items.WHEAT, 'p', Items.COOKED_CHICKEN);

        GameRegistry.addShapedRecipe(new ResourceLocation(ModInfo.ID, "dogtreatrabbit1"), group, new ItemStack(SWItems.DOG_TREAT, 1), "wpw", 'w', Items.WHEAT, 'p', Items.RABBIT);
        GameRegistry.addShapedRecipe(new ResourceLocation(ModInfo.ID, "dogtreatrabbit2"), group, new ItemStack(SWItems.DOG_TREAT, 2), "wpw", 'w', Items.WHEAT, 'p', Items.COOKED_RABBIT);

        GameRegistry.addShapedRecipe(new ResourceLocation(ModInfo.ID, "dogtreatmutton1"), group, new ItemStack(SWItems.DOG_TREAT, 1), "wpw", 'w', Items.WHEAT, 'p', Items.MUTTON);
        GameRegistry.addShapedRecipe(new ResourceLocation(ModInfo.ID, "dogtreatmutton2"), group, new ItemStack(SWItems.DOG_TREAT, 2), "wpw", 'w', Items.WHEAT, 'p', Items.COOKED_MUTTON);

        GameRegistry.addShapedRecipe(new ResourceLocation(ModInfo.ID, "petcarrier"), group, new ItemStack(SWItems.PET_CARRIER), " i ", "bwb", "sss",
                'i', Items.IRON_INGOT, 'b', Blocks.IRON_BARS, 'w', new ItemStack(Blocks.WOOL, 1, EnumDyeColor.WHITE.ordinal()),
                's', Blocks.STONE_SLAB);

        GameRegistry.addShapedRecipe(new ResourceLocation(ModInfo.ID, "whistle"), group, new ItemStack(SWItems.WHISTLE), "iig", " di", 'i', Items.IRON_INGOT, 'g', Items.GOLD_INGOT, 'd', new ItemStack(Items.DYE, 1, EnumDyeColor.ORANGE.getDyeDamage()));

        GameRegistry.addShapedRecipe(new ResourceLocation(ModInfo.ID, "dogbowl"), group, new ItemStack(SWBlocks.DOG_BOWL), "cbc", "ccc", 'c', Items.BRICK, 'b', Items.BOWL);
    }
}

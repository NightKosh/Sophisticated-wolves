package sophisticated_wolves.gametest;

import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.gametest.GameTestHolder;
import net.minecraftforge.gametest.PrefixGameTestTemplate;
import org.apache.commons.lang3.tuple.Pair;
import sophisticated_wolves.api.ModInfo;
import sophisticated_wolves.core.SWBlocks;
import sophisticated_wolves.core.SWItems;

import java.util.List;

import static com.mojang.text2speech.Narrator.LOGGER;
import static net.minecraft.resources.ResourceLocation.fromNamespaceAndPath;

@GameTestHolder(ModInfo.ID)
@PrefixGameTestTemplate(false)
public class CraftingRecipesTests {

    private static final String TEMPLATE = "empty";

    @GameTest(template = TEMPLATE)
    public static void dogBowl(GameTestHelper helper) {
        defaultTest(helper, "dog_bowl",
                List.of(Pair.of(0, Items.BOWL), Pair.of(1, Items.BRICK)),
                new ItemStack(SWBlocks.getDogBowl()));
    }

    @GameTest(template = TEMPLATE)
    public static void kennel(GameTestHelper helper) {
        defaultTest(helper, "kennel",
                List.of(Pair.of(0, Items.BRICK), Pair.of(1, Items.BRICK), Pair.of(2, Items.BRICK),
                        Pair.of(3, Items.OAK_PLANKS), Pair.of(4, Items.WHITE_WOOL), Pair.of(5, Items.OAK_PLANKS),
                        Pair.of(6, Items.OAK_PLANKS), Pair.of(7, SWBlocks.getDogBowl().asItem()), Pair.of(8, Items.OAK_PLANKS)),
                new ItemStack(SWBlocks.getKennel()));
    }

    @GameTest(template = TEMPLATE)
    public static void petCarrier(GameTestHelper helper) {
        defaultTest(helper, "pet_carrier",
                List.of(Pair.of(0, Items.IRON_INGOT), Pair.of(1, Items.SMOOTH_STONE_SLAB),
                        Pair.of(3, Items.IRON_BARS), Pair.of(4, Items.WHITE_WOOL)),
                new ItemStack(SWItems.getPetCarrier()));
    }

    @GameTest(template = TEMPLATE)
    public static void whistle(GameTestHelper helper) {
        defaultTest(helper, "whistle",
                List.of(Pair.of(0, Items.IRON_INGOT), Pair.of(1, Items.GOLD_INGOT),
                        Pair.of(3, Items.ORANGE_DYE), Pair.of(4, Items.IRON_INGOT)),
                new ItemStack(SWItems.getWhistle()));
    }

    @GameTest(template = TEMPLATE)
    public static void dogTag(GameTestHelper helper) {
        defaultTest(helper, "dog_tag",
                List.of(Pair.of(0, Items.STRING), Pair.of(1, Items.LEATHER)),
                new ItemStack(SWItems.getDogTag(), 5));
    }

    @GameTest(template = TEMPLATE)
    public static void dogTreatBeef(GameTestHelper helper) {
        defaultTest(helper, "dog_treat_beef",
                List.of(Pair.of(0, Items.WHEAT), Pair.of(1, Items.BEEF)),
                new ItemStack(SWItems.getDogTreat()));
    }

    @GameTest(template = TEMPLATE)
    public static void dogTreatBeefCooked(GameTestHelper helper) {
        defaultTest(helper, "dog_treat_beef_cooked",
                List.of(Pair.of(0, Items.WHEAT), Pair.of(1, Items.COOKED_BEEF)),
                new ItemStack(SWItems.getDogTreat(), 2));
    }

    @GameTest(template = TEMPLATE)
    public static void dogTreatChicken(GameTestHelper helper) {
        defaultTest(helper, "dog_treat_chicken",
                List.of(Pair.of(0, Items.WHEAT), Pair.of(1, Items.CHICKEN)),
                new ItemStack(SWItems.getDogTreat()));
    }

    @GameTest(template = TEMPLATE)
    public static void dogTreatChickenCooked(GameTestHelper helper) {
        defaultTest(helper, "dog_treat_chicken_cooked",
                List.of(Pair.of(0, Items.WHEAT), Pair.of(1, Items.COOKED_CHICKEN)),
                new ItemStack(SWItems.getDogTreat(), 2));
    }

    @GameTest(template = TEMPLATE)
    public static void dogTreatMutton(GameTestHelper helper) {
        defaultTest(helper, "dog_treat_mutton",
                List.of(Pair.of(0, Items.WHEAT), Pair.of(1, Items.MUTTON)),
                new ItemStack(SWItems.getDogTreat()));
    }

    @GameTest(template = TEMPLATE)
    public static void dogTreatMuttonCooked(GameTestHelper helper) {
        defaultTest(helper, "dog_treat_mutton_cooked",
                List.of(Pair.of(0, Items.WHEAT), Pair.of(1, Items.COOKED_MUTTON)),
                new ItemStack(SWItems.getDogTreat(), 2));
    }

    @GameTest(template = TEMPLATE)
    public static void dogTreatPork(GameTestHelper helper) {
        defaultTest(helper, "dog_treat_pork",
                List.of(Pair.of(0, Items.WHEAT), Pair.of(1, Items.PORKCHOP)),
                new ItemStack(SWItems.getDogTreat()));
    }

    @GameTest(template = TEMPLATE)
    public static void dogTreatPorkCooked(GameTestHelper helper) {
        defaultTest(helper, "dog_treat_pork_cooked",
                List.of(Pair.of(0, Items.WHEAT), Pair.of(1, Items.COOKED_PORKCHOP)),
                new ItemStack(SWItems.getDogTreat(), 2));
    }

    @GameTest(template = TEMPLATE)
    public static void dogTreatRabbit(GameTestHelper helper) {
        defaultTest(helper, "dog_treat_rabbit",
                List.of(Pair.of(0, Items.WHEAT), Pair.of(1, Items.RABBIT)),
                new ItemStack(SWItems.getDogTreat()));
    }

    @GameTest(template = TEMPLATE)
    public static void dogTreatRabbitCooked(GameTestHelper helper) {
        defaultTest(helper, "dog_treat_rabbit_cooked",
                List.of(Pair.of(0, Items.WHEAT), Pair.of(1, Items.COOKED_RABBIT)),
                new ItemStack(SWItems.getDogTreat(), 2));
    }

    protected static void defaultTest(GameTestHelper helper, String recipeName, List<Pair<Integer, Item>> inputs, ItemStack expected) {
        var level = helper.getLevel();

        var res = fromNamespaceAndPath(ModInfo.ID, recipeName);
        var recipeByKey = level.getRecipeManager().byKey(res);

        if (recipeByKey.isEmpty()) {
            helper.fail("Can't find " + res + " recipe in RecipeManager.");
            return;
        }

        if (!(recipeByKey.get() instanceof CraftingRecipe)) {
            helper.fail("Recipe " + recipeName + " isn't an instance of CraftingRecipe: " + recipeByKey.get());
            return;
        }

        var dummyMenu = new AbstractContainerMenu(MenuType.CRAFTING, 0) {

            @Override
            public boolean stillValid(Player player) {
                return true;
            }

            @Override
            public ItemStack quickMoveStack(Player player, int index) {
                return ItemStack.EMPTY;
            }
        };

        var crafting = new CraftingContainer(dummyMenu, 3, 3);
        for (var pair : inputs) {
            crafting.setItem(pair.getKey(), new ItemStack(pair.getValue()));
        }

        var recipeOpt = level.getRecipeManager().getRecipeFor(RecipeType.CRAFTING, crafting, level);
        if (recipeOpt.isEmpty()) {
            helper.fail("Can't find " + recipeName + " crafting recipe.");
            return;
        }

        var result = recipeOpt.get().assemble(crafting, level.registryAccess());
        if (!result.is(expected.getItem())) {
            helper.fail("Expected " + expected.getHoverName().getString() + " item but get " + result.getHoverName().getString());
            return;
        }

        if (result.getCount() != expected.getCount()) {
            helper.fail("Expected " + expected.getCount() + " items but get " + result.getCount());
            return;
        }

        LOGGER.info("Test for " + recipeName + " crafting recipe passed!");

        helper.succeed();
    }

}

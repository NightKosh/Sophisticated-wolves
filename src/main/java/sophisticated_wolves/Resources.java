package sophisticated_wolves;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;
import sophisticated_wolves.api.ModInfo;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class Resources {
    private static final String MOD_NAME = ModInfo.ID.toLowerCase();
    private static final String ENTITY_LOCATION = MOD_NAME + ":textures/entity/";
    private static final String GUI_LOCATION = MOD_NAME + ":textures/gui/";

    //Brown Wolf
    public static final ResourceLocation BROWN_WOLF = new ResourceLocation(ENTITY_LOCATION + "brown/wolf.png");
    public static final ResourceLocation BROWN_WOLF_TAME = new ResourceLocation(ENTITY_LOCATION + "brown/wolf_tame.png");
    public static final ResourceLocation BROWN_WOLF_ANGRY = new ResourceLocation(ENTITY_LOCATION + "brown/wolf_angry.png");

    //Black Wolf
    public static final ResourceLocation BLACK_WOLF = new ResourceLocation(ENTITY_LOCATION + "black/wolf.png");
    public static final ResourceLocation BLACK_WOLF_TAME = new ResourceLocation(ENTITY_LOCATION + "black/wolf_tame.png");
    public static final ResourceLocation BLACK_WOLF_ANGRY = new ResourceLocation(ENTITY_LOCATION + "black/wolf_angry.png");

    //Forest Wolf
    public static final ResourceLocation FOREST_WOLF = new ResourceLocation(ENTITY_LOCATION + "forest/wolf.png");
    public static final ResourceLocation FOREST_WOLF_TAME = new ResourceLocation(ENTITY_LOCATION + "forest/wolf_tame.png");
    public static final ResourceLocation FOREST_WOLF_ANGRY = new ResourceLocation(ENTITY_LOCATION + "forest/wolf_angry.png");

    // Gui
    public static final ResourceLocation DOG_BOWL_GUI = new ResourceLocation(GUI_LOCATION + "dog_bowl_gui.png");
    public static final ResourceLocation FOOD_GUI = new ResourceLocation(GUI_LOCATION + "food_background.png");
    public static final ResourceLocation CHECKBOX_GUI = new ResourceLocation(GUI_LOCATION + "checkbox.png");

    // MODEL RESOURCES
    public static final ModelResourceLocation DOG_TAG_MODEL = new ModelResourceLocation(SWItems.DOG_TAG.getRegistryName(), "inventory");
    public static final ModelResourceLocation DOG_TREAT_MODEL = new ModelResourceLocation(SWItems.DOG_TREAT.getRegistryName(), "inventory");
    public static final ModelResourceLocation WHISTLE_MODEL = new ModelResourceLocation(SWItems.WHISTLE.getRegistryName(), "inventory");
    public static final ModelResourceLocation PET_CARRIER_MODEL = new ModelResourceLocation(SWItems.PET_CARRIER.getRegistryName(), "inventory");
    public static final ModelResourceLocation SPAWN_EGG_MODEL = new ModelResourceLocation(SWItems.DOG_EGG.getRegistryName(), "inventory");

    public static final ModelResourceLocation DOG_BOWL = new ModelResourceLocation(SWBlocks.DOG_BOWL.getRegistryName(), "inventory");
    public static final ModelResourceLocation DOG_BOWL1 = new ModelResourceLocation(SWBlocks.DOG_BOWL.getRegistryName() + "1", "inventory");
    public static final ModelResourceLocation DOG_BOWL2 = new ModelResourceLocation(SWBlocks.DOG_BOWL.getRegistryName() + "2", "inventory");
    public static final ModelResourceLocation DOG_BOWL3 = new ModelResourceLocation(SWBlocks.DOG_BOWL.getRegistryName() + "3", "inventory");
    public static final ModelResourceLocation DOG_BOWL4 = new ModelResourceLocation(SWBlocks.DOG_BOWL.getRegistryName() + "4", "inventory");

    // Sounds
    public static final ResourceLocation WHISTLE_SHORT = new ResourceLocation(MOD_NAME, SWSound.WHISTLE_SHORT_ID);
    public static final ResourceLocation WHISTLE_LONG = new ResourceLocation(MOD_NAME, SWSound.WHISTLE_LONG_ID);
}

package sophisticated_wolves.core;

import net.minecraft.resources.ResourceLocation;
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
    public static final ResourceLocation TARGET_GUI = new ResourceLocation(GUI_LOCATION + "target_background.png");
    public static final ResourceLocation COMMANDS_GUI = new ResourceLocation(GUI_LOCATION + "commands_background.png");
    public static final ResourceLocation CHECKBOX_GUI = new ResourceLocation(GUI_LOCATION + "checkbox.png");
    public static final ResourceLocation TAB_BUTTON_GUI = new ResourceLocation(GUI_LOCATION + "tab.png");

}

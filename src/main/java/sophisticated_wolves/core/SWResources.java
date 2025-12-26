package sophisticated_wolves.core;

import net.minecraft.resources.Identifier;
import sophisticated_wolves.api.ModInfo;

import static net.minecraft.resources.Identifier.fromNamespaceAndPath;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class SWResources {

    private static final String GUI_LOCATION = "textures/gui/";

    // Gui
    public static final Identifier DOG_BOWL_GUI = fromNamespaceAndPath(ModInfo.ID, GUI_LOCATION + "dog_bowl_gui.png");
    public static final Identifier FOOD_GUI = fromNamespaceAndPath(ModInfo.ID, GUI_LOCATION + "food_background.png");
    public static final Identifier TARGET_GUI = fromNamespaceAndPath(ModInfo.ID, GUI_LOCATION + "target_background.png");
    public static final Identifier COMMANDS_GUI = fromNamespaceAndPath(ModInfo.ID, GUI_LOCATION + "commands_background.png");
    public static final Identifier CHECKBOX_GUI = fromNamespaceAndPath(ModInfo.ID, GUI_LOCATION + "checkbox.png");
    public static final Identifier TAB_BUTTON_GUI = fromNamespaceAndPath(ModInfo.ID, GUI_LOCATION + "tab.png");

}

package sophisticated_wolves.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import sophisticated_wolves.core.Resources;
import sophisticated_wolves.core.SWMessages;
import sophisticated_wolves.entity.SophisticatedWolf;
import sophisticated_wolves.gui.component.GuiCheckbox;
import sophisticated_wolves.packets.WolfFoodConfigMessageToServer;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class WolfFoodConfigScreen extends WolfConfigScreen {

    private GuiCheckbox isRottenMeatAndBones;
    private GuiCheckbox isRawFish;
    private GuiCheckbox isCookedFish;
    private GuiCheckbox isSpecialFish;
    private GuiCheckbox isRawMeat;
    private GuiCheckbox isCookedMeat;

    public WolfFoodConfigScreen(SophisticatedWolf wolf) {
        super(wolf, Component.translatable("gui.sophisticated_wolves.wolf_food_configs.title"));
    }

    public static void open(SophisticatedWolf wolf) {
        Minecraft.getInstance().setScreen(new WolfFoodConfigScreen(wolf));
    }

    @Override
    protected void initCustomComponents(int x, int y) {
        this.foodScreenButton.setTabActive(true);

        var wolfFood = wolf.getWolfFood();
        this.addRenderableWidget(this.isRottenMeatAndBones = new GuiCheckbox(x + COLUMN_1, y + LINE_1, wolfFood.rottenMeatAndBones()));
        this.addRenderableWidget(this.isRawFish = new GuiCheckbox(x + COLUMN_2, y + LINE_1, wolfFood.rawFish()));
        this.addRenderableWidget(this.isCookedFish = new GuiCheckbox(x + COLUMN_1, y + LINE_2, wolfFood.cookedFish()));
        this.addRenderableWidget(this.isSpecialFish = new GuiCheckbox(x + COLUMN_2, y + LINE_2, wolfFood.specialFish()));
        this.addRenderableWidget(this.isRawMeat = new GuiCheckbox(x + COLUMN_1, y + LINE_3, wolfFood.rawMeat()));
        this.addRenderableWidget(this.isCookedMeat = new GuiCheckbox(x + COLUMN_2, y + LINE_3, wolfFood.cookedMeat()));
    }

    @Override
    protected void onScreenClosed() {
        wolf.updateFood(this.isRottenMeatAndBones.isEnabled(), this.isRawMeat.isEnabled(), this.isRawFish.isEnabled(),
                this.isSpecialFish.isEnabled(), this.isCookedMeat.isEnabled(), this.isCookedFish.isEnabled());
        SWMessages.sendToServer(new WolfFoodConfigMessageToServer(this.wolf));
    }

    @Override
    protected ResourceLocation getBackground() {
        return Resources.FOOD_GUI;
    }

}

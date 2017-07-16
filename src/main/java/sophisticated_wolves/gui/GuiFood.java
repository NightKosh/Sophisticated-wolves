package sophisticated_wolves.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;
import sophisticated_wolves.MessageHandler;
import sophisticated_wolves.Resources;
import sophisticated_wolves.SophisticatedWolvesMod;
import sophisticated_wolves.entity.EntitySophisticatedWolf;
import sophisticated_wolves.gui.component.GuiCheckbox;
import sophisticated_wolves.packets.FoodMessageToServer;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class GuiFood extends GuiScreen {

    private final String screenTitle = SophisticatedWolvesMod.proxy.getLocalizedString("gui.food.title");
    private final String doneButtonText = SophisticatedWolvesMod.proxy.getLocalizedString("gui.food.done_button");
    private EntitySophisticatedWolf wolf;
    private GuiCheckbox isRottenMeatAndBones;
    private GuiCheckbox isRawFish;
    private GuiCheckbox isCookedFish;
    private GuiCheckbox isSpecialFish;
    private GuiCheckbox isRawMeat;
    private GuiCheckbox isCookedMeat;

    protected int xSize = 248;
    protected int ySize = 149;

    public GuiFood(EntitySophisticatedWolf wolf) {
        this.wolf = wolf;
    }

    @Override
    public void initGui() {
        this.buttonList.clear();
        super.initGui();
        Keyboard.enableRepeatEvents(true); //pauses the game when GUI is opened

        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;

        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 100, doneButtonText));
        this.buttonList.add(isRottenMeatAndBones = new GuiCheckbox(1, i + 14, j + 40, this.wolf.isRottenMeatAndBones()));
        this.buttonList.add(isRawFish = new GuiCheckbox(2, i + 14, j + 60, this.wolf.isRawFish()));
        this.buttonList.add(isCookedFish = new GuiCheckbox(3, i + 84, j + 60, this.wolf.isCookedFish()));
        this.buttonList.add(isSpecialFish = new GuiCheckbox(4, i + 150, j + 60, this.wolf.isSpecialFish()));
        this.buttonList.add(isRawMeat = new GuiCheckbox(5, i + 14, j + 81, this.wolf.isRawMeat()));
        this.buttonList.add(isCookedMeat = new GuiCheckbox(6, i + 118, j + 81, this.wolf.isCookedMeat()));
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false); //unpauses the game
    }

    //keys and input
    @Override
    protected void actionPerformed(GuiButton guibutton) {
        if (guibutton.id == 0) {
            MessageHandler.networkWrapper.sendToServer(new FoodMessageToServer(this.wolf, isRottenMeatAndBones.isEnabled(), isRawMeat.isEnabled(),
                    isRawFish.isEnabled(), isSpecialFish.isEnabled(), isCookedMeat.isEnabled(), isCookedFish.isEnabled()));
            wolf.updateFood(isRottenMeatAndBones.isEnabled(), isRawMeat.isEnabled(), isRawFish.isEnabled(), isSpecialFish.isEnabled(),
                    isCookedMeat.isEnabled(), isCookedFish.isEnabled());
            this.mc.displayGuiScreen(null);
        }
    }

    @Override
    public void drawScreen(int x, int y, float tick) {
        this.drawDefaultBackground();

        this.mc.getTextureManager().bindTexture(Resources.FOOD_GUI);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;

        // draw bones background
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

        this.drawCenteredString(this.fontRendererObj, this.screenTitle, this.width / 2, 65, 0xffffff);

        super.drawScreen(x, y, tick);
    }
}

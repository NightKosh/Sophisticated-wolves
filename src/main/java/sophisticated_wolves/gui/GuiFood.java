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

        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;

        this.buttonList.add(new GuiButton(0, x + 16, y + 113, doneButtonText));
        this.buttonList.add(isRottenMeatAndBones = new GuiCheckbox(1, x + 14, y + 40, this.wolf.isRottenMeatAndBones()));
        this.buttonList.add(isRawFish = new GuiCheckbox(2, x + 14, y + 60, this.wolf.isRawFish()));
        this.buttonList.add(isCookedFish = new GuiCheckbox(3, x + 84, y + 60, this.wolf.isCookedFish()));
        this.buttonList.add(isSpecialFish = new GuiCheckbox(4, x + 150, y + 60, this.wolf.isSpecialFish()));
        this.buttonList.add(isRawMeat = new GuiCheckbox(5, x + 14, y + 81, this.wolf.isRawMeat()));
        this.buttonList.add(isCookedMeat = new GuiCheckbox(6, x + 118, y + 81, this.wolf.isCookedMeat()));
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
        int xPos = (this.width - this.xSize) / 2;
        int yPos = (this.height - this.ySize) / 2;

        // draw bones background
        this.drawTexturedModalRect(xPos, yPos, 0, 0, this.xSize, this.ySize);

        this.drawCenteredString(this.fontRenderer, this.screenTitle, xPos + 120, yPos + 15, 0xffffff);

        super.drawScreen(x, y, tick);
    }
}

package sophisticated_wolves.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.passive.EntityTameable;
import org.lwjgl.input.Keyboard;
import sophisticated_wolves.MessageHandler;
import sophisticated_wolves.SophisticatedWolvesMod;
import sophisticated_wolves.packets.PetNameMessageToServer;

/**
 * Sophisticated Wolves
 *
 * @author metroidfood
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class GuiEditName extends GuiScreen {

    private final String screenTitle = SophisticatedWolvesMod.proxy.getLocalizedString("gui.edit_dog_name");
    private GuiTextField nameField;
    private EntityTameable pet;

    public GuiEditName(EntityTameable pet) {
        this.pet = pet;
    }

    @Override
    public void initGui() {
        this.buttonList.clear();
        super.initGui();
        Keyboard.enableRepeatEvents(true); //pauses the game when GUI is opened
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 120, "Done"));
        this.nameField = new GuiTextField(this.fontRendererObj, this.width / 2 - 40, 100, 80, 20); //Textbox size
        this.nameField.setText(this.pet.getCustomNameTag());
        this.nameField.setFocused(true);
        this.nameField.setMaxStringLength(12); //string length
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false); //unpauses the game
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        this.nameField.updateCursorCounter();
    }

    //keys and input
    @Override
    protected void actionPerformed(GuiButton guibutton) {
        if (guibutton.id == 0) {
            MessageHandler.networkWrapper.sendToServer(new PetNameMessageToServer(this.pet, this.nameField.getText()));
            this.mc.displayGuiScreen(null);
        }
    }

    @Override
    protected void keyTyped(char c, int i) {
        this.nameField.textboxKeyTyped(c, i);
    }

    @Override
    public void drawScreen(int i, int j, float f) {
        this.drawDefaultBackground();
        super.drawScreen(i, j, f);
        this.drawCenteredString(this.fontRendererObj, this.screenTitle, this.width / 2, 60, 0xffffff);
        if (this.nameField != null && this.nameField.getText() != null) {
            this.nameField.drawTextBox();
        }
    }
}

package sophisticated_wolves.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.util.ChatAllowedCharacters;
import org.lwjgl.input.Keyboard;

public class GuiEditName extends GuiScreen {
    private GuiTextField nameField;
    private EntityTameable pet;
    protected String screenTitle;

    public GuiEditName(EntityTameable pet) {
        this.screenTitle = "Give your pet a name:"; //Text that appears at the top of the screen
        this.pet = pet;
    }

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

    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false); //unpauses the game

        this.pet.setCustomNameTag(this.nameField.getText());
    }

    public void updateScreen() {
        super.updateScreen();
        this.nameField.updateCursorCounter();
    }

    //keys and input
    protected void actionPerformed(GuiButton guibutton) {
        if (!guibutton.enabled) {
            return;
        }
        if (guibutton.id == 0) {
            this.mc.displayGuiScreen(null);
        }
    }

    protected void keyTyped(char c, int i) {
        this.nameField.textboxKeyTyped(c, i);
    }

    public void drawScreen(int i, int j, float f) {
        this.drawDefaultBackground();
        super.drawScreen(i, j, f);
        this.drawCenteredString(this.fontRendererObj, this.screenTitle, this.width / 2, 60, 0xffffff);
        if (this.nameField != null && this.nameField.getText() != null) {
            this.nameField.drawTextBox();
        }
    }

}

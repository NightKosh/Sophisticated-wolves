package sophisticated_wolves.gui.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.TamableAnimal;
import sophisticated_wolves.core.SWMessages;
import sophisticated_wolves.packets.PetNameMessageToServer;

import javax.annotation.Nonnull;

/**
 * Sophisticated Wolves
 *
 * @author metroidfood
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class DogTagScreen extends Screen {

    private final Component title = Component.translatable("gui.sophisticated_wolves.edit_dog_name.title");
    private final TamableAnimal animal;

    private EditBox nameField;

    public DogTagScreen(TamableAnimal animal) {
        super(Component.empty());
        this.animal = animal;
    }

    public static void open(TamableAnimal dog) {
        Minecraft.getInstance().setScreen(new DogTagScreen(dog));
    }

    @Override
    public void init() {
        int x = this.width / 2;
        int y = this.height / 4;

        this.nameField = new EditBox(this.font, x - 50, 100, 100, 20,
                null, Component.empty());
        this.nameField.setMaxLength(32);
        this.nameField.setFocused(true);
        this.nameField.setCanLoseFocus(false);
        this.nameField.moveCursorToEnd(false);
        if (animal.hasCustomName()) {
            this.nameField.setValue(animal.getCustomName().getString());
        }

        this.addRenderableWidget(
                Button.builder(CommonComponents.GUI_DONE, (button) -> {
                            SWMessages.sendToServer(
                                    new PetNameMessageToServer(this.animal.getId(), this.nameField.getValue())
                            );
                            this.minecraft.setScreen(null); // close GUI
                        })
                        .bounds(x + 5, y + 120, 55, 20)
                        .build()
        );
        this.addRenderableWidget(nameField);
    }

    @Override
    public void render(@Nonnull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);

        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 60, 0xffffff);
    }

    @Override
    public boolean keyPressed(@Nonnull KeyEvent keyEvent) {
        this.nameField.keyPressed(keyEvent);
        return super.keyPressed(keyEvent);
    }

    @Override
    public boolean charTyped(@Nonnull CharacterEvent characterEvent) {
        return this.nameField.charTyped(characterEvent);
    }

}

package sophisticated_wolves.gui.screen;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.TamableAnimal;
import sophisticated_wolves.core.SWMessages;
import sophisticated_wolves.packets.PetNameMessageToServer;

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
        this.minecraft.keyboardHandler.setSendRepeatsToGui(true); //pauses the game when GUI is opened

        int x = this.width / 2;
        int y = this.height / 4;

        this.nameField = new EditBox(this.font,x - 50, 100, 100, 20,
                        null, Component.empty());
        this.nameField.setMaxLength(32);
        this.nameField.setFocus(true);
        this.nameField.setCanLoseFocus(false);
        this.nameField.moveCursorToEnd();
        if (animal.hasCustomName()) {
            this.nameField.setValue(animal.getCustomName().getString());
        }

        this.addRenderableWidget(
                new Button(x - 55, y + 120, 55, 20,
                        CommonComponents.GUI_CANCEL,
                        (button) -> this.minecraft.setScreen(null)));
        this.addRenderableWidget(
                new Button(x + 5, y + 120, 55, 20,
                        CommonComponents.GUI_DONE,
                        (button) ->  {
                            SWMessages.sendToServer(new PetNameMessageToServer(this.animal, this.nameField.getValue()));
                            this.minecraft.setScreen(null);
                        }));
        this.addRenderableWidget(nameField);
    }

    @Override
    public void render(PoseStack poseStack, int i, int j, float f) {
        Lighting.setupForFlatItems();
        this.renderBackground(poseStack);

        this.drawCenteredString(poseStack, this.font, this.title, this.width / 2, 60, 0xffffff);

        Lighting.setupFor3DItems();
        super.render(poseStack, i, j, f);
    }

    @Override
    public boolean keyPressed(int p_96552_, int p_96553_, int p_96554_) {
        this.nameField.keyPressed(p_96552_, p_96553_, p_96554_);
        return super.keyPressed(p_96552_, p_96553_, p_96554_);
    }

    @Override
    public boolean charTyped(char c, int i) {
        return this.nameField.charTyped(c, i);
    }

    @Override
    public void removed() {
        this.minecraft.keyboardHandler.setSendRepeatsToGui(false);
    }

}

package sophisticated_wolves.gui;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
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
public class WolfFoodConfigScreen extends Screen {

    private final Component title = Component.translatable("gui.sophisticated_wolves.wolf_food_configs.title");
    private final SophisticatedWolf wolf;
    private GuiCheckbox isRottenMeatAndBones;
    private GuiCheckbox isRawFish;
    private GuiCheckbox isCookedFish;
    private GuiCheckbox isSpecialFish;
    private GuiCheckbox isRawMeat;
    private GuiCheckbox isCookedMeat;

    protected int xSize = 248;
    protected int ySize = 149;

    public WolfFoodConfigScreen(SophisticatedWolf wolf) {
        super(Component.empty());
        this.wolf = wolf;
    }

    public static void open(SophisticatedWolf wolf) {
        Minecraft.getInstance().setScreen(new WolfFoodConfigScreen(wolf));
    }

    @Override
    public void init() {
        this.minecraft.keyboardHandler.setSendRepeatsToGui(true); //pauses the game when GUI is opened

        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;

        this.isRottenMeatAndBones = new GuiCheckbox(x + 14, y + 40, wolf.isRottenMeatAndBones());
        this.isRawFish = new GuiCheckbox(x + 14, y + 60, wolf.isRawFish());
        this.isCookedFish = new GuiCheckbox(x + 84, y + 60, wolf.isCookedFish());
        this.isSpecialFish = new GuiCheckbox(x + 150, y + 60, wolf.isSpecialFish());
        this.isRawMeat = new GuiCheckbox(x + 14, y + 81, wolf.isRawMeat());
        this.isCookedMeat = new GuiCheckbox(x + 118, y + 81, wolf.isCookedMeat());

        this.addRenderableWidget(
                new Button(this.width / 2 - 50, y + 113, 100, 20,
                        CommonComponents.GUI_DONE,
                        (button) -> this.minecraft.setScreen(null)));
        this.addRenderableWidget(this.isRottenMeatAndBones);
        this.addRenderableWidget(this.isRawFish);
        this.addRenderableWidget(this.isCookedFish);
        this.addRenderableWidget(this.isSpecialFish);
        this.addRenderableWidget(this.isRawMeat);
        this.addRenderableWidget(this.isCookedMeat);
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
    public void renderBackground(PoseStack poseStack) {
        super.renderBackground(poseStack);

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.setShaderTexture(0, Resources.FOOD_GUI);

        int xPos = (this.width - this.xSize) / 2;
        int yPos = (this.height - this.ySize) / 2;
        this.blit(poseStack, xPos, yPos, 0, 0, 256, 256);
    }

    @Override
    public void removed() {
        this.minecraft.keyboardHandler.setSendRepeatsToGui(false);
        wolf.updateFood(this.isRottenMeatAndBones.isEnabled(), this.isRawMeat.isEnabled(), this.isRawFish.isEnabled(),
                this.isSpecialFish.isEnabled(), this.isCookedMeat.isEnabled(), this.isCookedFish.isEnabled());
        SWMessages.sendToServer(new WolfFoodConfigMessageToServer(this.wolf));
    }

}

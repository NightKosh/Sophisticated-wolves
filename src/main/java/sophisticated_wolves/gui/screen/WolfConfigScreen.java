package sophisticated_wolves.gui.screen;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import sophisticated_wolves.entity.SophisticatedWolf;
import sophisticated_wolves.gui.component.GuiTabButton;

import javax.annotation.Nonnull;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public abstract class WolfConfigScreen extends Screen {

    private static final int TAB_BUTTONS_Y = 29;
    private static final int X_SIZE = 248;
    private static final int Y_SIZE = 149;

    protected static final int COLUMN_1 = 14;
    protected static final int COLUMN_2 = 118;
    protected static final int LINE_1 = 40;
    protected static final int LINE_2 = 61;
    protected static final int LINE_3 = 81;

    protected final SophisticatedWolf wolf;

    protected GuiTabButton foodScreenButton;
    protected GuiTabButton targetsScreenButton;
    protected GuiTabButton commandsScreenButton;

    public WolfConfigScreen(SophisticatedWolf wolf, Component component) {
        super(component);
        this.wolf = wolf;
    }

    @Override
    public void init() {
        int x = (this.width - X_SIZE) / 2;
        int y = (this.height - Y_SIZE) / 2;

        this.addRenderableWidget(this.foodScreenButton = new GuiTabButton(x + 3, y - TAB_BUTTONS_Y,
                Component.translatable("gui.sophisticated_wolves.wolf_configs.food_button"),
                (button) -> this.minecraft.setScreen(new WolfFoodConfigScreen(this.wolf))));
        this.addRenderableWidget(this.targetsScreenButton = new GuiTabButton(x + 3 + GuiTabButton.X_SIZE, y - TAB_BUTTONS_Y,
                Component.translatable("gui.sophisticated_wolves.wolf_configs.targets_button"),
                (button) -> this.minecraft.setScreen(new WolfTargetsConfigScreen(this.wolf))));
        this.addRenderableWidget(this.commandsScreenButton = new GuiTabButton(x + 3 + GuiTabButton.X_SIZE * 2, y - TAB_BUTTONS_Y,
                Component.translatable("gui.sophisticated_wolves.wolf_configs.commands_button"),
                (button) -> this.minecraft.setScreen(new WolfCommandsConfigScreen(this.wolf))));

        this.addRenderableWidget(
                Button.builder(CommonComponents.GUI_DONE, (button) -> this.minecraft.setScreen(null))
                        .bounds(this.width / 2 - 50, y + 113, 100, 20)
                        .build()
        );
        this.initCustomComponents(x, y);
    }

    @Override
    public void render(@Nonnull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        Lighting.setupForFlatItems();
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);

        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 60, 0xffffff);

        Lighting.setupFor3DItems();
        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    @Override
    public void renderBackground(@Nonnull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.renderBackground(guiGraphics, mouseX, mouseY, partialTick);

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.setShaderTexture(0, getBackground());

        int xPos = (this.width - X_SIZE) / 2;
        int yPos = (this.height - Y_SIZE) / 2;
        guiGraphics.blit(getBackground(), xPos, yPos, 0, 0, 256, 256);
    }

    @Override
    public void removed() {
        this.onScreenClosed();
    }

    protected abstract void initCustomComponents(int x, int y);

    protected abstract ResourceLocation getBackground();

    protected abstract void onScreenClosed();

}

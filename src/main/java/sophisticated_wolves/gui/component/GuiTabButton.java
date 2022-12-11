package sophisticated_wolves.gui.component;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import sophisticated_wolves.core.Resources;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class GuiTabButton extends Button {

    public static final int X_SIZE = 81;
    public static final int Y_SIZE = 30;
    public static final int Y_SIZE_ENABLED = 32;

    private boolean isTabActive = false;

    public GuiTabButton(int x, int y, Component component, Button.OnPress onPress) {
        super(x, y, X_SIZE, Y_SIZE_ENABLED, component, onPress);
    }

    @Override
    public void renderButton(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.setShaderTexture(0, Resources.TAB_BUTTON_GUI);

        if (this.isTabActive) {
            this.blit(poseStack, this.x, this.y, 0, 30, this.width, Y_SIZE_ENABLED);
        } else {
            this.blit(poseStack, this.x, this.y, 0, 0, this.width, Y_SIZE);
        }
        drawCenteredString(poseStack, Minecraft.getInstance().font, this.getMessage(),
                this.x + this.width / 2, this.y + (this.height - 8) / 2,
                this.getFGColor() | Mth.ceil(this.alpha * 255F) << 24);
    }

    public boolean isTabActive() {
        return this.isTabActive;
    }

    public void setTabActive(boolean tabActive) {
        this.isTabActive = tabActive;
    }

}

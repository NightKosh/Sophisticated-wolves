package sophisticated_wolves.gui.component;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import sophisticated_wolves.core.Resources;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class GuiCheckbox extends Button {

    private static final int X_SIZE = 15;
    private static final int Y_SIZE = 15;

    private boolean enabled;

    public GuiCheckbox(int x, int y, Boolean enabled) {
        super(x, y, X_SIZE, Y_SIZE, Component.empty(), (button) -> ((GuiCheckbox) button).onClick());
        this.enabled = enabled;
    }

    @Override
    public void renderButton(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.setShaderTexture(0, Resources.CHECKBOX_GUI);

        this.blit(poseStack, this.x, this.y, 0, 0, this.width, this.height);
        if (this.enabled) {
            this.blit(poseStack, this.x, this.y, 15, 0, this.width, this.height);
        }
    }

    private void onClick() {
        this.enabled = !this.enabled;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

}

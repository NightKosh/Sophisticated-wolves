package sophisticated_wolves.gui.component;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import sophisticated_wolves.core.SWResources;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.Supplier;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class GuiCheckbox extends Button {

    private static final int X_SIZE = 15;
    private static final int Y_SIZE = 15;

    private GetCheckboxesGroup group;
    private boolean hasText = true;
    private boolean enabled;

    public GuiCheckbox(int x, int y, Boolean enabled) {
        this(x, y, null, enabled);
    }

    public GuiCheckbox(int x, int y, GuiCheckbox.GetCheckboxesGroup group, Boolean enabled) {
        super(x, y, X_SIZE, Y_SIZE, Component.empty(), (button) -> ((GuiCheckbox) button).onClick(), Supplier::get);
        this.group = group;
        this.enabled = enabled;
        this.hasText = false;
    }

    public GuiCheckbox(int x, int y, int width, Component component, Boolean enabled) {
        this(x, y, width, component, null, enabled);
    }

    public GuiCheckbox(int x, int y, int width, Component component, GuiCheckbox.GetCheckboxesGroup group, Boolean enabled) {
        super(x, y, width, Y_SIZE, component, (button) -> ((GuiCheckbox) button).onClick(), Supplier::get);
        this.group = group;
        this.enabled = enabled;
    }

    @Override
    public void renderWidget(@Nonnull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.setShaderTexture(0, SWResources.CHECKBOX_GUI);

        guiGraphics.blit(SWResources.CHECKBOX_GUI, this.getX(), this.getY(), 0, 0, X_SIZE, Y_SIZE);
        if (this.enabled) {
            guiGraphics.blit(SWResources.CHECKBOX_GUI, this.getX(), this.getY(), Y_SIZE, 0, X_SIZE, Y_SIZE);
        }

        if (this.hasText) {
            guiGraphics.drawString(Minecraft.getInstance().font, this.getMessage(),
                    this.getX() + X_SIZE, this.getY() + (this.height - 8) / 2,
                    this.getFGColor() | Mth.ceil(this.alpha * 255F) << 24);
        }
    }

    private void onClick() {
        if (group != null) {
            for (var checkbox : group.get()) {
                checkbox.enabled = this.enabled;
            }
        }
        this.enabled = !this.enabled;
    }

    public boolean isEnabled() {
        return this.enabled;
    }


    public interface GetCheckboxesGroup {
        List<GuiCheckbox> get();
    }

}

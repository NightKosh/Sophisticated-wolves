package sophisticated_wolves.gui.component;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import sophisticated_wolves.core.SWResources;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

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

    private static final int TEXTURE_SIZE = 256;

    private boolean isTabActive = false;

    public GuiTabButton(int x, int y, Component component, Button.OnPress onPress) {
        super(x, y, X_SIZE, Y_SIZE_ENABLED, component, onPress, Supplier::get);
    }

    @Override
    public void renderContents(@Nonnull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        if (this.isTabActive) {
            guiGraphics.blit(RenderPipelines.GUI_TEXTURED, SWResources.TAB_BUTTON_GUI,
                    this.getX(), this.getY(),
                    0, 30,
                    this.width, Y_SIZE_ENABLED,
                    TEXTURE_SIZE, TEXTURE_SIZE);
        } else {
            guiGraphics.blit(RenderPipelines.GUI_TEXTURED, SWResources.TAB_BUTTON_GUI,
                    this.getX(), this.getY(),
                    0, 0,
                    this.width, Y_SIZE,
                    TEXTURE_SIZE, TEXTURE_SIZE);
        }
        guiGraphics.drawCenteredString(Minecraft.getInstance().font, this.getMessage(),
                this.getX() + this.width / 2, this.getY() + (this.height - 8) / 2,
                this.getFGColor() | Mth.ceil(this.alpha * 255F) << 24);
    }

    public boolean isTabActive() {
        return this.isTabActive;
    }

    public void setTabActive(boolean tabActive) {
        this.isTabActive = tabActive;
    }

}

package sophisticated_wolves.gui.component;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sophisticated_wolves.Resources;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
@SideOnly(Side.CLIENT)
public class GuiCheckbox extends GuiButton {

    protected int xSize = 15;
    protected int ySize = 15;

    private boolean enabled;

    public GuiCheckbox(int id, int x, int y, boolean enabled) {
        super(id, x, y, 15, 15, "");
        this.enabled = enabled;
    }

    @Override
    public void drawButton(Minecraft minecraft, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        minecraft.getTextureManager().bindTexture(Resources.CHECKBOX_GUI);
        this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 0, this.width, this.height);
        if (this.enabled) {
            this.drawTexturedModalRect(this.xPosition, this.yPosition, 15, 0, this.width, this.height);
        }

    }

    @Override
    public void mouseReleased(int mouseX, int mouseY) {
        this.enabled = !this.enabled;
    }

    public boolean isEnabled() {
        return this.enabled;
    }
}

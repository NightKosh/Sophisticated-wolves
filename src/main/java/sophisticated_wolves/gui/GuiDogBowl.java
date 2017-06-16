package sophisticated_wolves.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import sophisticated_wolves.Resources;
import sophisticated_wolves.block.BlockDogBowl;
import sophisticated_wolves.gui.container.ContainerDogBowl;
import sophisticated_wolves.tile_entity.TileEntityDogBowl;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class GuiDogBowl extends GuiContainer {

    private final TileEntityDogBowl te;

    public GuiDogBowl(InventoryPlayer playerInv, TileEntityDogBowl te) {
        super(new ContainerDogBowl(playerInv, te));
        this.te = te;
    }

    /**
     * Draws the background layer of this container (behind the items).
     */
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(Resources.DOG_BOWL_GUI);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;

        // draw bones background
        this.drawTexturedModalRect(i + 35, j + 14, 109, 168, 108, 57);

        // draw bones
        int amountOfFood = te.getFoodAmount();
        int bonesLevel = (amountOfFood == 0) ? 1 : amountOfFood / BlockDogBowl.EnumDogBowl.BONES_PER_LEVEL + 1;
        int amountOfBonesForLevel = (amountOfFood == 0) ? 0 : amountOfFood % BlockDogBowl.EnumDogBowl.BONES_PER_LEVEL;
        if (amountOfFood > BlockDogBowl.EnumDogBowl.EMPTY.getAmountOfFood()) {
            drawBones(i, j + 14, 1, bonesLevel, amountOfBonesForLevel);
            if (amountOfFood > BlockDogBowl.EnumDogBowl.FILLED1.getAmountOfFood()) {
                drawBones(i, j + 29, 2, bonesLevel, amountOfBonesForLevel);
                if (amountOfFood > BlockDogBowl.EnumDogBowl.FILLED2.getAmountOfFood()) {
                    drawBones(i, j + 44, 3, bonesLevel, amountOfBonesForLevel);
                    if (amountOfFood > BlockDogBowl.EnumDogBowl.FILLED3.getAmountOfFood()) {
                        drawBones(i, j + 59, 4, bonesLevel, amountOfBonesForLevel);
                    }
                }
            }
        }

        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
    }

    private void drawBones(int i, int j, int level, int bonesLevel, int amountOfBones) {
        if (bonesLevel > level) {
            this.drawTexturedModalRect(i + 34, j, 0, 168, 108, 12);
        } else {
            int offset = amountOfBones * 4 + 12;
            this.drawTexturedModalRect(i + 34, j, 112 - offset, 168, offset - 4, 12);
        }
    }
}

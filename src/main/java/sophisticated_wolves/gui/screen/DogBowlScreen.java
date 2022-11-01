package sophisticated_wolves.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import sophisticated_wolves.block.BlockDogBowl;
import sophisticated_wolves.block_entity.BlockEntityDogBowl;
import sophisticated_wolves.core.Resources;
import sophisticated_wolves.gui.menu.DogBowlContainerMenu;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class DogBowlScreen extends AbstractContainerScreen<DogBowlContainerMenu> {

    private static final int BONES_X_OFFSET = 34;
    private static final int BONES_Y_OFFSET_1 = 13;
    private static final int BONES_Y_OFFSET_2 = 28;
    private static final int BONES_Y_OFFSET_3 = 43;
    private static final int BONES_Y_OFFSET_4 = 58;
    private static final int BONE_WIDTH = 4;
    private static final int BONE_HEIGHT = 12;

    private final BlockEntityDogBowl dogBowl;

    public DogBowlScreen(DogBowlContainerMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
        this.dogBowl = menu.getDogBowl();
    }

    @Override
    protected void renderBg(PoseStack poseStack, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.setShaderTexture(0, Resources.DOG_BOWL_GUI);

        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;

        //draw bones background
        this.blit(poseStack, x + BONES_X_OFFSET, y + BONES_Y_OFFSET_1, 109, 167, 108, 57);

        // draw bones
        int amountOfFood = dogBowl.getFoodAmount();
        int bonesLevel = (amountOfFood == 0) ? 1 : amountOfFood / BlockDogBowl.EnumDogBowl.BONES_PER_LEVEL + 1;
        int amountOfBonesForLevel = (amountOfFood == 0) ? 0 : amountOfFood % BlockDogBowl.EnumDogBowl.BONES_PER_LEVEL;
        if (amountOfFood > BlockDogBowl.EnumDogBowl.EMPTY.getAmountOfFood()) {
            drawBones(poseStack, x, y + BONES_Y_OFFSET_1, 1, bonesLevel, amountOfBonesForLevel);
            if (amountOfFood > BlockDogBowl.EnumDogBowl.FILLED_25_P.getAmountOfFood()) {
                drawBones(poseStack, x, y + BONES_Y_OFFSET_2, 2, bonesLevel, amountOfBonesForLevel);
                if (amountOfFood > BlockDogBowl.EnumDogBowl.FILLED_50_P.getAmountOfFood()) {
                    drawBones(poseStack, x, y + BONES_Y_OFFSET_3, 3, bonesLevel, amountOfBonesForLevel);
                    if (amountOfFood > BlockDogBowl.EnumDogBowl.FILLED_75_P.getAmountOfFood()) {
                        drawBones(poseStack, x, y + BONES_Y_OFFSET_4, 4, bonesLevel, amountOfBonesForLevel);
                    }
                }
            }
        }

        this.blit(poseStack, x, y, 0, 0, imageWidth, imageHeight);
    }

    private void drawBones(PoseStack poseStack, int x, int y, int level, int bonesLevel, int amountOfBones) {
        if (bonesLevel > level) {
            this.blit(poseStack, x + BONES_X_OFFSET, y, 0, 167, 108, BONE_HEIGHT);
        } else {
            int offset = amountOfBones * BONE_WIDTH + 12;
            this.blit(poseStack, x + BONES_X_OFFSET, y, 112 - offset, 167, offset - 4, BONE_HEIGHT);
        }
    }

    @Override
    public void render(PoseStack pPoseStack, int mouseX, int mouseY, float delta) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, mouseX, mouseY, delta);
    }

}

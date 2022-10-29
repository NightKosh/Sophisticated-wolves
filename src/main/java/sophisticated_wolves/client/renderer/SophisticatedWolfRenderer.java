package sophisticated_wolves.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.WolfRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Wolf;
import sophisticated_wolves.Resources;
import sophisticated_wolves.core.SWConfiguration;
import sophisticated_wolves.entity.SophisticatedWolf;

/**
 * Sophisticated Wolves
 *
 * @author metroidfood
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)git
 */
public class SophisticatedWolfRenderer extends WolfRenderer {

    private static final int COLOR_FULL_HEALTH = 553648127;
    private static final int COLOR_75_99_P_HEALTH = 0xfffecccc;
    private static final int COLOR_50_75_P_HEALTH = 0xffff9696;
    private static final int COLOR_25_50_P_HEALTH = 0xfffe5656;
    private static final int COLOR_0_25_P_HEALTH = 0xffff0202;

    private static final int HEALTH_75_P = SWConfiguration.WOLVES_HEALTH_TAMED.get() / 4 * 3;
    private static final int HEALTH_50_P = SWConfiguration.WOLVES_HEALTH_TAMED.get() / 2;
    private static final int HEALTH_25_P = SWConfiguration.WOLVES_HEALTH_TAMED.get() / 4;

    public SophisticatedWolfRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(Wolf wolf) {
        if (SWConfiguration.CUSTOM_WOLF_TEXTURES.get()) {
            switch (((SophisticatedWolf) wolf).getSpecies()) {
                case BROWN:
                    if (wolf.isTame()) {
                        return Resources.BROWN_WOLF_TAME;
                    } else if (wolf.isAngry()) {
                        return Resources.BROWN_WOLF_ANGRY;
                    }
                    return Resources.BROWN_WOLF;
                case BLACK:
                    if (wolf.isTame()) {
                        return Resources.BLACK_WOLF_TAME;
                    } else if (wolf.isAngry()) {
                        return Resources.BLACK_WOLF_ANGRY;
                    }
                    return Resources.BLACK_WOLF;
                case FOREST:
                    if (wolf.isTame()) {
                        return Resources.FOREST_WOLF_TAME;
                    } else if (wolf.isAngry()) {
                        return Resources.FOREST_WOLF_ANGRY;
                    }
                    return Resources.FOREST_WOLF;
            }
        }
        return super.getTextureLocation(wolf);
    }

    @Override
    protected void renderNameTag(Wolf wolf, Component component, PoseStack poseStack,
                                 MultiBufferSource multiBufferSource, int xz) {
        var coloredComponent = Component.literal(component.getString())
                .withStyle(component.getStyle().withColor(getWolfNameColor(wolf)));
        super.renderNameTag(wolf, coloredComponent, poseStack, multiBufferSource, xz);
    }

    //changes color of wolf's name based on health
    private static int getWolfNameColor(Wolf wolf) {
        if (wolf.isTame() && wolf.getHealth() < SWConfiguration.WOLVES_HEALTH_TAMED.get()) {
            if (wolf.getHealth() <= HEALTH_75_P) {
                if (wolf.getHealth() <= HEALTH_50_P) {
                    if (wolf.getHealth() <= HEALTH_25_P) {
                        return COLOR_0_25_P_HEALTH;
                    }
                    return COLOR_25_50_P_HEALTH;
                }
                return COLOR_50_75_P_HEALTH;
            }
            return COLOR_75_99_P_HEALTH;
        }
        return COLOR_FULL_HEALTH;
    }

}

package sophisticated_wolves;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderWolf;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.opengl.GL11;
import sophisticated_wolves.api.EnumWolfSpecies;
import sophisticated_wolves.entity.EntitySophisticatedWolf;

/**
 * Sophisticated Wolves
 *
 * @author metroidfood
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)git
 */
public class RenderSophisticatedWolf extends RenderWolf {

    public RenderSophisticatedWolf(ModelBase modelBase) {
        super(Minecraft.getMinecraft().getRenderManager(), modelBase, 0.5F);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityWolf entity) {
        EntitySophisticatedWolf wolf = (EntitySophisticatedWolf) entity;
        if (SWConfiguration.customWolfTextures) {
            if (wolf.getSpecies() == EnumWolfSpecies.BROWN) {
                if (wolf.isTamed())
                    return Resources.brownWolfTame;
                if (wolf.isAngry())
                    return Resources.brownWolfAngry;
                else
                    return Resources.brownWolf;
            }
            if (wolf.getSpecies() == EnumWolfSpecies.BLACK) {
                if (wolf.isTamed())
                    return Resources.blackWolfTame;
                if (wolf.isAngry())
                    return Resources.blackWolfAngry;
                else
                    return Resources.blackWolf;
            }
            if (wolf.getSpecies() == EnumWolfSpecies.FOREST) {
                if (wolf.isTamed())
                    return Resources.forestWolfTame;
                if (wolf.isAngry())
                    return Resources.forestWolfAngry;
                else
                    return Resources.forestWolf;
            } else {
                return super.getEntityTexture(wolf);
            }
        } else {
            return super.getEntityTexture(wolf);
        }
    }

    //Custom Functions below here
    //Function called by RenderLiving for special renders, used to call nametag function
    @Override
    public void renderName(EntityWolf entity, double x, double y, double z) {
        EntitySophisticatedWolf wolf = (EntitySophisticatedWolf) entity;

        if (wolf.isTamed() && Minecraft.isGuiEnabled() && StringUtils.isNotBlank(wolf.getCustomNameTag())) {
            if (wolf.getCustomNameTag().length() > 0) {
                this.renderWolfName(wolf, x, y, z);
            }
        }
    }

    //renders wolf nametag
    public void renderWolfName(EntitySophisticatedWolf wolf, double d, double d1, double d2) {
        float f = 1.6F;
        float f1 = 0.01666667F * f;
        float f2 = wolf.getDistanceToEntity(this.renderManager.livingPlayer);
        float f3 = wolf.isSitting() ? 32 : 64;
        if (f2 < f3) {
            String wolfName = wolf.getCustomNameTag();
            FontRenderer fontRenderer = this.getFontRendererFromRenderManager();
            GL11.glPushMatrix();
            GL11.glTranslatef((float) d, (float) d1 + 1.5F, (float) d2);
            GL11.glNormal3f(0, 1, 0);
            GL11.glRotatef(-this.renderManager.playerViewY, 0, 1, 0);
            GL11.glRotatef(this.renderManager.playerViewX, 1, 0, 0);
            GL11.glScalef(-f1, -f1, f1);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glTranslatef(0, 0.25F / f1, 0);
            GL11.glDepthMask(false);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            Tessellator tessellator = Tessellator.getInstance();
            //TODO !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//            WorldRenderer worldRenderer = tessellator.getBuffer().getWorldRenderer();
//            GL11.glDisable(GL11.GL_TEXTURE_2D);
//            worldRenderer.startDrawingQuads();
//            int var16 = fontRenderer.getStringWidth(wolfName) / 2;
//            worldRenderer.setColorRGBA_F(0, 0, 0, 0.25F);
//            worldRenderer.addVertex(-var16 - 1, -1, 0);
//            worldRenderer.addVertex(-var16 - 1, 8, 0);
//            worldRenderer.addVertex(var16 + 1, 8, 0);
//            worldRenderer.addVertex(var16 + 1, -1, 0);
//            tessellator.draw();
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            if (!wolf.isSitting()) {
                fontRenderer.drawString(wolfName, -fontRenderer.getStringWidth(wolfName) / 2, 0, getSitNameColor(wolf));
                GL11.glEnable(GL11.GL_DEPTH_TEST);
                GL11.glDepthMask(true);
                fontRenderer.drawString(wolfName, -fontRenderer.getStringWidth(wolfName) / 2, 0, getWolfNameColor(wolf));
            } else {
                GL11.glDepthMask(true);
                fontRenderer.drawString(wolfName, -fontRenderer.getStringWidth(wolfName) / 2, 0, getSitNameColor(wolf));
            }
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glColor4f(1, 1, 1, 1);
            GL11.glPopMatrix();
        }
    }

    //changes color of wolf's name based on health
    public int getWolfNameColor(EntityWolf wolf) {
        if (wolf.getHealth() < 20) {
            if (wolf.getHealth() < 16) {
                if (wolf.getHealth() < 11) {
                    if (wolf.getHealth() < 6) {
                        return 0xffff0202;
                    }
                    return 0xfffe5656;
                }
                return 0xffff9696;
            }
            return 0xfffecccc;
        } else {
            return 0xffffffff;
        }
    }

    //same function but with transparency
    public int getSitNameColor(EntityWolf wolf) {
        if (wolf.getHealth() < 20) {
            if (wolf.getHealth() < 16) {
                if (wolf.getHealth() < 11) {
                    if (wolf.getHealth() < 6) {
                        return 0x40ff0202;
                    }
                    return 0x40fe5656;
                }
                return 0x40ff9696;
            }
            return 0x40fecccc;
        } else {
            return 0x40ffffff;
        }
    }
}

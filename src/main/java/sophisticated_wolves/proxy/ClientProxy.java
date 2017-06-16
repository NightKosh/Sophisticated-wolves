package sophisticated_wolves.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.item.Item;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import org.apache.commons.lang3.StringUtils;
import sophisticated_wolves.RenderSophisticatedWolf;
import sophisticated_wolves.Resources;
import sophisticated_wolves.SWBlocks;
import sophisticated_wolves.SWItems;
import sophisticated_wolves.api.EnumWolfSpecies;
import sophisticated_wolves.entity.EntitySophisticatedWolf;
import sophisticated_wolves.gui.GuiEditName;
import sophisticated_wolves.item.WolfEggColor;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class ClientProxy extends CommonProxy {

    @Override
    public void registerRenderers() {
        // Mobs renderers
        registerMobsRenderers();

        Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new WolfEggColor(), SWItems.DOG_EGG);
    }

    private void registerMobsRenderers() {
        RenderingRegistry.registerEntityRenderingHandler(EntitySophisticatedWolf.class, new RenderSophisticatedWolf());
    }

    @Override
    public String getLocalizedString(String str) {
        String localizedString = null;
        try {
            localizedString = I18n.translateToLocal(str);
        } catch (Exception e) {
        }

        if (StringUtils.isBlank(localizedString)) {
            return I18n.translateToFallback(str);
        } else {
            return localizedString;
        }
    }

    @Override
    public void openPetGui(EntityTameable pet) {
        FMLClientHandler.instance().getClient().displayGuiScreen(new GuiEditName(pet));
    }

    @Override
    public void modelsRegistration() {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(SWItems.DOG_TAG, 0, Resources.DOG_TAG_MODEL);
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(SWItems.DOG_TREAT, 0, Resources.DOG_TREAT_MODEL);
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(SWItems.PET_CARRIER, 0, Resources.PET_CARRIER_MODEL);

        for (EnumWolfSpecies wolfSpecies : EnumWolfSpecies.values()) {
            Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(SWItems.DOG_EGG, wolfSpecies.ordinal(), Resources.SPAWN_EGG_MODEL);
        }

        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(SWBlocks.DOG_BOWL), 0, Resources.DOG_BOWL);
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(SWBlocks.DOG_BOWL), 1, Resources.DOG_BOWL1);
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(SWBlocks.DOG_BOWL), 2, Resources.DOG_BOWL2);
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(SWBlocks.DOG_BOWL), 3, Resources.DOG_BOWL3);
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(SWBlocks.DOG_BOWL), 4, Resources.DOG_BOWL4);
        ModelBakery.registerItemVariants(SWItems.DOG_BOWL_IB, Resources.DOG_BOWL, Resources.DOG_BOWL1, Resources.DOG_BOWL2, Resources.DOG_BOWL3, Resources.DOG_BOWL4);
    }
}

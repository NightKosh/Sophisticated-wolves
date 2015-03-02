package sophisticated_wolves.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelWolf;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.LanguageRegistry;
import org.apache.commons.lang3.StringUtils;
import sophisticated_wolves.RenderSophisticatedWolf;
import sophisticated_wolves.Resources;
import sophisticated_wolves.SWItems;
import sophisticated_wolves.entity.EntitySophisticatedWolf;
import sophisticated_wolves.gui.GuiEditName;

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
    }

    private void registerMobsRenderers() {
        RenderingRegistry.registerEntityRenderingHandler(EntitySophisticatedWolf.class, new RenderSophisticatedWolf(new ModelWolf()));
    }

    @Override
    public String getLocalizedString(String str) {
        String localizedString = null;
        try {
            localizedString = LanguageRegistry.instance().getStringLocalization(str);
        } catch (Exception e) {
        }

        if (StringUtils.isBlank(localizedString)) {
            return LanguageRegistry.instance().getStringLocalization(str, "en_US");
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
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(SWItems.dogTag, 0, Resources.dogTagModel);
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(SWItems.dogTreat, 0, Resources.dogTreatModel);
    }
}

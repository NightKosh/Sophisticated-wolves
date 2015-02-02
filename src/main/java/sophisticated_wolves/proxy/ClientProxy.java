package sophisticated_wolves.proxy;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.client.model.ModelWolf;
import net.minecraft.entity.passive.EntityTameable;
import org.apache.commons.lang3.StringUtils;
import sophisticated_wolves.RenderSophisticatedWolf;
import sophisticated_wolves.entity.SophisticatedWolf;
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
        // zombie dog
        RenderingRegistry.registerEntityRenderingHandler(SophisticatedWolf.class, new RenderSophisticatedWolf(new ModelWolf(), new ModelWolf()));
    }

    @Override
    public String getLocalizedString(String str) {
        String localizedString = null;
        try {
            localizedString = LanguageRegistry.instance().getStringLocalization(str);
        } catch (Exception e) {}

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
}

package sophisticated_wolves.proxy;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.client.model.ModelWolf;
import sophisticated_wolves.RenderSophisticatedWolf;
import sophisticated_wolves.entity.SophisticatedWolf;

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
        String localizedString = LanguageRegistry.instance().getStringLocalization(str);
        if (localizedString.length() == 0) {
            return LanguageRegistry.instance().getStringLocalization(str, "en_US");
        } else {
            return localizedString;
        }
    }
}

package sophisticated_wolves.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import org.apache.commons.lang3.StringUtils;
import sophisticated_wolves.RenderSophisticatedWolf;
import sophisticated_wolves.SWItems;
import sophisticated_wolves.entity.EntitySophisticatedWolf;
import sophisticated_wolves.gui.GuiEditName;
import sophisticated_wolves.gui.GuiFood;
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

    public void openFoodGui(EntitySophisticatedWolf pet) {
        FMLClientHandler.instance().getClient().displayGuiScreen(new GuiFood(pet));
    }
}

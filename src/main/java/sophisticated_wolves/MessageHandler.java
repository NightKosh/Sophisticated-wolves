package sophisticated_wolves;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import sophisticated_wolves.api.ModInfo;
import sophisticated_wolves.packets.PetNameMessageToServer;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class MessageHandler {
    public static final SimpleNetworkWrapper networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(ModInfo.ID.toLowerCase());

    public static void init() {
        networkWrapper.registerMessage(PetNameMessageToServer.class, PetNameMessageToServer.class, 0, Side.SERVER);
    }
}

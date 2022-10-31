package sophisticated_wolves.core;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import sophisticated_wolves.api.ModInfo;
import sophisticated_wolves.packets.PetNameMessageToServer;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class SWMessages {

    private static SimpleChannel simpleChannel;

    private static int packetId = 0;

    private static int id() {
        return packetId++;
    }

    public static void register() {
        var net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(ModInfo.ID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        simpleChannel = net;

        net.messageBuilder(PetNameMessageToServer.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(PetNameMessageToServer::new)
                .encoder(PetNameMessageToServer::toBytes)
                .consumerMainThread(PetNameMessageToServer::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG message) {
        simpleChannel.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        simpleChannel.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

}

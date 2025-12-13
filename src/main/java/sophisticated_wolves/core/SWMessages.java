package sophisticated_wolves.core;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import sophisticated_wolves.api.ModInfo;
import sophisticated_wolves.packets.PetNameMessageToServer;
import sophisticated_wolves.packets.WolfCommandsConfigMessageToServer;
import sophisticated_wolves.packets.WolfFoodConfigMessageToServer;
import sophisticated_wolves.packets.WolfTargetsConfigMessageToServer;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
@EventBusSubscriber(modid = ModInfo.ID, bus = EventBusSubscriber.Bus.MOD)
public class SWMessages {

    public static final String NETWORK_VERSION = "1";

    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        final var registrar = event.registrar(NETWORK_VERSION);

        registrar.playToServer(
                PetNameMessageToServer.TYPE,
                PetNameMessageToServer.STREAM_CODEC,
                PetNameMessageToServer::handle
        );

        registrar.playToServer(
                WolfFoodConfigMessageToServer.TYPE,
                WolfFoodConfigMessageToServer.STREAM_CODEC,
                WolfFoodConfigMessageToServer::handle
        );

        registrar.playToServer(
                WolfTargetsConfigMessageToServer.TYPE,
                WolfTargetsConfigMessageToServer.STREAM_CODEC,
                WolfTargetsConfigMessageToServer::handle
        );

        registrar.playToServer(
                WolfCommandsConfigMessageToServer.TYPE,
                WolfCommandsConfigMessageToServer.STREAM_CODEC,
                WolfCommandsConfigMessageToServer::handle
        );
    }

    public static void sendToServer(CustomPacketPayload payload) {
        PacketDistributor.sendToServer(payload);
    }

    public static void sendToPlayer(CustomPacketPayload payload, ServerPlayer player) {
        PacketDistributor.sendToPlayer(player, payload);
    }

}

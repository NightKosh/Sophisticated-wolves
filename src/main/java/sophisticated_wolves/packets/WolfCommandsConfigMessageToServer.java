package sophisticated_wolves.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import sophisticated_wolves.api.ModInfo;
import sophisticated_wolves.entity.SophisticatedWolf;

import javax.annotation.Nonnull;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public record WolfCommandsConfigMessageToServer(int wolfId, boolean followOwner, boolean guardZone)
        implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<WolfCommandsConfigMessageToServer> TYPE =
            new CustomPacketPayload.Type<>(new ResourceLocation(ModInfo.ID, "wolf_commands_config"));

    public static final StreamCodec<ByteBuf, WolfCommandsConfigMessageToServer> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.VAR_INT, WolfCommandsConfigMessageToServer::wolfId,
                    ByteBufCodecs.BOOL, WolfCommandsConfigMessageToServer::followOwner,
                    ByteBufCodecs.BOOL, WolfCommandsConfigMessageToServer::guardZone,
                    WolfCommandsConfigMessageToServer::new
            );

    @Nonnull
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static WolfCommandsConfigMessageToServer getFromWolf(SophisticatedWolf wolf) {
        var commands = wolf.getWolfCommands();
        return new WolfCommandsConfigMessageToServer(wolf.getId(), commands.followOwner(), commands.guardZone());

    }

    public static void handle(WolfCommandsConfigMessageToServer msg, IPayloadContext context) {
        context.enqueueWork(() -> {
            var player = (ServerPlayer) context.player();
            var level = player.level();

            var entity = level.getEntity(msg.wolfId());
            if (entity instanceof SophisticatedWolf wolf) {
                wolf.updateCommands(msg.followOwner(), msg.guardZone());
            }
        });
    }

}
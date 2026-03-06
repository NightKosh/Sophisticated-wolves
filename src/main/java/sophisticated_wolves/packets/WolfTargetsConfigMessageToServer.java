package sophisticated_wolves.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import sophisticated_wolves.api.ModInfo;
import sophisticated_wolves.core.SWAdvancements;
import sophisticated_wolves.entity.SophisticatedWolf;

import javax.annotation.Nonnull;

import static net.minecraft.resources.Identifier.fromNamespaceAndPath;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public record WolfTargetsConfigMessageToServer(
        int wolfId,
        boolean attackSkeletons,
        boolean attackZombies,
        boolean attackSpiders,
        boolean attackSlimes,
        boolean attackNether,
        boolean attackRaider
) implements CustomPacketPayload {

    public static final Type<WolfTargetsConfigMessageToServer> TYPE =
            new Type<>(fromNamespaceAndPath(ModInfo.ID, "wolf_targets_config"));

    public static final StreamCodec<ByteBuf, WolfTargetsConfigMessageToServer> STREAM_CODEC =
            StreamCodec.of(
                    (buf, msg) -> {
                        ByteBufCodecs.VAR_INT.encode(buf, msg.wolfId());
                        ByteBufCodecs.BOOL.encode(buf, msg.attackSkeletons());
                        ByteBufCodecs.BOOL.encode(buf, msg.attackZombies());
                        ByteBufCodecs.BOOL.encode(buf, msg.attackSpiders());
                        ByteBufCodecs.BOOL.encode(buf, msg.attackSlimes());
                        ByteBufCodecs.BOOL.encode(buf, msg.attackNether());
                        ByteBufCodecs.BOOL.encode(buf, msg.attackRaider());
                    },
                    buf -> new WolfTargetsConfigMessageToServer(
                            ByteBufCodecs.VAR_INT.decode(buf),
                            ByteBufCodecs.BOOL.decode(buf),
                            ByteBufCodecs.BOOL.decode(buf),
                            ByteBufCodecs.BOOL.decode(buf),
                            ByteBufCodecs.BOOL.decode(buf),
                            ByteBufCodecs.BOOL.decode(buf),
                            ByteBufCodecs.BOOL.decode(buf)
                    )
            );

    @Nonnull
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static WolfTargetsConfigMessageToServer getFromWolf(SophisticatedWolf wolf) {
        return new WolfTargetsConfigMessageToServer(wolf.getId(),
                wolf.attackSkeletons(), wolf.attackZombies(), wolf.attackSpiders(),
                wolf.attackSlimes(), wolf.attackNether(), wolf.attackRaider());

    }

    public static void handle(WolfTargetsConfigMessageToServer msg, IPayloadContext context) {
        context.enqueueWork(() -> {
            ServerPlayer player = (ServerPlayer) context.player();
            var level = player.level();

            var entity = level.getEntity(msg.wolfId());
            if (entity instanceof SophisticatedWolf wolf) {
                wolf.updateTargets(
                        msg.attackSkeletons(), msg.attackZombies(), msg.attackSpiders(),
                        msg.attackSlimes(), msg.attackNether(), msg.attackRaider()
                );
                SWAdvancements.giveAdvancement(player, level, SWAdvancements.NEW_TRICKS);
                if (msg.attackZombies() || msg.attackSpiders() || msg.attackSlimes() ||
                        msg.attackNether() || msg.attackRaider()) {
                    SWAdvancements.giveAdvancement(player, level, SWAdvancements.THE_TERMINAL_LIST);
                }
            }
        });
    }

}
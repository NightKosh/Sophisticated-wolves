package sophisticated_wolves.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.TamableAnimal;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import sophisticated_wolves.api.ModInfo;

import javax.annotation.Nonnull;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public record PetNameMessageToServer(
        int animalId,
        String text
) implements CustomPacketPayload {

    public static final Type<PetNameMessageToServer> TYPE =
            new Type<>(new ResourceLocation(ModInfo.ID, "pet_name"));

    public static final StreamCodec<ByteBuf, PetNameMessageToServer> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.INT, PetNameMessageToServer::animalId,
                    ByteBufCodecs.STRING_UTF8, PetNameMessageToServer::text,
                    PetNameMessageToServer::new
            );

    @Nonnull
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(PetNameMessageToServer msg, IPayloadContext context) {
        context.enqueueWork(() -> {
            ServerPlayer player = (ServerPlayer) context.player();
            var level = player.level();

            var entity = level.getEntity(msg.animalId());
            if (entity instanceof TamableAnimal animal) {
                animal.setCustomName(Component.literal(msg.text()));
            }
        });
    }

}

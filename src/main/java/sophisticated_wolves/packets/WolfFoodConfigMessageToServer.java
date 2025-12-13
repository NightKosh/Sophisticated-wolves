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
public record WolfFoodConfigMessageToServer(
        int wolfId,
        boolean rottenMeatAndBones,
        boolean rawMeat,
        boolean rawFish,
        boolean specialFish,
        boolean cookedMeat,
        boolean cookedFish
) implements CustomPacketPayload {

    public static final Type<WolfFoodConfigMessageToServer> TYPE =
            new Type<>(new ResourceLocation(ModInfo.ID, "wolf_food_config"));

    public static final StreamCodec<ByteBuf, WolfFoodConfigMessageToServer> STREAM_CODEC =
            StreamCodec.of(
                    (buf, msg) -> {
                        ByteBufCodecs.VAR_INT.encode(buf, msg.wolfId());
                        ByteBufCodecs.BOOL.encode(buf, msg.rottenMeatAndBones());
                        ByteBufCodecs.BOOL.encode(buf, msg.rawMeat());
                        ByteBufCodecs.BOOL.encode(buf, msg.rawFish());
                        ByteBufCodecs.BOOL.encode(buf, msg.specialFish());
                        ByteBufCodecs.BOOL.encode(buf, msg.cookedMeat());
                        ByteBufCodecs.BOOL.encode(buf, msg.cookedFish());
                    },
                    buf -> new WolfFoodConfigMessageToServer(
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

    public static WolfFoodConfigMessageToServer getFromWolf(SophisticatedWolf wolf) {
        var wolfFood = wolf.getWolfFood();
        return new WolfFoodConfigMessageToServer(wolf.getId(),
                wolfFood.rottenMeatAndBones(), wolfFood.rawMeat(), wolfFood.rawFish(),
                wolfFood.specialFish(), wolfFood.cookedMeat(), wolfFood.cookedFish());

    }

    public static void handle(WolfFoodConfigMessageToServer msg, IPayloadContext context) {
        context.enqueueWork(() -> {
            ServerPlayer player = (ServerPlayer) context.player();
            var level = player.level();

            var entity = level.getEntity(msg.wolfId());
            if (entity instanceof SophisticatedWolf wolf) {
                wolf.updateFood(
                        msg.rottenMeatAndBones(),
                        msg.rawMeat(),
                        msg.rawFish(),
                        msg.specialFish(),
                        msg.cookedMeat(),
                        msg.cookedFish()
                );
            }
        });
    }

}
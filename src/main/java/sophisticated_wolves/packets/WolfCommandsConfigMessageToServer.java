package sophisticated_wolves.packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import sophisticated_wolves.entity.SophisticatedWolf;

import java.util.UUID;
import java.util.function.Supplier;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class WolfCommandsConfigMessageToServer {

    private final UUID wolfId;
    private final boolean followOwner;
    private final boolean guardZone;

    public WolfCommandsConfigMessageToServer(SophisticatedWolf wolf) {
        this.wolfId = wolf.getUUID();
        var commands = wolf.getWolfCommands();
        this.followOwner = commands.followOwner();
        this.guardZone = commands.guardZone();
    }

    public WolfCommandsConfigMessageToServer(FriendlyByteBuf buf) {
        this.wolfId = UUID.fromString(buf.readUtf());
        this.followOwner = buf.readBoolean();
        this.guardZone = buf.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(wolfId.toString());
        buf.writeBoolean(this.followOwner);
        buf.writeBoolean(this.guardZone);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        var context = supplier.get();
        context.enqueueWork(() -> {
            var player = context.getSender();
            var level = player.getLevel();

            if (level != null) {
                var animal = level.getEntity(wolfId);
                if (animal != null && animal instanceof SophisticatedWolf wolf) {
                    wolf.updateCommands(this.followOwner, this.guardZone);
                }
            }
        });
        return true;
    }

}
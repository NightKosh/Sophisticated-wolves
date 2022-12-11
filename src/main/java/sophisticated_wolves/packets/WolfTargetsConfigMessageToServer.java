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
public class WolfTargetsConfigMessageToServer {

    private final UUID wolfId;
    private final boolean attackSkeletons;
    private final boolean attackZombies;
    private final boolean attackSpiders;
    private final boolean attackSlimes;
    private final boolean attackNether;
    private final boolean attackRaider;

    public WolfTargetsConfigMessageToServer(SophisticatedWolf wolf) {
        this.wolfId = wolf.getUUID();
        var wolfTargets = wolf.getWolfTargets();
        this.attackSkeletons = wolfTargets.attackSkeletons();
        this.attackZombies = wolfTargets.attackZombies();
        this.attackSpiders = wolfTargets.attackSpiders();
        this.attackSlimes = wolfTargets.attackSlimes();
        this.attackNether = wolfTargets.attackNether();
        this.attackRaider = wolfTargets.attackRaider();
    }

    public WolfTargetsConfigMessageToServer(FriendlyByteBuf buf) {
        this.wolfId = UUID.fromString(buf.readUtf());
        this.attackSkeletons = buf.readBoolean();
        this.attackZombies = buf.readBoolean();
        this.attackSpiders = buf.readBoolean();
        this.attackSlimes = buf.readBoolean();
        this.attackNether = buf.readBoolean();
        this.attackRaider = buf.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(wolfId.toString());
        buf.writeBoolean(this.attackSkeletons);
        buf.writeBoolean(this.attackZombies);
        buf.writeBoolean(this.attackSpiders);
        buf.writeBoolean(this.attackSlimes);
        buf.writeBoolean(this.attackNether);
        buf.writeBoolean(this.attackRaider);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        var context = supplier.get();
        context.enqueueWork(() -> {
            var player = context.getSender();
            var level = player.getLevel();

            if (level != null) {
                var animal = level.getEntity(wolfId);
                if (animal != null && animal instanceof SophisticatedWolf wolf) {
                    wolf.updateTargets(this.attackSkeletons, this.attackZombies, this.attackSpiders,
                            this.attackSlimes, this.attackNether, this.attackRaider);
                }
            }
        });
        return true;
    }

}
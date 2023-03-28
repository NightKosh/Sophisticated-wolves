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
public class WolfFoodConfigMessageToServer {

    private final UUID wolfId;
    private final boolean rottenMeatAndBones;
    private final boolean rawMeat;
    private final boolean rawFish;
    private final boolean specialFish;
    private final boolean cookedMeat;
    private final boolean cookedFish;

    public WolfFoodConfigMessageToServer(SophisticatedWolf wolf) {
        this.wolfId = wolf.getUUID();
        var wolfFood = wolf.getWolfFood();
        this.rottenMeatAndBones = wolfFood.rottenMeatAndBones();
        this.rawMeat = wolfFood.rawMeat();
        this.rawFish = wolfFood.rawFish();
        this.specialFish = wolfFood.specialFish();
        this.cookedMeat = wolfFood.cookedMeat();
        this.cookedFish = wolfFood.cookedFish();
    }

    public WolfFoodConfigMessageToServer(FriendlyByteBuf buf) {
        this.wolfId = UUID.fromString(buf.readUtf());
        this.rottenMeatAndBones = buf.readBoolean();
        this.rawMeat = buf.readBoolean();
        this.rawFish = buf.readBoolean();
        this.specialFish = buf.readBoolean();
        this.cookedMeat = buf.readBoolean();
        this.cookedFish = buf.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(this.wolfId.toString());
        buf.writeBoolean(this.rottenMeatAndBones);
        buf.writeBoolean(this.rawMeat);
        buf.writeBoolean(this.rawFish);
        buf.writeBoolean(this.specialFish);
        buf.writeBoolean(this.cookedMeat);
        buf.writeBoolean(this.cookedFish);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        var context = supplier.get();
        context.enqueueWork(() -> {
            var player = context.getSender();
            var level = player.getLevel();

            if (level != null) {
                var animal = level.getEntity(this.wolfId);
                if (animal != null && animal instanceof SophisticatedWolf wolf) {
                    wolf.updateFood(this.rottenMeatAndBones, this.rawMeat, this.rawFish,
                            this.specialFish, this.cookedMeat, this.cookedFish);
                }
            }
        });
        return true;
    }

}
package sophisticated_wolves.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import sophisticated_wolves.entity.EntitySophisticatedWolf;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class FoodMessageToServer implements IMessage, IMessageHandler<FoodMessageToServer, IMessage> {

    private int playerID;
    private int dimensionID;
    private boolean rottenMeatAndBones;
    private boolean rawMeat;
    private boolean rawFish;
    private boolean specialFish;
    private boolean cookedMeat;
    private boolean cookedFish;

    public FoodMessageToServer() {

    }

    public FoodMessageToServer(EntityTameable entity, boolean rottenMeatAndBones, boolean rawMeat, boolean rawFish,
                               boolean specialFish, boolean cookedMeat, boolean cookedFish) {
        this.playerID = entity.getEntityId();
        this.dimensionID = entity.world.provider.getDimension();
        this.rottenMeatAndBones = rottenMeatAndBones;
        this.rawMeat = rawMeat;
        this.rawFish = rawFish;
        this.specialFish = specialFish;
        this.cookedMeat = cookedMeat;
        this.cookedFish = cookedFish;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.playerID = buf.readInt();
        this.dimensionID = buf.readInt();
        this.rottenMeatAndBones = buf.readBoolean();
        this.rawMeat = buf.readBoolean();
        this.rawFish = buf.readBoolean();
        this.specialFish = buf.readBoolean();
        this.cookedMeat = buf.readBoolean();
        this.cookedFish = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(playerID);
        buf.writeInt(dimensionID);
        buf.writeBoolean(this.rottenMeatAndBones);
        buf.writeBoolean(this.rawMeat);
        buf.writeBoolean(this.rawFish);
        buf.writeBoolean(this.specialFish);
        buf.writeBoolean(this.cookedMeat);
        buf.writeBoolean(this.cookedFish);
    }

    @Override
    public IMessage onMessage(FoodMessageToServer message, MessageContext ctx) {
        if (ctx.side.isServer()) {
            World world = DimensionManager.getWorld(message.dimensionID);
            if (world != null) {
                Entity entity = world.getEntityByID(message.playerID);
                if (entity != null && entity instanceof EntitySophisticatedWolf) {
                    ((EntitySophisticatedWolf) entity).updateFood(message.rottenMeatAndBones, message.rawMeat, message.rawFish, message.specialFish, message.cookedMeat, message.cookedFish);
                }
            }
        }
        return null;
    }
}
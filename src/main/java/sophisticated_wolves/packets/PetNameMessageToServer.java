package sophisticated_wolves.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * GraveStone mod
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class PetNameMessageToServer implements IMessage, IMessageHandler<PetNameMessageToServer, IMessage> {

    private int playerID;
    private int dimensionID;
    private String text;

    public PetNameMessageToServer() {

    }

    public PetNameMessageToServer(EntityTameable entity, String text) {
        this.playerID = entity.getEntityId();
        this.dimensionID = entity.world.provider.getDimension();
        this.text = text;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.playerID = buf.readInt();
        this.dimensionID = buf.readInt();
        this.text = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(playerID);
        buf.writeInt(dimensionID);
        ByteBufUtils.writeUTF8String(buf, text);

    }

    @Override
    public IMessage onMessage(PetNameMessageToServer message, MessageContext ctx) {
        if (ctx.side.isServer()) {
            World world = DimensionManager.getWorld(message.dimensionID);
            if (world != null) {
                Entity entity = world.getEntityByID(message.playerID);
                if (entity != null && entity instanceof EntityTameable) {
                    ((EntityTameable) entity).setCustomNameTag(message.text);
                }
            }
        }
        return null;
    }
}

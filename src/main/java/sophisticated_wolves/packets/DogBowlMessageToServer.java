package sophisticated_wolves.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import sophisticated_wolves.tile_entity.TileEntityDogBowl;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class DogBowlMessageToServer implements IMessage, IMessageHandler<DogBowlMessageToServer, IMessage> {

    private int x;
    private int y;
    private int z;
    private int dimensionID;
    private int amountOfFood;

    public DogBowlMessageToServer() {

    }

    public DogBowlMessageToServer(TileEntityDogBowl te, int amountOfFood) {
        this.x = te.getPos().getX();
        this.y = te.getPos().getY();
        this.z = te.getPos().getZ();
        this.dimensionID = te.getWorld().provider.getDimension();
        this.amountOfFood = amountOfFood;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
        this.dimensionID = buf.readInt();
        this.amountOfFood = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        buf.writeInt(dimensionID);
        buf.writeInt(amountOfFood);

    }

    @Override
    public IMessage onMessage(DogBowlMessageToServer message, MessageContext ctx) {
        if (ctx.side.isServer()) {
            World world = DimensionManager.getWorld(message.dimensionID);
            if (world != null) {
                TileEntityDogBowl te = (TileEntityDogBowl) world.getTileEntity(new BlockPos(message.x, message.y, message.z));
                if (te != null) {
                    te.addFood(message.amountOfFood);
                }
            }
        }
        return null;
    }
}

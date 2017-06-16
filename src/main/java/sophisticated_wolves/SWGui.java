package sophisticated_wolves;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import sophisticated_wolves.gui.GuiDogBowl;
import sophisticated_wolves.gui.container.ContainerDogBowl;
import sophisticated_wolves.tile_entity.TileEntityDogBowl;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class SWGui implements IGuiHandler {

    public static final int DOG_BOWL_ID = 0;

    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
        if (tileEntity instanceof TileEntityDogBowl) {
            return new ContainerDogBowl(player.inventory, (TileEntityDogBowl) tileEntity);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
        if (tileEntity instanceof TileEntityDogBowl) {
            return new GuiDogBowl(player.inventory, (TileEntityDogBowl) tileEntity);
        }
        return null;
    }
}

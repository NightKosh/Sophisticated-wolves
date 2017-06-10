package sophisticated_wolves.item.pet_carrier;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.List;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public abstract class CustomPetCarrier {

    public abstract String getPetId();

    public abstract EntityLiving spawnPet(World world, EntityPlayer player);

    public List<String> getInfo(NBTTagCompound infoNbt) {
        return null;
    }

    public NBTTagCompound getInfo(EntityLivingBase pet) {
        return null;
    }
}

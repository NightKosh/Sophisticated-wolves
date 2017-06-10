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
public abstract class PetCarrier {

    public abstract Class getPetClass();

    public abstract String getPetId();

    public abstract EntityLiving spawnPet(World world, EntityPlayer player);

    public List<String> getInfo(NBTTagCompound infoNbt) {
        return null;
    }

    public NBTTagCompound getInfo(EntityLivingBase pet) {
        return null;
    }

    public abstract List<NBTTagCompound> getDefaultPetCarriers();

    public final NBTTagCompound getDefaultPetCarrier(NBTTagCompound infoNbt, NBTTagCompound entityNbt) {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setString("ClassName", getPetClass().getSimpleName());

        if (infoNbt != null) {
            nbt.setTag("InfoList", infoNbt);
        }

        if (entityNbt != null) {
            nbt.setTag("MobData", entityNbt);
        }

        return nbt;
    }
}

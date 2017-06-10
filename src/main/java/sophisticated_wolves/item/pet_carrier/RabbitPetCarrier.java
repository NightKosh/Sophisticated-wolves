package sophisticated_wolves.item.pet_carrier;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class RabbitPetCarrier extends PetCarrier {

    @Override
    public Class getPetClass() {
        return EntityRabbit.class;
    }

    @Override
    public String getPetId() {
        return "Rabbit";
    }

    @Override
    public EntityLiving spawnPet(World world, EntityPlayer player) {
        return new EntityRabbit(world);
    }

    @Override
    public List<NBTTagCompound> getDefaultPetCarriers() {
        List<NBTTagCompound> list = new ArrayList<>();
        list.add(getDefaultPetCarrier(null, null));

        return list;
    }
}

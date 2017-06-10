package sophisticated_wolves.item.pet_carrier;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import sophisticated_wolves.api.pet_carrier.PetCarrier;

import java.util.ArrayList;
import java.util.List;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class ChickenPetCarrier extends PetCarrier {

    @Override
    public Class getPetClass() {
        return EntityChicken.class;
    }

    @Override
    public String getPetId() {
        return "Chicken";
    }

    @Override
    public EntityLiving spawnPet(World world, EntityPlayer player) {
        return new EntityChicken(world);
    }

    @Override
    public List<NBTTagCompound> getDefaultPetCarriers() {
        List<NBTTagCompound> list = new ArrayList<>();
        list.add(getDefaultPetCarrier(null, null));

        return list;
    }
}

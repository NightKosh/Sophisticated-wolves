package sophisticated_wolves.item.pet_carrier;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import sophisticated_wolves.api.pet_carrier.PetCarrier;
import sophisticated_wolves.compatibility.Compatibility;
import sophisticated_wolves.compatibility.CompatibilityWolfArmor;

import java.util.ArrayList;
import java.util.List;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class WolfPetCarrier extends PetCarrier {

    @Override
    public Class getPetClass() {
        return EntityWolf.class;
    }

    @Override
    public String getPetId() {
        return "Wolf";
    }

    @Override
    public NBTTagCompound getAdditionalData(EntityLivingBase pet) {
        NBTTagCompound nbt = new NBTTagCompound();
        if (Compatibility.IS_WOLF_ARMOR_INSTALLED) {
            CompatibilityWolfArmor.storeWolfItems((EntityWolf) pet, nbt);
        }
        return nbt;
    }

    @Override
    public void setAdditionalData(EntityLiving pet, NBTTagCompound nbt) {
        CompatibilityWolfArmor.getWolfItems((EntityWolf) pet, nbt);
    }

    @Override
    public EntityLiving spawnPet(World world, EntityPlayer player) {
        return new EntityWolf(world);
    }

    @Override
    public List<NBTTagCompound> getDefaultPetCarriers() {
        List<NBTTagCompound> list = new ArrayList<>();
        list.add(getDefaultPetCarrier(null, null));

        return list;
    }
}

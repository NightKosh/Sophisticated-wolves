package sophisticated_wolves.item.pet_carrier;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import sophisticated_wolves.SophisticatedWolvesMod;
import sophisticated_wolves.api.pet_carrier.PetCarrier;

import java.util.ArrayList;
import java.util.List;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class RabbitPetCarrier extends PetCarrier {
    public static final int[] RABBITS_SPECIES = {0, 1, 2, 3, 4, 5, 99};

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
    public List<String> getInfo(NBTTagCompound infoNbt) {
        if (infoNbt.hasKey("RabbitType")) {
            List<String> list = new ArrayList<>(1);
            StringBuilder str = new StringBuilder(SophisticatedWolvesMod.proxy.getLocalizedString("carrier.rabbit_type"))
                    .append(" - ").append(SophisticatedWolvesMod.proxy.getLocalizedString("rabbit_type." + infoNbt.getInteger("RabbitType"))
                            .toString().toLowerCase());
            list.add(str.toString());
            return list;
        }
        return null;
    }

    @Override
    public NBTTagCompound getInfo(EntityLivingBase pet) {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("RabbitType", ((EntityRabbit) pet).getRabbitType());

        return nbt;
    }

    @Override
    public List<NBTTagCompound> getDefaultPetCarriers() {
        List<NBTTagCompound> list = new ArrayList<>();
        for (int species : RABBITS_SPECIES) {
            NBTTagCompound infoNbt = new NBTTagCompound();
            infoNbt.setInteger("RabbitType", species);

            NBTTagCompound entityNbt = new NBTTagCompound();
            entityNbt.setInteger("RabbitType", species);

            list.add(getDefaultPetCarrier(infoNbt, entityNbt));
        }

        return list;
    }
}

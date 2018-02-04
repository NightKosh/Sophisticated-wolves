package sophisticated_wolves.item.pet_carrier;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityParrot;
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
public class ParrotPetCarrier extends PetCarrier {
    public static final int[] PARROTS_SPECIES = {0, 1, 2, 3, 4};
    //0 = red, 1 = blue, 2 = green, 3 = cyan, 4 = silver.

    @Override
    public Class getPetClass() {
        return EntityParrot.class;
    }

    @Override
    public String getPetId() {
        return "Parrot";
    }

    @Override
    public EntityLiving spawnPet(World world, EntityPlayer player) {
        return new EntityParrot(world);
    }

    @Override
    public List<String> getInfo(NBTTagCompound infoNbt) {
        if (infoNbt.hasKey("Variant")) {
            List<String> list = new ArrayList<>(1);
            StringBuilder str = new StringBuilder(SophisticatedWolvesMod.proxy.getLocalizedString("carrier.parrot_type"))
                    .append(" - ").append(SophisticatedWolvesMod.proxy.getLocalizedString("parrot_type." + infoNbt.getInteger("Variant"))
                            .toString().toLowerCase());
            list.add(str.toString());
            return list;
        }
        return null;
    }

    @Override
    public NBTTagCompound getInfo(EntityLivingBase pet) {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("Variant", ((EntityParrot) pet).getVariant());

        return nbt;
    }

    @Override
    public List<NBTTagCompound> getDefaultPetCarriers() {
        List<NBTTagCompound> list = new ArrayList<>();
        for (int species : PARROTS_SPECIES) {
            NBTTagCompound infoNbt = new NBTTagCompound();
            infoNbt.setInteger("Variant", species);

            NBTTagCompound entityNbt = new NBTTagCompound();
            entityNbt.setInteger("Variant", species);

            list.add(getDefaultPetCarrier(infoNbt, entityNbt));
        }

        return list;
    }
}

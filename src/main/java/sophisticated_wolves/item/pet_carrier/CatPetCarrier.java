package sophisticated_wolves.item.pet_carrier;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import sophisticated_wolves.SophisticatedWolvesMod;

import java.util.ArrayList;
import java.util.List;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class CatPetCarrier extends CustomPetCarrier {

    public static enum EnumCatType {
        OCELOT,
        BLACK,
        RED,
        SIAMESE;

        public static EnumCatType getSpeciesByNum(int num) {
            if (num >= 0 && num < EnumCatType.values().length) {
                return EnumCatType.values()[num];
            } else {
                return OCELOT;
            }
        }
    }

    @Override
    public String getPetId() {
        return "Ozelot";
    }

    @Override
    public EntityLiving spawnPet(World world, EntityPlayer player) {
        return new EntityOcelot(world);
    }

    @Override
    public List<String> getInfo(NBTTagCompound infoNbt) {
        if (infoNbt.hasKey("CatType")) {
            List<String> list = new ArrayList(1);
            StringBuilder str = new StringBuilder(SophisticatedWolvesMod.proxy.getLocalizedString("cat_type"))
                    .append(" - ").append(SophisticatedWolvesMod.proxy.getLocalizedString("cat_type." + EnumCatType.getSpeciesByNum(infoNbt.getInteger("CatType"))
                    .toString().toLowerCase()));
            list.add(str.toString());
            return list;
        }
        return null;
    }

    @Override
    public NBTTagCompound getInfo(EntityLivingBase pet) {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("CatType", ((EntityOcelot) pet).getTameSkin());

        return nbt;
    }
}

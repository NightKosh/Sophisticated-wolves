package sophisticated_wolves.compatibility;

import com.attributestudios.wolfarmor.api.IWolfArmorCapability;
import com.attributestudios.wolfarmor.common.capabilities.CapabilityWolfArmor;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import sophisticated_wolves.entity.EntitySophisticatedWolf;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class CompatibilityWolfArmor {

    public static NBTTagCompound storeWolfItems(EntityWolf wolf, NBTTagCompound nbt) {
        if (wolf.hasCapability(CapabilityWolfArmor.WOLF_ARMOR_CAPABILITY, null)) {
            IWolfArmorCapability wolfCapability = wolf.getCapability(CapabilityWolfArmor.WOLF_ARMOR_CAPABILITY, null);
            if (wolfCapability != null) {
                nbt.setBoolean("HasChest", wolfCapability.getHasChest());
                if (wolfCapability.getHasChest()) {
//                    wolfCapability.setInventory(wolfCapability.getInventory());//TODO setInventory!!!!
//                    NBTTagCompound itemsNBT = new NBTTagCompound();
//                    wolfCapability.getArmorItemStack().writeToNBT(itemsNBT);
//                    nbt.setTag("Items", itemsNBT);
                    wolfCapability.dropInventoryContents();
                }
                if (wolfCapability.getHasArmor()) {
                    NBTTagCompound armorNBT = new NBTTagCompound();
                    wolfCapability.getArmorItemStack().writeToNBT(armorNBT);
                    nbt.setTag("Armor", armorNBT);
                }
            }
        }
        return nbt;
    }

    public static void getWolfItems(EntityWolf wolf, NBTTagCompound nbt) {
        if (wolf.hasCapability(CapabilityWolfArmor.WOLF_ARMOR_CAPABILITY, null)) {
            IWolfArmorCapability wolfCapability = wolf.getCapability(CapabilityWolfArmor.WOLF_ARMOR_CAPABILITY, null);
            if (nbt.getBoolean("HasChest")) {
                wolfCapability.setHasChest(true);
//                wolfCapability.setInventory(wolfCapability.getInventory());//TODO setInventory!!!!
            }
            if (nbt.hasKey("Armor")) {
                wolfCapability.equipArmor(new ItemStack(nbt.getCompoundTag(("Armor"))));
            }
        }

    }

    public static void copyWolfItems(EntityWolf wolf, EntitySophisticatedWolf sWolf) {
        //TODO CapabilityWolfArmor.WOLF_ARMOR_CAPABILITY !!!!!!!!
        if (wolf.hasCapability(CapabilityWolfArmor.WOLF_ARMOR_CAPABILITY, null) && sWolf.hasCapability(CapabilityWolfArmor.WOLF_ARMOR_CAPABILITY, null)) {
            IWolfArmorCapability wolfCapability = wolf.getCapability(CapabilityWolfArmor.WOLF_ARMOR_CAPABILITY, null);
            IWolfArmorCapability sWolfCapability = sWolf.getCapability(CapabilityWolfArmor.WOLF_ARMOR_CAPABILITY, null);
            if (wolfCapability.getHasChest()) {
                sWolfCapability.setHasChest(true);
//                sWolfCapability.setInventory(wolfCapability.getInventory());//TODO setInventory!!!!
                wolfCapability.dropInventoryContents();
            }
            if (wolfCapability.getHasArmor()) {
                sWolfCapability.equipArmor(wolfCapability.getArmorItemStack());
            }
        }
    }
}

package sophisticated_wolves.compatibility;

import com.attributestudios.wolfarmor.api.IWolfArmorCapability;
import com.attributestudios.wolfarmor.api.util.Capabilities;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import sophisticated_wolves.entity.EntitySophisticatedWolf;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class CompatibilityWolfArmor {

    public static NBTTagCompound storeWolfItems(EntityWolf wolf, NBTTagCompound nbt) {
        if (wolf.hasCapability(Capabilities.CAPABILITY_WOLF_ARMOR, null)) {
            IWolfArmorCapability wolfCapability = wolf.getCapability(Capabilities.CAPABILITY_WOLF_ARMOR, null);
            if (wolfCapability != null) {
                nbt.setBoolean("HasChest", wolfCapability.getHasChest());
                if (wolfCapability.getHasChest()) {
                    NBTTagList itemsNBT = new NBTTagList();
                    InventoryBasic inventory = wolfCapability.getInventory();
                    for (int slot = 0; slot < inventory.getSizeInventory(); slot++) {
                        NBTTagCompound itemNbt = new NBTTagCompound();
                        inventory.getStackInSlot(slot).writeToNBT(itemNbt);
                        itemsNBT.appendTag(itemNbt);

                        wolfCapability.setInventoryItem(slot, ItemStack.EMPTY);
                    }

                    nbt.setTag("Items", itemsNBT);
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
        if (wolf.hasCapability(Capabilities.CAPABILITY_WOLF_ARMOR, null)) {
            IWolfArmorCapability wolfCapability = wolf.getCapability(Capabilities.CAPABILITY_WOLF_ARMOR, null);
            if (nbt.getBoolean("HasChest") && nbt.hasKey("Items")) {
                wolfCapability.setHasChest(true);
                NBTTagList ntbItemsList = nbt.getTagList("Items", 10);
                for (int i = 0; i < ntbItemsList.tagCount(); i++) {
                    wolfCapability.setInventoryItem(i, new ItemStack(ntbItemsList.getCompoundTagAt(i)));
                }
            }
            if (nbt.hasKey("Armor")) {
                wolfCapability.equipArmor(new ItemStack(nbt.getCompoundTag(("Armor"))));
            }
        }

    }

    public static void copyWolfItems(EntityWolf wolf, EntitySophisticatedWolf sWolf) {
        if (wolf.hasCapability(Capabilities.CAPABILITY_WOLF_ARMOR, null) && sWolf.hasCapability(Capabilities.CAPABILITY_WOLF_ARMOR, null)) {
            IWolfArmorCapability wolfCapability = wolf.getCapability(Capabilities.CAPABILITY_WOLF_ARMOR, null);
            IWolfArmorCapability sWolfCapability = sWolf.getCapability(Capabilities.CAPABILITY_WOLF_ARMOR, null);
            if (wolfCapability.getHasChest()) {
                sWolfCapability.setHasChest(true);
                InventoryBasic inventory = wolfCapability.getInventory();
                for (int slot = 0; slot < inventory.getSizeInventory(); slot++) {
                    sWolfCapability.setInventoryItem(slot, inventory.getStackInSlot(slot).copy());
                }
            }
            if (wolfCapability.getHasArmor()) {
                sWolfCapability.equipArmor(wolfCapability.getArmorItemStack());
            }
        }
    }
}

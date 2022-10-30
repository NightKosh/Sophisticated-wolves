package sophisticated_wolves.compatibility;

//import com.attributestudios.wolfarmor.api.IWolfArmorCapability;
//import com.attributestudios.wolfarmor.api.util.Capabilities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.item.ItemStack;
import sophisticated_wolves.entity.SophisticatedWolf;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class CompatibilityWolfArmor {
//TODO
//    public static CompoundTag storeWolfItems(Wolf wolf, CompoundTag nbt) {
//        IWolfArmorCapability wolfCapability = wolf.getCapability(Capabilities.CAPABILITY_WOLF_ARMOR, null);
//        if (wolfCapability != null) {
//            nbt.setBoolean("HasChest", wolfCapability.getHasChest());
//            if (wolfCapability.getHasChest()) {
//                var itemsNBT = new ListTag();
//                InventoryBasic inventory = wolfCapability.getInventory();
//                for (int slot = 0; slot < inventory.getSizeInventory(); slot++) {
//                    CompoundTag itemNbt = new CompoundTag();
//                    inventory.getStackInSlot(slot)
//                            .writeToNBT(itemNbt);
//                    itemsNBT.appendTag(itemNbt);
//
//                    wolfCapability.setInventoryItem(slot, ItemStack.EMPTY);
//                }
//
//                nbt.put("Items", itemsNBT);
//            }
//            if (wolfCapability.getHasArmor()) {
//                CompoundTag armorNBT = new CompoundTag();
//                wolfCapability.getArmorItemStack().writeToNBT(armorNBT);
//                nbt.put("Armor", armorNBT);
//            }
//        }
//        return nbt;
//    }
//
//    public static void getWolfItems(Wolf wolf, CompoundTag nbt) {
//        IWolfArmorCapability wolfCapability = wolf.getCapability(Capabilities.CAPABILITY_WOLF_ARMOR, null);
//        if (nbt.getBoolean("HasChest") && nbt.contains("Items")) {
//            wolfCapability.setHasChest(true);
//            var ntbItemsList = nbt.getList("Items", 10);
//            for (int i = 0; i < ntbItemsList.size(); i++) {
//                wolfCapability.setInventoryItem(i, new ItemStack(ntbItemsList.getCompoundTagAt(i)));
//            }
//        }
//        if (nbt.contains("Armor")) {
//            wolfCapability.equipArmor(new ItemStack(nbt.getCompoundTag(("Armor"))));
//        }
//    }
//
//    public static void copyWolfItems(Wolf wolf, SophisticatedWolf sWolf) {
//        IWolfArmorCapability wolfCapability = wolf.getCapability(Capabilities.CAPABILITY_WOLF_ARMOR, null);
//        IWolfArmorCapability sWolfCapability = sWolf.getCapability(Capabilities.CAPABILITY_WOLF_ARMOR, null);
//        if (wolfCapability.getHasChest()) {
//            sWolfCapability.setHasChest(true);
//            InventoryBasic inventory = wolfCapability.getInventory();
//            for (int slot = 0; slot < inventory.getSizeInventory(); slot++) {
//                sWolfCapability.setInventoryItem(slot, inventory.getStackInSlot(slot).copy());
//            }
//        }
//        if (wolfCapability.getHasArmor()) {
//            sWolfCapability.equipArmor(wolfCapability.getArmorItemStack());
//        }
//    }

}

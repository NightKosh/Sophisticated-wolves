package sophisticated_wolves.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import sophisticated_wolves.SWConfiguration;
import sophisticated_wolves.SWTabs;
import sophisticated_wolves.SophisticatedWolvesMod;
import sophisticated_wolves.entity.EntitySophisticatedWolf;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class ItemDogTag extends Item {

    public ItemDogTag() {
        super();
        setUnlocalizedName("dogtag");
        setCreativeTab(SWTabs.tab);
    }

    /**
     * Returns true if the item can be used on the given entity, e.g. shears on sheep.
     */
    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase entity, EnumHand hand) {
        if (SWConfiguration.nameTagForAnyPets) {
            if (entity instanceof EntityTameable) {
                return setName((EntityTameable) entity, stack, player);
            }
        } else if (entity instanceof EntitySophisticatedWolf) {
            return setName((EntityTameable) entity, stack, player);
        }
        return super.itemInteractionForEntity(stack, player, entity, hand);
    }

    private static boolean setName(EntityTameable pet, ItemStack stack, EntityPlayer player) {
        if (pet.isTamed() && pet.getOwnerId().equals(player.getUniqueID())) {

            if (player.world.isRemote) {
                SophisticatedWolvesMod.proxy.openPetGui(pet);
            } else {
                stack.shrink(1);
            }
            return true;
        }
        return false;
    }
}

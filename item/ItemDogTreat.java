package sophisticated_wolves.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import sophisticated_wolves.Resources;
import sophisticated_wolves.api.ISophisticatedWolf;
import sophisticated_wolves.entity.SophisticatedWolf;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class ItemDogTreat extends Item {

    public ItemDogTreat() {
        super();
        setUnlocalizedName("dogtreat");
        setTextureName(Resources.DOG_TREAT);
        setCreativeTab(CreativeTabs.tabMisc);
    }

    /**
     * Returns true if the item can be used on the given entity, e.g. shears on sheep.
     */
    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase entity) {
        if (!player.worldObj.isRemote && entity instanceof EntityWolf && !(entity instanceof ISophisticatedWolf)) {
            EntityWolf wolf = (EntityWolf) entity;
            if (wolf.isTamed()) {
                SophisticatedWolf SWolf = new SophisticatedWolf(player.worldObj);

                SWolf.copyLocationAndAnglesFrom(wolf);
                SWolf.setCustomNameTag(wolf.getCustomNameTag());
                SWolf.setCollarColor(wolf.getCollarColor());
                SWolf.setTamed(true);
                SWolf.setOwner(wolf.getOwnerName());
                SWolf.setHealth(wolf.getHealth());

                wolf.setDead();

                player.worldObj.spawnEntityInWorld(SWolf);
                player.worldObj.playAuxSFXAtEntity((EntityPlayer) null, 1016, (int) SWolf.posX, (int) SWolf.posY, (int) SWolf.posZ, 0);
                --stack.stackSize;
                return true;
            }
            return false;
        } else {
            return super.itemInteractionForEntity(stack, player, entity);
        }
    }
}

package sophisticated_wolves.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.world.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import sophisticated_wolves.api.ISophisticatedWolf;
import sophisticated_wolves.entity.EntitySophisticatedWolf;

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
        setCreativeTab(CreativeTabs.MISC);
    }

    /**
     * Returns true if the item can be used on the given entity, e.g. shears on sheep.
     */
    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase entity, EnumHand hand) {
        if (!player.worldObj.isRemote && entity instanceof EntityWolf && !(entity instanceof ISophisticatedWolf)) {
            EntityWolf wolf = (EntityWolf) entity;
            if (wolf.isTamed()) {
                EntitySophisticatedWolf SWolf = new EntitySophisticatedWolf(player.worldObj);

                SWolf.copyLocationAndAnglesFrom(wolf);
                SWolf.setCustomNameTag(wolf.getCustomNameTag());
                SWolf.setCollarColor(wolf.getCollarColor());
                SWolf.setTamed(true);
                SWolf.setOwnerId(wolf.getOwnerId());
                SWolf.setCustomNameTag(wolf.getCustomNameTag());
                SWolf.setHealth(wolf.getHealth());

                wolf.setDead();

                player.worldObj.spawnEntityInWorld(SWolf);
                player.worldObj.playSound((player), new BlockPos(SWolf.posX, SWolf.posY, SWolf.posZ), null, null, 1016, 0);
                --stack.stackSize;
                return true;
            }
            return false;
        } else {
            return super.itemInteractionForEntity(stack, player, entity, hand);
        }
    }
}

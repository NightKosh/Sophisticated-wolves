package sophisticated_wolves.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import sophisticated_wolves.SWTabs;
import sophisticated_wolves.api.ISophisticatedWolf;
import sophisticated_wolves.api.ModInfo;
import sophisticated_wolves.compatibility.Compatibility;
import sophisticated_wolves.compatibility.CompatibilityWolfArmor;
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
        this.setRegistryName(ModInfo.ID, "SWDogTreat");
        this.setUnlocalizedName("dogtreat");
        this.setCreativeTab(SWTabs.tab);
    }

    /**
     * Returns true if the item can be used on the given entity, e.g. shears on sheep.
     */
    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase entity, EnumHand hand) {
        if (!player.world.isRemote && entity instanceof EntityWolf && !(entity instanceof ISophisticatedWolf)) {
            EntityWolf wolf = (EntityWolf) entity;
            if (wolf.isTamed()) {
                EntitySophisticatedWolf sWolf = new EntitySophisticatedWolf(player.world);

                sWolf.copyLocationAndAnglesFrom(wolf);
                sWolf.setCustomNameTag(wolf.getCustomNameTag());
                sWolf.setCollarColor(wolf.getCollarColor());
                sWolf.setTamed(true);
                sWolf.setOwnerId(wolf.getOwnerId());
                sWolf.setCustomNameTag(wolf.getCustomNameTag());
                sWolf.setHealth(wolf.getHealth());

                if (Compatibility.IS_WOLF_ARMOR_INSTALLED) {
                    CompatibilityWolfArmor.copyWolfItems(wolf, sWolf);
                }

                wolf.setDead();

                player.world.spawnEntity(sWolf);
                player.world.playSound((player), new BlockPos(sWolf.posX, sWolf.posY, sWolf.posZ), null, null, 1016, 0);
                stack.shrink(1);
                return true;
            }
            return false;
        } else {
            return super.itemInteractionForEntity(stack, player, entity, hand);
        }
    }
}

package sophisticated_wolves.item;

import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import sophisticated_wolves.SWSound;
import sophisticated_wolves.SWTabs;
import sophisticated_wolves.api.ModInfo;
import sophisticated_wolves.entity.ai.EntityAINewFollowOwner;

import java.util.List;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class ItemWhistle extends Item {

    public ItemWhistle() {
        super();
        this.setRegistryName(ModInfo.ID, "SWWhistle");
        this.setUnlocalizedName("whistle");
        this.setCreativeTab(SWTabs.tab);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        if (world.isRemote) {
            world.playSound(player, player.getPosition(), player.isSneaking() ? SWSound.WHISTLE_LONG : SWSound.WHISTLE_SHORT, SoundCategory.PLAYERS, 1, 1);
        } else {
            List<EntityWolf> wolvesList = world.getEntitiesWithinAABB(EntityWolf.class,
                    new AxisAlignedBB(player.posX - 35, player.posY - 35, player.posZ - 35,
                            player.posX + 35, player.posY + 35, player.posZ + 35));

            if (!wolvesList.isEmpty()) {
                int xPos = MathHelper.floor(player.posX);
                int zPos = MathHelper.floor(player.posZ);
                int yPos = MathHelper.floor(player.getEntityBoundingBox().minY);

                for (EntityWolf wolf : wolvesList) {
                    if (wolf.isTamed() && wolf.isOwner(player) && (!wolf.isSitting() || player.isSneaking())) {
                        for (int i = 0; i < 50; i++) {
                            int xRand = player.world.rand.nextInt(5) + xPos - 2;
                            int zRand = player.world.rand.nextInt(5) + zPos - 2;
                            if (EntityAINewFollowOwner.canTeleport(world, xRand, yPos, zRand)) {
                                wolf.setSitting(false);
                                wolf.getAISit().setSitting(false);
                                wolf.setLocationAndAngles(xRand + 0.5, yPos, zRand + 0.5, wolf.rotationYaw, wolf.rotationPitch);
                                wolf.getNavigator().clearPath();
                                wolf.setAttackTarget(null);
                                break;
                            }
                        }
                    }
                }
            }
        }

        return new ActionResult(EnumActionResult.SUCCESS, player.getHeldItem(hand));
    }
}

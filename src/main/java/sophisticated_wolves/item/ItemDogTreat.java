package sophisticated_wolves.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import sophisticated_wolves.core.SWTabs;
import sophisticated_wolves.api.ISophisticatedWolf;
import sophisticated_wolves.compatibility.Compatibility;
import sophisticated_wolves.entity.SophisticatedWolf;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class ItemDogTreat extends Item {

    public ItemDogTreat() {
        super(new Item.Properties()
                .tab(SWTabs.TAB));
    }

    /**
     * Returns true if the item can be used on the given entity, e.g. shears on sheep.
     */
    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity entity, InteractionHand hand) {
        if (!player.getLevel().isClientSide() &&
                entity instanceof Wolf wolf &&
                !(entity instanceof ISophisticatedWolf)) {
            if (wolf.isTame()) {
                //TODO
                SophisticatedWolf sWolf = null;//new SophisticatedWolf(player.getLevel());

                sWolf.copyPosition(wolf);
                sWolf.setCustomName(wolf.getCustomName());
                sWolf.setCollarColor(wolf.getCollarColor());
                sWolf.setTame(true);
                sWolf.setOwnerUUID(wolf.getOwnerUUID());
                sWolf.setHealth(wolf.getHealth());
                sWolf.setWolfSpeciesByBiome();

                if (Compatibility.IS_WOLF_ARMOR_INSTALLED) {
                    //TODO
//                    CompatibilityWolfArmor.copyWolfItems(wolf, sWolf);
                }

                //TODO
//                wolf.setDead();

                //TODO
//                player.getLevel().spawnEntity(sWolf);
                player.getLevel().playSound(player, sWolf, null, null, 1016, 0);
                stack.shrink(1);
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.FAIL;
        } else {
            return super.interactLivingEntity(stack, player, entity, hand);
        }
    }

}

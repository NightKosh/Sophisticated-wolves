package sophisticated_wolves.item;

import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import sophisticated_wolves.core.SWSound;
import sophisticated_wolves.core.SWTabs;
import sophisticated_wolves.entity.ai.SWFollowOwnerGoal;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class ItemWhistle extends Item {

    public ItemWhistle() {
        super(new Item.Properties()
                .tab(SWTabs.TAB)
                .stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (level.isClientSide()) {
            level.playSound(player, player,
                    player.isShiftKeyDown() ? SWSound.getWhistleLong() : SWSound.getWhistleShort(),
                    SoundSource.PLAYERS, 1, 1);
        } else {
            var wolves = level.getEntitiesOfClass(Wolf.class,
                    new AABB(player.getX() - 35, player.getY() - 35, player.getZ() - 35,
                            player.getX() + 35, player.getY() + 35, player.getZ() + 35));

            if (!wolves.isEmpty()) {
                int xPos = Mth.floor(player.getX());
                int zPos = Mth.floor(player.getZ());
                int yPos = Mth.floor(player.getBoundingBox().minY);

                for (Wolf wolf : wolves) {
                    if (wolf.isTame() &&
                            wolf.isOwnedBy(player) &&
                            (!wolf.isOrderedToSit() || player.isShiftKeyDown())) {
                        for (int i = 0; i < 50; i++) {
                            int xRand = player.getLevel().getRandom().nextInt(5) + xPos - 2;
                            int zRand = player.getLevel().getRandom().nextInt(5) + zPos - 2;
                            if (SWFollowOwnerGoal.canTeleport(level, xRand, yPos, zRand)) {
                                wolf.setInSittingPose(false);
                                wolf.moveTo(xRand + 0.5, yPos, zRand + 0.5, wolf.getYRot(), wolf.getXRot());
                                wolf.getNavigation().stop();
                                wolf.setTarget(null);
                                break;
                            }
                        }
                    }
                }
            }
        }

        return InteractionResultHolder.success(player.getItemInHand(hand));
    }

}

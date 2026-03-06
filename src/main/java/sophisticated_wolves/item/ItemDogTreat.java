package sophisticated_wolves.item;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.animal.wolf.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.gameevent.GameEvent;
import sophisticated_wolves.api.AEntitySophisticatedWolf;
import sophisticated_wolves.core.SWAdvancements;
import sophisticated_wolves.core.SWConfiguration;
import sophisticated_wolves.core.SWEntities;
import sophisticated_wolves.core.SWItems;

import static sophisticated_wolves.SophisticatedWolvesMod.LOGGER;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class ItemDogTreat extends Item {

    public ItemDogTreat() {
        super(new Item.Properties()
                .stacksTo(64)
                .setId(SWItems.DOG_TREAT_RK));
    }

    public static void useItemOnWolf(Entity e, Player player, ItemStack stack) {
        if (!player.level().isClientSide() &&
                e instanceof Wolf wolf &&
                !(e instanceof AEntitySophisticatedWolf)) {
            if (wolf.isTame()) {
                if (SWConfiguration.DEBUG_MODE.get()) {
                    LOGGER.info("Dog Treat was used on vanilla wolf");
                }

                var level = player.level();
                var sWolf = SWEntities.getSophisticatedWolfType().spawn(
                        (ServerLevel) level, stack, player,
                        wolf.blockPosition(), EntitySpawnReason.SPAWN_ITEM_USE,
                        true, true);
                if (sWolf != null) {
                    sWolf.copyPosition(wolf);
                    sWolf.setCustomName(wolf.getCustomName());
                    sWolf.setCollarColor(wolf.getCollarColor());
                    sWolf.setTame(true, true);
                    sWolf.setOwner(wolf.getOwner());
                    sWolf.setHealth(wolf.getHealth());
                    sWolf.setVariant(wolf.getVariant());

                    wolf.remove(Entity.RemovalReason.DISCARDED);

                    level.gameEvent(player, GameEvent.ENTITY_PLACE, wolf.blockPosition());

                    stack.consume(1, player);

                    SWAdvancements.giveAdvancement(player, level, SWAdvancements.SOPHISTICATED_TASTE);
                }
            }
        }
    }

}

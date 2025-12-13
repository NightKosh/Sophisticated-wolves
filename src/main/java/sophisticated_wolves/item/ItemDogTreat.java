package sophisticated_wolves.item;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.gameevent.GameEvent;
import sophisticated_wolves.api.ISophisticatedWolf;
import sophisticated_wolves.compatibility.Compatibility;
import sophisticated_wolves.core.SWConfiguration;
import sophisticated_wolves.core.SWEntities;

import static sophisticated_wolves.SophisticatedWolvesMod.LOGGER;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class ItemDogTreat extends Item {

    public ItemDogTreat() {
        super(new Item.Properties().stacksTo(64));
    }

    public static void useItemOnWolf(Entity e, Player player, ItemStack stack) {
        if (!player.level().isClientSide() &&
                e instanceof Wolf wolf &&
                !(e instanceof ISophisticatedWolf)) {
            if (wolf.isTame()) {
                if (SWConfiguration.DEBUG_MODE.get()) {
                    LOGGER.info("Dog Treat was used on vanilla wolf");
                }

                var level = player.level();
                var sWolf = SWEntities.getSophisticatedWolfType().spawn(
                        (ServerLevel) level, stack, player,
                        wolf.blockPosition(), MobSpawnType.SPAWN_EGG,
                        true, true);
                if (sWolf != null) {
                    sWolf.copyPosition(wolf);
                    sWolf.setCustomName(wolf.getCustomName());
                    sWolf.setCollarColor(wolf.getCollarColor());
                    sWolf.setTame(true, true);
                    sWolf.setOwnerUUID(wolf.getOwnerUUID());
                    sWolf.setHealth(wolf.getHealth());
                    sWolf.setWolfSpeciesByBiome(level);

                    if (Compatibility.IS_WOLF_ARMOR_INSTALLED) {
                        //TODO remove?
                        //CompatibilityWolfArmor.copyWolfItems(wolf, sWolf);
                    }

                    wolf.remove(Entity.RemovalReason.DISCARDED);

                    level.gameEvent(player, GameEvent.ENTITY_PLACE, wolf.blockPosition());

                    stack.shrink(1);
                }
            }
        }
    }

}

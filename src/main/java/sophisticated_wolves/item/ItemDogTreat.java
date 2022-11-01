package sophisticated_wolves.item;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.gameevent.GameEvent;
import sophisticated_wolves.core.SWEntities;
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
                var level = player.getLevel();
                var sWolf = (SophisticatedWolf) SWEntities.getSophisticatedWolfType().spawn(
                        (ServerLevel) level, stack, player,
                        entity.blockPosition(), MobSpawnType.SPAWN_EGG,
                        true, true);
                if (sWolf != null) {
                    sWolf.copyPosition(wolf);
                    sWolf.setCustomName(wolf.getCustomName());
                    sWolf.setCollarColor(wolf.getCollarColor());
                    sWolf.setTame(true);
                    sWolf.setOwnerUUID(wolf.getOwnerUUID());
                    sWolf.setHealth(wolf.getHealth());
                    sWolf.setWolfSpeciesByBiome();

                    if (Compatibility.IS_WOLF_ARMOR_INSTALLED) {
                        //TODO
                        //CompatibilityWolfArmor.copyWolfItems(wolf, sWolf);
                    }

                    wolf.remove(Entity.RemovalReason.DISCARDED);

                    //TODO
                    //player.getLevel().spawnEntity(sWolf);

                    level.gameEvent(player, GameEvent.ENTITY_PLACE, entity.blockPosition());

                    stack.shrink(1);
                }
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.PASS;
        }
        return InteractionResult.PASS;
    }

}

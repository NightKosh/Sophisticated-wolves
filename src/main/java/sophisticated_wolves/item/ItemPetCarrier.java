package sophisticated_wolves.item;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import sophisticated_wolves.entity.SophisticatedWolf;
import sophisticated_wolves.item.pet_carrier.PetCarrierHelper;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class ItemPetCarrier extends Item {

    public ItemPetCarrier() {
        super(new Item.Properties().stacksTo(1));
    }

    @Nonnull
    @Override
    public InteractionResult interactLivingEntity(
            @Nonnull ItemStack stack, @Nonnull Player player,
            LivingEntity entity, @Nonnull InteractionHand hand) {
        if (!entity.getLevel().isClientSide() &&
                !(stack.hasTag() && stack.getTag().contains("ClassName")) &&
                entity instanceof SophisticatedWolf wolf &&
                wolf.isTame() &&
                wolf.getOwnerUUID() != null && wolf.getOwnerUUID().equals(player.getUUID())) {
            return getPetInfo(stack, player, entity, hand);
        }
        return super.interactLivingEntity(stack, player, entity, hand);
    }

    public static void useItemOnOtherPets(Entity e, Player player, ItemStack stack, InteractionHand hand) {
        if (!e.getLevel().isClientSide() &&
                stack != null &&
                !(stack.hasTag() && stack.getTag().contains("ClassName")) &&
                e instanceof LivingEntity entity) {
            if (entity instanceof TamableAnimal pet && !(pet instanceof SophisticatedWolf)) {
                if (pet.isTame() && pet.getOwnerUUID() != null && pet.getOwnerUUID().equals(player.getUUID())) {
                    getPetInfo(stack, player, entity, hand);
                }
            } else if (PetCarrierHelper.hasPetCarrier(entity.getClass())) {
                getPetInfo(stack, player, entity, hand);
            }
        }
    }

    private static InteractionResult getPetInfo(
            ItemStack stack, Player player, LivingEntity entity, InteractionHand hand) {
        var entityTag = new CompoundTag();
        entity.save(entityTag);

        var tag = new CompoundTag();
        tag.putString("ClassName", entity.getClass().getSimpleName());

        var petCarrier = PetCarrierHelper.getPetCarrier(entity.getClass().getSimpleName());
        if (petCarrier != null) {
            var infoTag = petCarrier.getInfo(entity);
            if (infoTag != null) {
                tag.put("InfoList", infoTag);
            }

            var additionalNbt = petCarrier.getAdditionalData(entity);
            if (additionalNbt != null) {
                tag.put("AdditionalData", additionalNbt);
            }
        }

        if (entity.hasCustomName()) {
            tag.putString("CustomName", entity.getCustomName().getString());
        }

        tag.put("MobData", entityTag);

        stack.setTag(tag);
        player.setItemInHand(hand, stack);
        entity.remove(Entity.RemovalReason.DISCARDED);

        return InteractionResult.SUCCESS;
    }

    @Nonnull
    @Override
    public InteractionResult useOn(UseOnContext context) {
        var level = context.getLevel();

        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        } else {
            var stack = context.getItemInHand();
            if (stack.hasTag()) {
                var tag = stack.getTag();
                if (tag.contains("ClassName")) {
                    var petCarrier = PetCarrierHelper.getPetCarrier(tag.getString("ClassName"));
                    if (petCarrier != null) {
                        var pos = context.getClickedPos();
                        var player = context.getPlayer();
                        var entityType = petCarrier.getEntityType();
                        var entity = entityType.spawn((ServerLevel) level, stack, player, pos, MobSpawnType.SPAWN_EGG,
                                true, context.getClickedFace() == Direction.UP);
                        if (entity != null) {
                            if (entity instanceof Mob mob) {
                                mob.readAdditionalSaveData(tag.getCompound("MobData"));
                            }
                            if (tag.contains("AdditionalData")) {
                                petCarrier.setAdditionalData(entity, tag.getCompound("AdditionalData"));
                            }
                            if (tag.contains("CustomName")) {
                                entity.setCustomName(Component.literal(tag.getString("CustomName")));
                            }
                            petCarrier.doAtSpawn(entity, player);

                            if (!player.isCreative()) {
                                stack.setTag(new CompoundTag());
                            }
                            level.gameEvent(player, GameEvent.ENTITY_PLACE, pos);

                            return InteractionResult.SUCCESS;
                        }
                    }
                }
                return InteractionResult.FAIL;
            }

            return InteractionResult.SUCCESS;
        }
    }

    @Override
    public void appendHoverText(
            @Nonnull ItemStack stack, Level level,
            @Nonnull List<Component> tooltips, @Nonnull TooltipFlag flag) {
        if (stack.hasTag()) {
            var tag = stack.getTag();
            if (tag != null && tag.contains("ClassName")) {
                var petCarrier = PetCarrierHelper.getPetCarrier(tag.getString("ClassName"));
                if (petCarrier != null) {
                    tooltips.add(Component.translatable("sophisticated_wolves.carrier.pet")
                            .append(" - ")
                            .append(Component.translatable(petCarrier.getPetNameLocalizationKey())));

                    if (tag.contains("CustomName")) {
                        tooltips.add(Component.translatable("sophisticated_wolves.carrier.name")
                                .append(" - ")
                                .append(Component.literal(tag.getString("CustomName"))));
                    }

                    if (tag.contains("InfoList")) {
                        var tooltipList = petCarrier.getInfo(tag.getCompound("InfoList"));
                        if (tooltipList != null) {
                            tooltips.addAll(tooltipList);
                        }
                    }
                }
            }
        }

        super.appendHoverText(stack, level, tooltips, flag);
    }

}

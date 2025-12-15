package sophisticated_wolves.item;

import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
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
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.gameevent.GameEvent;
import sophisticated_wolves.core.SWItems;
import sophisticated_wolves.entity.SophisticatedWolf;
import sophisticated_wolves.item.pet_carrier.PetCarrierHelper;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class ItemPetCarrier extends Item {

    private static final String CLASS_NAME = "ClassName";
    private static final String CUSTOM_NAME = "CustomName";
    private static final String INFO_LIST = "InfoList";
    private static final String MOB_DATA = "MobData";
    private static final String ADDITIONAL_DATA = "AdditionalData";

    public ItemPetCarrier() {
        super(new Item.Properties()
                .stacksTo(1)
                .setId(SWItems.PET_CARRIER_RK));
    }

    @Nonnull
    @Override
    public InteractionResult interactLivingEntity(
            @Nonnull ItemStack stack, @Nonnull Player player,
            LivingEntity entity, @Nonnull InteractionHand hand) {
        if (!entity.level().isClientSide() &&
                !(stack.get(DataComponents.CUSTOM_DATA) != null && stack.get(DataComponents.CUSTOM_DATA).contains(CLASS_NAME)) &&
                entity instanceof SophisticatedWolf wolf &&
                wolf.isTame() &&
                wolf.getOwner() != null && wolf.getOwner().equals(player)) {
            return getPetInfo(stack, player, entity, hand);
        }
        return super.interactLivingEntity(stack, player, entity, hand);
    }

    public static void useItemOnOtherPets(Entity e, Player player, ItemStack stack, InteractionHand hand) {
        if (!e.level().isClientSide() &&
                stack != null &&
                !(stack.get(DataComponents.CUSTOM_DATA) != null && stack.get(DataComponents.CUSTOM_DATA).contains(CLASS_NAME)) &&
                e instanceof LivingEntity entity) {
            if (entity instanceof TamableAnimal pet && !(pet instanceof SophisticatedWolf)) {
                if (pet.isTame() && pet.getOwner() != null && pet.getOwner().equals(player)) {
                    getPetInfo(stack, player, entity, hand);
                }
            } else if (PetCarrierHelper.hasPetCarrier(entity.getClass())) {
                getPetInfo(stack, player, entity, hand);
            }
        }
    }

    private static InteractionResult getPetInfo(
            ItemStack stack, Player player, LivingEntity entity, InteractionHand hand) {

        var tag = new CompoundTag();
        tag.putString(CLASS_NAME, entity.getClass().getSimpleName());

        var petCarrier = PetCarrierHelper.getPetCarrier(entity.getClass().getSimpleName());
        if (petCarrier != null) {
            var infoTag = petCarrier.getInfo(entity);
            if (infoTag != null) {
                tag.put(INFO_LIST, infoTag);
            }

            var additionalNbt = petCarrier.getAdditionalData(entity);
            if (additionalNbt != null) {
                tag.put(ADDITIONAL_DATA, additionalNbt);
            }
        }

        if (entity.hasCustomName()) {
            tag.putString(CUSTOM_NAME, entity.getCustomName().getString());
        }

        var petData = petCarrier.addPetData(entity);
        if (petData != null) {
            tag.put(MOB_DATA, petData);
        }

        stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
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
            var data = stack.get(DataComponents.CUSTOM_DATA);
            if (data != null) {
                var tag = data.copyTag();
                if (tag != null && tag.contains(CLASS_NAME)) {
                    var petCarrier = PetCarrierHelper.getPetCarrier(tag.getString(CLASS_NAME).get());
                    if (petCarrier != null) {
                        var pos = context.getClickedPos();
                        var player = context.getPlayer();
                        var entityType = petCarrier.getEntityType();
                        var entity = entityType.spawn((ServerLevel) level, stack, player, pos, EntitySpawnReason.SPAWN_ITEM_USE,
                                true, context.getClickedFace() == Direction.UP);
                        if (entity != null) {
                            if (entity instanceof Mob mob && tag.contains(MOB_DATA)) {
                                petCarrier.readPetData(mob, tag.getCompound(MOB_DATA).get());
                            }
                            if (tag.contains(ADDITIONAL_DATA)) {
                                petCarrier.setAdditionalData(entity, tag.getCompound(ADDITIONAL_DATA).get());
                            }
                            if (tag.contains(CUSTOM_NAME)) {
                                entity.setCustomName(Component.literal(tag.getString(CUSTOM_NAME).get()));
                            }
                            petCarrier.doAtSpawn(entity, player);

                            if (!player.isCreative()) {
                                stack.set(DataComponents.CUSTOM_DATA, CustomData.of(new CompoundTag()));
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
            @Nonnull ItemStack stack, @Nonnull Item.TooltipContext context,
            @Nonnull TooltipDisplay tooltipDisplay, @Nonnull Consumer<Component> consumer,
            @Nonnull TooltipFlag flag) {
        var data = stack.get(DataComponents.CUSTOM_DATA);
        if (data != null) {
            var tag = data.copyTag();
            if (tag != null && tag.contains(CLASS_NAME)) {
                var petCarrier = PetCarrierHelper.getPetCarrier(tag.getString(CLASS_NAME).get());
                if (petCarrier != null) {
                    consumer.accept(Component.translatable("sophisticated_wolves.carrier.pet")
                            .append(" - ")
                            .append(Component.translatable(petCarrier.getPetNameLocalizationKey())));

                    if (tag.contains(CUSTOM_NAME)) {
                        consumer.accept(Component.translatable("sophisticated_wolves.carrier.name")
                                .append(" - ")
                                .append(Component.literal(tag.getString(CUSTOM_NAME).get())));
                    }

                    if (tag.contains(INFO_LIST)) {
                        var tooltipList = petCarrier.getInfo(tag.getCompound(INFO_LIST).get());
                        if (tooltipList != null) {
                            tooltipList.stream().forEach(consumer::accept);
                        }
                    }
                }
            }
        }

        super.appendHoverText(stack, context, tooltipDisplay, consumer, flag);
    }

}

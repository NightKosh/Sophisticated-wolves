package sophisticated_wolves.item.pet_carrier;

import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import sophisticated_wolves.api.pet_carrier.PetCarrier;
import sophisticated_wolves.core.SWTabs;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class ItemPetCarrier extends Item {

    public ItemPetCarrier() {
        super(new Item.Properties()
                .tab(SWTabs.TAB)
                .stacksTo(1));
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity entity, InteractionHand hand) {
        if (!entity.getLevel().isClientSide() &&
                stack != null &&
                !(stack.hasTag() && stack.getTag().contains("ClassName"))) {
            if (entity instanceof TamableAnimal pet) {
                if (pet.isTame() && pet.getOwnerUUID() != null && pet.getOwnerUUID().equals(player.getUUID())) {
                    return getPetInfo(stack, player, entity, hand);
                }
            } else if (PetCarrierHelper.PETS_MAP.containsKey(entity.getClass().getSimpleName())) {
                return getPetInfo(stack, player, entity, hand);
            }
        }
        return super.interactLivingEntity(stack, player, entity, hand);
    }

    private static InteractionResult getPetInfo(ItemStack stack, Player player, LivingEntity entity, InteractionHand hand) {
        var entityTag = new CompoundTag();
        entity.save(entityTag);

        var tag = new CompoundTag();
        tag.putString("ClassName", entity.getClass().getSimpleName());

        var petCarrier = PetCarrierHelper.PETS_MAP.get(entity.getClass().getSimpleName());
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

    @Override
    public InteractionResult useOn(UseOnContext context) {
        var level = context.getLevel();

        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        } else {
            var stack = context.getItemInHand();
            if (stack != null && stack.hasTag()) {
                var tag = stack.getTag();
                if (tag.contains("ClassName")) {
                    var petCarrier = PetCarrierHelper.PETS_MAP.get(tag.getString("ClassName"));
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

                            if (entity instanceof TamableAnimal animal) {
                                animal.setOwnerUUID(player.getUUID());
                                animal.setTame(true);
                            }

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
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltips, TooltipFlag flag) {
        if (stack != null && stack.hasTag()) {
            var tag = stack.getTag();
            if (tag != null && tag.contains("ClassName")) {
                var petCarrier = PetCarrierHelper.PETS_MAP.get(tag.getString("ClassName"));
                if (petCarrier != null) {
                    tooltips.add(Component.translatable("carrier.pet_type")
                            .append(" - ")
                            .append(Component.translatable(petCarrier.getPetNameLocalizationKey())));

                    if (tag.contains("CustomName")) {
                        tooltips.add(Component.translatable("carrier.pet_name")
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

    @Override
    public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> items) {
        if (this.allowedIn(tab)) {
            items.add(new ItemStack(this, 1));

            for (Map.Entry<String, PetCarrier> entry : PetCarrierHelper.PETS_MAP.entrySet()) {
                var petCarrier = entry.getValue();
                if (petCarrier != null) {
                    for (CompoundTag tag : petCarrier.getDefaultPetCarriers()) {
                        var stack = new ItemStack(this, 1);
                        stack.setTag(tag);
                        items.add(stack);
                    }
                }
            }
        }
    }

}

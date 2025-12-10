package sophisticated_wolves.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import sophisticated_wolves.core.SWConfiguration;
import sophisticated_wolves.entity.SophisticatedWolf;
import sophisticated_wolves.gui.screen.DogTagScreen;

import javax.annotation.Nonnull;

import static com.mojang.text2speech.Narrator.LOGGER;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class ItemDogTag extends Item {

    public ItemDogTag() {
        super(new Item.Properties().stacksTo(64));
    }

    @Nonnull
    @Override
    public InteractionResult interactLivingEntity(
            @Nonnull ItemStack stack, @Nonnull Player player,
            @Nonnull LivingEntity entity, @Nonnull InteractionHand hand) {
        if (entity instanceof SophisticatedWolf wolf) {
            return setName(wolf, stack, player);
        }
        return super.interactLivingEntity(stack, player, entity, hand);
    }

    public static void useItemOnOtherPets(Entity entity, Player player, ItemStack stack) {
        if (SWConfiguration.NAME_TAG_FOR_ANY_PETS.get() && !(entity instanceof SophisticatedWolf)) {
            if (entity instanceof TamableAnimal animal) {
                setName(animal, stack, player);
            }
        }
    }

    private static InteractionResult setName(TamableAnimal pet, ItemStack stack, Player player) {
        if (pet.isTame() && pet.getOwnerUUID() != null && pet.getOwnerUUID().equals(player.getUUID())) {
            if (SWConfiguration.DEBUG_MODE.get()) {
                LOGGER.info("Dog Tag was used on pet ");
            }
            if (player.getLevel().isClientSide()) {
                DogTagScreen.open(pet);
            } else {
                stack.shrink(1);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }

}

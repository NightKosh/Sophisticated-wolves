package sophisticated_wolves.gui.menu;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import sophisticated_wolves.block_entity.BlockEntityDogBowl;
import sophisticated_wolves.core.SWMenu;
import sophisticated_wolves.gui.slot.SlotDogBowl;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class DogBowlContainerMenu extends AbstractContainerMenu {

    public static final int COLUMNS = 9;
    public static final int INVENTORY_ROWS = 3;
    private static final int VANILLA_SLOT_COUNT = COLUMNS * (INVENTORY_ROWS + 1);
    private static final int FOOD_SLOT_INDEX = VANILLA_SLOT_COUNT;
    public static final int SLOT_WIDTH = 18;

    private final Level level;
    private final ContainerData data;

    protected BlockEntityDogBowl dogBowl;

    public DogBowlContainerMenu(int containerId, Inventory inv, FriendlyByteBuf extraData) {
        this(containerId, inv, inv.player.level.getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(2));
    }

    public DogBowlContainerMenu(int containerId, Inventory inv, BlockEntity blockEntity, ContainerData data) {
        super(SWMenu.DOG_BOWL.get(), containerId);
        this.dogBowl = ((BlockEntityDogBowl) blockEntity);
        this.level = inv.player.level;
        this.data = data;

        this.addSlot(new SlotDogBowl(this.dogBowl, 0, 8, 35));

        for (int row = 0; row < INVENTORY_ROWS; ++row) {
            for (int column = 0; column < COLUMNS; ++column) {
                this.addSlot(new Slot(inv, column + row * COLUMNS + COLUMNS,
                        8 + column * SLOT_WIDTH, 84 + row * SLOT_WIDTH));
            }
        }

        for (int column = 0; column < COLUMNS; ++column) {
            this.addSlot(new Slot(inv, column, 8 + column * SLOT_WIDTH, 142));
        }

        addDataSlots(data);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        var slot = slots.get(index);
        if (slot != null && slot.hasItem()) {
            var stack = slot.getItem();
            var stackCopy = stack.copy();

            if (index < VANILLA_SLOT_COUNT) {
                if (!moveItemStackTo(stack, FOOD_SLOT_INDEX, FOOD_SLOT_INDEX + 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index < FOOD_SLOT_INDEX + 1) {
                if (!moveItemStackTo(stack, 0, VANILLA_SLOT_COUNT, false)) {
                    return ItemStack.EMPTY;
                }
            } else {
                return ItemStack.EMPTY;
            }

            if (stack.getCount() == 0) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            slot.onTake(player, stack);
            return stackCopy;
        }
        return ItemStack.EMPTY;
    }

    public BlockEntityDogBowl getDogBowl() {
        return dogBowl;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

}

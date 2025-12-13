package sophisticated_wolves.core;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import sophisticated_wolves.api.ModInfo;
import sophisticated_wolves.block.BlockDogBowl;
import sophisticated_wolves.block.BlockKennel;
import sophisticated_wolves.item.item_block.ItemBlockDogBowl;
import sophisticated_wolves.item.item_block.ItemBlockKennel;

import java.util.function.Supplier;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class SWBlocks {

    public static final DeferredRegister<Block> BLOCKS_REGISTER =
            DeferredRegister.create(Registries.BLOCK, ModInfo.ID);

    private static final DeferredHolder<Block, Block> DOG_BOWL = registerBlock("dog_bowl",
            () -> new BlockDogBowl(BlockBehaviour.Properties.of()
                    .sound(SoundType.STONE)
                    .noCollission()
                    .strength(0.7F)),
            ItemBlockDogBowl::new);
    private static final DeferredHolder<Block, Block> KENNEL = registerBlock("kennel", BlockKennel::new, ItemBlockKennel::new);

    private static <T extends Block> DeferredHolder<Block, T> registerBlock(
            String name, Supplier<T> block, Supplier<Item> itemBlock) {
        SWItems.ITEMS_REGISTER.register(name, itemBlock);
        return BLOCKS_REGISTER.register(name, block);
    }

    public static void register(IEventBus eventBus) {
        BLOCKS_REGISTER.register(eventBus);
    }

    public static Block getDogBowl() {
        return DOG_BOWL.get();
    }

    public static Block getKennel() {
        return KENNEL.get();
    }

}

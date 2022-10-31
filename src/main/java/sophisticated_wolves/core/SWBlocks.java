package sophisticated_wolves.core;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import sophisticated_wolves.api.ModInfo;
import sophisticated_wolves.block.BlockDogBowl;
import sophisticated_wolves.item.item_block.ItemBlockDogBowl;

import java.util.function.Supplier;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class SWBlocks {

    public static final DeferredRegister<Block> BLOCKS_REGISTER =
            DeferredRegister.create(ForgeRegistries.BLOCKS, ModInfo.ID);

    private static final RegistryObject<Block> DOG_BOWL = registerBlock("dog_bowl", BlockDogBowl::new, ItemBlockDogBowl::new);

//    public static final Block KENNEL = new BlockKennel();

    private static <T extends Block> RegistryObject<T> registerBlock(
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

}

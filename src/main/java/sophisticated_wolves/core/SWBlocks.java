package sophisticated_wolves.core;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import sophisticated_wolves.api.ModInfo;
import sophisticated_wolves.block.BlockDogBowl;

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

    private static final RegistryObject<Block> DOG_BOWL = registerBlock("dog_bowl", BlockDogBowl::new);

//    public static final Block KENNEL = new BlockKennel();

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        var toReturn = BLOCKS_REGISTER.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(
            String name, RegistryObject<T> block) {
        return SWItems.ITEMS_REGISTER.register(name, () -> new BlockItem(block.get(),
                new Item.Properties().tab(SWTabs.tab)));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS_REGISTER.register(eventBus);
    }

}

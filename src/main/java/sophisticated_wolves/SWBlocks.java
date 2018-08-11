package sophisticated_wolves;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import sophisticated_wolves.api.ModInfo;
import sophisticated_wolves.block.BlockDogBowl;
import sophisticated_wolves.block.BlockKennel;
import sophisticated_wolves.item.item_block.ItemBlockDogBowl;
import sophisticated_wolves.item.item_block.ItemBlockKennel;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

@GameRegistry.ObjectHolder(ModInfo.ID)
public class SWBlocks {

    public static final Block DOG_BOWL = new BlockDogBowl();
    public static final Block KENNEL = new BlockKennel();
    public static final ItemBlock DOG_BOWL_IB = new ItemBlockDogBowl(SWBlocks.DOG_BOWL);
    public static final ItemBlock KENNEL_IB = new ItemBlockKennel(SWBlocks.KENNEL);

    @Mod.EventBusSubscriber(modid = ModInfo.ID)
    public static class RegistrationHandler {

        @SubscribeEvent
        public static void registerBlocks(final RegistryEvent.Register<Block> event) {
            final IForgeRegistry<Block> registry = event.getRegistry();
            registry.registerAll(DOG_BOWL, KENNEL);
        }

        @SubscribeEvent
        public static void registerItemBlocks(final RegistryEvent.Register<Item> event) {
            final IForgeRegistry<Item> registry = event.getRegistry();

            registry.registerAll(DOG_BOWL_IB, KENNEL_IB);
        }
    }
}

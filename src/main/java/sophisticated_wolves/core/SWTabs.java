package sophisticated_wolves.core;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import sophisticated_wolves.api.ModInfo;
import sophisticated_wolves.block.BlockDogBowl;
import sophisticated_wolves.item.pet_carrier.PetCarrierHelper;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
@Mod.EventBusSubscriber(modid = ModInfo.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SWTabs {

    public static CreativeModeTab SW_TAB;

    @SubscribeEvent
    public static void registerTabs(CreativeModeTabEvent.Register event) {
        SW_TAB = event.registerCreativeModeTab(
                ResourceLocation.fromNamespaceAndPath(ModInfo.ID, "sophisticated_wolves"),
                builder -> builder
                        .icon(() -> new ItemStack(SWItems.getDogTreat()))
                        .title(Component.translatable("itemGroup." + ModInfo.ID))
                        .build()
        );
    }

    @SubscribeEvent
    public static void buildContents(CreativeModeTabEvent.BuildContents event) {
        if (event.getTab() == SW_TAB) {
            event.accept(SWItems.getDogTag());
            event.accept(SWItems.getDogTreat());
            event.accept(SWItems.getWhistle());

            event.accept(BlockDogBowl.getItemsForTab(BlockDogBowl.EnumDogBowl.EMPTY));
            event.accept(BlockDogBowl.getItemsForTab(BlockDogBowl.EnumDogBowl.FULL));
            event.accept(new ItemStack(SWBlocks.getKennel()));

            for (var entry : PetCarrierHelper.getPetCarriers()) {
                var petCarrier = entry.getValue();
                if (petCarrier != null) {
                    for (var tag : petCarrier.getDefaultPetCarriers()) {
                        var stack = new ItemStack(SWItems.getPetCarrier(), 1);
                        stack.setTag(tag);
                        event.accept(stack);
                    }
                }
            }

            event.accept(SWItems.getDogEgg());//TODO ???
        }
    }

}

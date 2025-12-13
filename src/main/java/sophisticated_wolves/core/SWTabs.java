package sophisticated_wolves.core;

import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import sophisticated_wolves.api.ModInfo;
import sophisticated_wolves.block.BlockDogBowl;
import sophisticated_wolves.item.pet_carrier.PetCarrierHelper;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class SWTabs {

    public static final DeferredRegister<CreativeModeTab> SW_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ModInfo.ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> ADVANCED_FISHING_TAB =
            SW_TAB.register("sophisticated_wolves", () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(SWItems.getDogTreat()))
                    .title(Component.translatable("itemGroup." + ModInfo.ID))
                    .displayItems((parameters, output) -> {

                        output.accept(SWItems.getDogTag());
                        output.accept(SWItems.getDogTreat());
                        output.accept(SWItems.getWhistle());

                        output.accept(BlockDogBowl.getItemsForTab(BlockDogBowl.EnumDogBowl.EMPTY));
                        output.accept(BlockDogBowl.getItemsForTab(BlockDogBowl.EnumDogBowl.FULL));
                        output.accept(new ItemStack(SWBlocks.getKennel()));

                        output.accept(new ItemStack(SWItems.getPetCarrier()));
                        for (var entry : PetCarrierHelper.getPetCarriers()) {
                            var petCarrier = entry.getValue();
                            if (petCarrier != null) {
                                for (var tag : petCarrier.getDefaultPetCarriers()) {
                                    var stack = new ItemStack(SWItems.getPetCarrier());
                                    stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
                                    output.accept(stack);
                                }
                            }
                        }

                        output.accept(SWItems.getDogEgg());
                    })
                    .build()
            );

    public static void register(IEventBus modEventBus) {
        SW_TAB.register(modEventBus);
    }

}

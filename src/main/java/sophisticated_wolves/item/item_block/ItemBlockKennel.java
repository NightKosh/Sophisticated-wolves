package sophisticated_wolves.item.item_block;

import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import sophisticated_wolves.core.SWBlocks;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class ItemBlockKennel extends BlockItem {

    public ItemBlockKennel() {
        super(SWBlocks.getKennel(), new Item.Properties()
                .stacksTo(64)
                .setId(ResourceKey.create(Registries.ITEM, SWBlocks.KENNEL_RK.identifier())));
    }

    @Override
    public void appendHoverText(
            @Nonnull ItemStack stack, @Nonnull Item.TooltipContext context,
            @Nonnull TooltipDisplay tooltipDisplay, @Nonnull Consumer<Component> consumer,
            @Nonnull TooltipFlag flag) {
        var data = stack.get(DataComponents.CUSTOM_DATA);
        if (data != null) {
            var tag = data.copyTag();
            if (tag.contains("FoodAmount")) {
                consumer.accept(Component.translatable("item.sophisticated_wolves.dog_bowl.amount_of_food")
                        .append(Component.literal(String.valueOf(tag.getInt("FoodAmount").get()))));
            }
        }
    }

}

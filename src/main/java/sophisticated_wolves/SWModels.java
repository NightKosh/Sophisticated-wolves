package sophisticated_wolves;

import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import sophisticated_wolves.api.EnumWolfSpecies;
import sophisticated_wolves.api.ModInfo;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

@GameRegistry.ObjectHolder(ModInfo.ID)
public class SWModels {

    @Mod.EventBusSubscriber(modid = ModInfo.ID)
    public static class RegistrationHandler {

        @SubscribeEvent
        public static void registerModels(final ModelRegistryEvent event) {
            ModelLoader.setCustomModelResourceLocation(SWItems.DOG_TAG, 0, Resources.DOG_TAG_MODEL);
            ModelLoader.setCustomModelResourceLocation(SWItems.DOG_TREAT, 0, Resources.DOG_TREAT_MODEL);
            ModelLoader.setCustomModelResourceLocation(SWItems.WHISTLE, 0, Resources.WHISTLE_MODEL);
            ModelLoader.setCustomModelResourceLocation(SWItems.PET_CARRIER, 0, Resources.PET_CARRIER_MODEL);

            for (EnumWolfSpecies wolfSpecies : EnumWolfSpecies.values()) {
                ModelLoader.setCustomModelResourceLocation(SWItems.DOG_EGG, wolfSpecies.ordinal(), Resources.SPAWN_EGG_MODEL);
            }

            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SWBlocks.DOG_BOWL), 0, Resources.DOG_BOWL);
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SWBlocks.DOG_BOWL), 1, Resources.DOG_BOWL1);
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SWBlocks.DOG_BOWL), 2, Resources.DOG_BOWL2);
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SWBlocks.DOG_BOWL), 3, Resources.DOG_BOWL3);
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SWBlocks.DOG_BOWL), 4, Resources.DOG_BOWL4);
            ModelBakery.registerItemVariants(SWBlocks.DOG_BOWL_IB, Resources.DOG_BOWL, Resources.DOG_BOWL1, Resources.DOG_BOWL2, Resources.DOG_BOWL3, Resources.DOG_BOWL4);

            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SWBlocks.KENNEL), 0, Resources.KENNEL);
        }
    }
}

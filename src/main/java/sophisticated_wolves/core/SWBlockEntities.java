package sophisticated_wolves.core;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import sophisticated_wolves.api.ModInfo;
import sophisticated_wolves.block_entity.BlockEntityDogBowl;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class SWBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES_REGISTER =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, ModInfo.ID);

    public static final RegistryObject<BlockEntityType<BlockEntityDogBowl>> DOG_BOWL =
            BLOCK_ENTITIES_REGISTER.register(
                    "dog_bowl_entity",
                    () -> BlockEntityType.Builder.of(BlockEntityDogBowl::new, SWBlocks.getDogBowl())
                            .build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES_REGISTER.register(eventBus);
    }

}

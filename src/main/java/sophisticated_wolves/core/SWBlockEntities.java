package sophisticated_wolves.core;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
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
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, ModInfo.ID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BlockEntityDogBowl>> DOG_BOWL =
            BLOCK_ENTITIES_REGISTER.register(
                    "dog_bowl_entity",
                    () -> BlockEntityType.Builder.of(BlockEntityDogBowl::new, SWBlocks.getDogBowl())
                            .build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES_REGISTER.register(eventBus);
    }

}

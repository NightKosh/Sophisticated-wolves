package sophisticated_wolves.core;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import sophisticated_wolves.api.ModInfo;
import sophisticated_wolves.block_entity.BlockEntityDogBowl;
import sophisticated_wolves.block_entity.BlockEntityKennel;

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
                    () -> new BlockEntityType<>(
                            BlockEntityDogBowl::new,
                            SWBlocks.getDogBowl()
                    ));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BlockEntityKennel>> KENNEL =
            BLOCK_ENTITIES_REGISTER.register(
                    "kennel_entity",
                    () -> new BlockEntityType<>(
                            BlockEntityKennel::new,
                            SWBlocks.getKennel()
                    ));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES_REGISTER.register(eventBus);
    }

}

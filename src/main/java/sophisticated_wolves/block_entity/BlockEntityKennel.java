package sophisticated_wolves.block_entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import sophisticated_wolves.core.SWBlockEntities;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class BlockEntityKennel extends BlockEntityDogBowl {

    public BlockEntityKennel(BlockPos blockPos, BlockState state) {
        super(SWBlockEntities.KENNEL.get(), blockPos, state);
    }

}

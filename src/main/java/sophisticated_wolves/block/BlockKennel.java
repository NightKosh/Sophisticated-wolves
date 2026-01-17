package sophisticated_wolves.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import sophisticated_wolves.block_entity.BlockEntityKennel;
import sophisticated_wolves.core.SWBlocks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class BlockKennel extends BlockDogBowl {

    private static final VoxelShape SHAPE = Block.box(1, 0, 1, 15, 14, 15);

    public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
    public static final MapCodec<BlockKennel> CODEC = simpleCodec(BlockKennel::new);

    public BlockKennel(Properties properties) {
        super(properties);

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(FOOD_LEVEL, EnumDogBowl.EMPTY.ordinal()));
    }

    public BlockKennel() {
        this(BlockBehaviour.Properties.of()
                .setId(SWBlocks.KENNEL_RK)
                .sound(SoundType.WOOD)
                .noCollision()
                .strength(0.7F));
    }

    public static ItemStack getItemsForTab(EnumDogBowl bowl) {
        var tag = new CompoundTag();
        tag.putInt("FoodLevel", bowl.ordinal());
        tag.putInt("FoodAmount", bowl.getAmountOfFood());

        var stack = new ItemStack(SWBlocks.getKennel());
        stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
        return stack;
    }

    @Nonnull
    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@Nonnull BlockPos blockPos, @Nonnull BlockState state) {
        return new BlockEntityKennel(blockPos, state);
    }

    @Nonnull
    @Override
    public VoxelShape getShape(
            @Nonnull BlockState blockState, @Nonnull BlockGetter blockGetter,
            @Nonnull BlockPos blockPos, @Nonnull CollisionContext collisionContext) {
        return SHAPE;
    }

    @Nonnull
    @Override
    public VoxelShape getCollisionShape(
            @Nonnull BlockState blockState, @Nonnull BlockGetter blockGetter,
            @Nonnull BlockPos blockPos, @Nonnull CollisionContext collisionContext) {
        return SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateBuilder) {
        super.createBlockStateDefinition(stateBuilder);
        stateBuilder.add(FACING);
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

}

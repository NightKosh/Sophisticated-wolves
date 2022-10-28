package sophisticated_wolves.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import sophisticated_wolves.tile_entity.BlockEntityDogBowl;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class BlockDogBowl extends BaseEntityBlock {

    public enum EnumDogBowl {//TODO implements IStringSerializable {
        EMPTY("empty", 0),
        FILLED1("filled_1", 25),
        FILLED2("filled_2", 50),
        FILLED3("filled_3", 75),
        FILLED4("filled_4", 100);

        public static final int BONES_PER_LEVEL = 25;

        private String blockModelName;
        private int amountOfFood;

        private EnumDogBowl(String blockModelName, int amountOfFood) {
            this.blockModelName = blockModelName;
            this.amountOfFood = amountOfFood;
        }

        //        @Override
        public String getName() {
            return blockModelName;
        }

        public static EnumDogBowl getById(int id) {
            if (id < values().length) {
                return values()[id];
            }
            return EMPTY;
        }

        public static EnumDogBowl getTypeByFood(int amountOfFood) {
            if (amountOfFood > EnumDogBowl.FILLED3.getAmountOfFood()) {
                return EnumDogBowl.FILLED4;
            } else if (amountOfFood > EnumDogBowl.FILLED2.getAmountOfFood()) {
                return EnumDogBowl.FILLED3;
            } else if (amountOfFood > EnumDogBowl.FILLED1.getAmountOfFood()) {
                return EnumDogBowl.FILLED2;
            } else if (amountOfFood > EnumDogBowl.EMPTY.getAmountOfFood()) {
                return EnumDogBowl.FILLED1;
            } else {
                return EnumDogBowl.EMPTY;
            }
        }

        public int getAmountOfFood() {
            return amountOfFood;
        }
    }

    public static final IntegerProperty FOOD_LEVEL = IntegerProperty.create("food_level", 0, 4);

    protected static final VoxelShape SHAPE = Block.box(4, 0, 4, 10, 2, 12);

    public BlockDogBowl() {
        super(BlockBehaviour.Properties.of(Material.STONE)
                .sound(SoundType.STONE)
                .noCollission()
                .strength(0.7F));

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FOOD_LEVEL, EnumDogBowl.EMPTY.getAmountOfFood()));
    }

    @Override
    public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> list) {
        for (int foodLevel = 0; foodLevel < EnumDogBowl.values().length; foodLevel++) {
            list.add(new ItemStack(this, 1));//TODO, foodLevel));
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState state) {
        //TODO
        return null;//new BlockEntityDogBowl(blockPos, state);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
                                 InteractionHand hand, BlockHitResult hitResult) {
        var dogBowl = (BlockEntityDogBowl) level.getBlockEntity(pos);
        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        } else if (dogBowl != null && !player.isShiftKeyDown()) {
            //TODO
//            player.openMenu(state.getMenuProvider(level, pos));
//            player.openGui(SophisticatedWolvesMod.instance, SWGui.DOG_BOWL_ID, level, pos.getX(), pos.getY(), pos.getZ());
            return InteractionResult.CONSUME;
        }
        return InteractionResult.FAIL;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);

        var dogBowl = (BlockEntityDogBowl) level.getBlockEntity(pos);
        if (dogBowl != null) {
            //TODO
//            dogBowl.setFoodAmount(((EnumDogBowl) state.getValue(VARIANT)).getAmountOfFood());
        }
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter,
                               BlockPos blockPos, CollisionContext collisionContext) {
        return SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateBuilder) {
        stateBuilder.add(FOOD_LEVEL);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        return this.defaultBlockState().setValue(FOOD_LEVEL, 0);
    }

}

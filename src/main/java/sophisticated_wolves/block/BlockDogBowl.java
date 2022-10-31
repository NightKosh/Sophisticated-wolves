package sophisticated_wolves.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
//TODO
public class BlockDogBowl extends Block {//BaseEntityBlock {

    public static final IntegerProperty FOOD = IntegerProperty.create("food", 0, 100);
    public static final IntegerProperty FOOD_LEVEL = IntegerProperty.create("food_level", 0, 4);
    private static final VoxelShape SHAPE = Block.box(4, 0, 4, 12, 2, 12);

    //TODO remove ??
    public enum EnumDogBowl {
        EMPTY(0),
        FILLED_25_P(25),
        FILLED_50_P(50),
        FILLED_75_P(75),
        FILLED_100_P(100);

        public static final int BONES_PER_LEVEL = 25;

        private int amountOfFood;

        EnumDogBowl(int amountOfFood) {
            this.amountOfFood = amountOfFood;
        }

        public static EnumDogBowl getById(int id) {
            if (id < values().length) {
                return values()[id];
            }
            return EMPTY;
        }

        public static EnumDogBowl getTypeByFood(int amountOfFood) {
            if (amountOfFood > EnumDogBowl.FILLED_75_P.getAmountOfFood()) {
                return EnumDogBowl.FILLED_100_P;
            } else if (amountOfFood > EnumDogBowl.FILLED_50_P.getAmountOfFood()) {
                return EnumDogBowl.FILLED_75_P;
            } else if (amountOfFood > EnumDogBowl.FILLED_25_P.getAmountOfFood()) {
                return EnumDogBowl.FILLED_50_P;
            } else if (amountOfFood > EnumDogBowl.EMPTY.getAmountOfFood()) {
                return EnumDogBowl.FILLED_25_P;
            } else {
                return EnumDogBowl.EMPTY;
            }
        }

        public int getAmountOfFood() {
            return amountOfFood;
        }

    }

    public BlockDogBowl() {
        super(BlockBehaviour.Properties.of(Material.STONE)
                .sound(SoundType.STONE)
                .noCollission()
                .strength(0.7F));

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FOOD, EnumDogBowl.EMPTY.getAmountOfFood())
                .setValue(FOOD_LEVEL, EnumDogBowl.EMPTY.ordinal()));
    }

    @Override
    public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> list) {
        for (var bowl : EnumDogBowl.values()) {
            var tag = new CompoundTag();
            tag.putInt("FoodLevel", bowl.ordinal());
            tag.putInt("FoodAmount", bowl.getAmountOfFood());
            var stack = new ItemStack(this, 1);
            stack.setTag(tag);
            list.add(stack);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter blockGetter,
                                List<Component> tooltips, TooltipFlag flag) {
        if (stack != null && stack.hasTag()) {
            var tag = stack.getTag();
            if (tag != null && tag.contains("FoodAmount")) {
                    tooltips.add(Component.translatable("block.sophisticated_wolves.dog_bowl.amount_of_food")
                            .append(Component.literal(String.valueOf(tag.getInt("FoodAmount")))));
            }
        }
    }

//    @Nullable
//    @Override
//    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState state) {
//        //TODO
//        return null;//new BlockEntityDogBowl(blockPos, state);
//    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
                                 InteractionHand hand, BlockHitResult hitResult) {
//        var dogBowl = (BlockEntityDogBowl) level.getBlockEntity(pos);
        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        } else if (!player.isShiftKeyDown()) {
//        } else if (dogBowl != null && !player.isShiftKeyDown()) {
            //TODO
//            player.openMenu(state.getMenuProvider(level, pos));
//            player.openGui(SophisticatedWolvesMod.instance, SWGui.DOG_BOWL_ID, level, pos.getX(), pos.getY(), pos.getZ());
            return InteractionResult.CONSUME;
        }
        return InteractionResult.FAIL;
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter,
                               BlockPos blockPos, CollisionContext collisionContext) {
        return SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateBuilder) {
        stateBuilder.add(FOOD);
        stateBuilder.add(FOOD_LEVEL);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        var item = context.getItemInHand();
        var state = this.defaultBlockState();
        if (item.hasTag()) {
            var tag = item.getTag();
            if (tag.contains("FoodLevel")) {
                state = state.setValue(FOOD_LEVEL, tag.getInt("FoodLevel"));
            }
            if (tag.contains("FoodAmount")) {
                state = state.setValue(FOOD, tag.getInt("FoodAmount"));
            }
        }
        return state;
    }

}

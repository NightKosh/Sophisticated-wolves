package sophisticated_wolves.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
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
import net.minecraftforge.network.NetworkHooks;
import sophisticated_wolves.block_entity.BlockEntityDogBowl;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class BlockDogBowl extends BaseEntityBlock {

    public static final IntegerProperty FOOD_LEVEL = IntegerProperty.create("food_level", 0, 4);
    private static final VoxelShape SHAPE = Block.box(4, 0, 4, 12, 2, 12);

    public enum EnumDogBowl {
        EMPTY(0),
        FILLED_25_P(25),
        FILLED_50_P(50),
        FILLED_75_P(75),
        FULL(100);

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
                return EnumDogBowl.FULL;
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
                .setValue(FOOD_LEVEL, EnumDogBowl.EMPTY.ordinal()));
    }

    @Override
    public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> list) {
        list.add(getItemsForTab(EnumDogBowl.EMPTY));
        list.add(getItemsForTab(EnumDogBowl.FULL));
    }

    private ItemStack getItemsForTab(EnumDogBowl bowl) {
        var tag = new CompoundTag();
        tag.putInt("FoodLevel", bowl.ordinal());
        tag.putInt("FoodAmount", bowl.getAmountOfFood());
        var stack = new ItemStack(this, 1);
        stack.setTag(tag);
        return stack;
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

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState state) {
        return new BlockEntityDogBowl(blockPos, state);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
                                 InteractionHand hand, BlockHitResult hitResult) {
        var dogBowl = (BlockEntityDogBowl) level.getBlockEntity(pos);
        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        } else if (dogBowl != null && !player.isShiftKeyDown()) {
            NetworkHooks.openScreen(((ServerPlayer) player), dogBowl, pos);
            return InteractionResult.CONSUME;
        }
        return InteractionResult.PASS;
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter,
                               BlockPos blockPos, CollisionContext collisionContext) {
        return SHAPE;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateBuilder) {
        stateBuilder.add(FOOD_LEVEL);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);

        var tileEntity = (BlockEntityDogBowl) level.getBlockEntity(pos);
        if (tileEntity != null) {
            Integer foodAmount = null;

            if (stack != null && stack.hasTag()) {
                var tag = stack.getTag();
                if (tag != null && tag.contains("FoodAmount")) {
                    foodAmount = tag.getInt("FoodAmount");
                }
            }

            tileEntity.setFoodAmount(foodAmount == null ?
                    EnumDogBowl.getById(state.getValue(FOOD_LEVEL)).getAmountOfFood() :
                    foodAmount);
        }
    }

}

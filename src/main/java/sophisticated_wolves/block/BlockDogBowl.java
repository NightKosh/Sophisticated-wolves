package sophisticated_wolves.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import sophisticated_wolves.SWGui;
import sophisticated_wolves.SWTabs;
import sophisticated_wolves.SophisticatedWolvesMod;
import sophisticated_wolves.api.ModInfo;
import sophisticated_wolves.tile_entity.TileEntityDogBowl;

import javax.annotation.Nullable;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class BlockDogBowl extends BlockContainer {

    public enum EnumDogBowl implements IStringSerializable {
        EMPTY("empty", 0),
        FILLED1("filled1", 25),
        FILLED2("filled2", 50),
        FILLED3("filled3", 75),
        FILLED4("filled4", 100);

        public static final int BONES_PER_LEVEL = 25;

        private String blockModelName;
        private int amountOfFood;

        private EnumDogBowl(String blockModelName, int amountOfFood) {
            this.blockModelName = blockModelName;
            this.amountOfFood = amountOfFood;
        }

        @Override
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

    public static final PropertyEnum VARIANT = PropertyEnum.create("variant", EnumDogBowl.class);

    public BlockDogBowl() {
        super(Material.CIRCUITS);
        this.setRegistryName(ModInfo.ID, "SWDogBowl");
        this.setUnlocalizedName("dogbowl");
        this.setCreativeTab(SWTabs.tab);
        this.setSoundType(SoundType.STONE);
        this.setHardness(0.7F);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }


    @Override
    public boolean isCollidable() {
        return super.isCollidable();
    }

    private static final AxisAlignedBB BB = new AxisAlignedBB(0.25, 0, 0.25, 0.75, 0.0625, 0.75);

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return BB;
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return null;
    }

    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
        Item item = Item.getItemFromBlock(this);
        for (int meta = 0; meta < EnumDogBowl.values().length; meta++) {
            list.add(new ItemStack(item, 1, meta));
        }
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityDogBowl(world);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        TileEntityDogBowl tileEntity = (TileEntityDogBowl) world.getTileEntity(pos);
        if (tileEntity != null && !player.isSneaking()) {
            player.openGui(SophisticatedWolvesMod.instance, SWGui.DOG_BOWL_ID, world, pos.getX(), pos.getY(), pos.getZ());
            return true;
        }
        return false;
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(world, pos, state, placer, stack);

        TileEntityDogBowl tileEntity = (TileEntityDogBowl) world.getTileEntity(pos);
        if (tileEntity != null) {
            tileEntity.setFoodAmount(((EnumDogBowl) state.getValue(VARIANT)).getAmountOfFood());
        }
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public int damageDropped(IBlockState state) {
        int meta = ((EnumDogBowl) state.getValue(VARIANT)).ordinal();
        return (meta == 0) ? 0 : meta -1;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(VARIANT, EnumDogBowl.getById(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return ((EnumDogBowl) state.getValue(VARIANT)).ordinal();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{VARIANT});
    }
}

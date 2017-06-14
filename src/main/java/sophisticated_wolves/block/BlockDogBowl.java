package sophisticated_wolves.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import sophisticated_wolves.SWTabs;
import sophisticated_wolves.api.ModInfo;

import javax.annotation.Nullable;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class BlockDogBowl extends Block {
    enum EnumDogBowl implements IStringSerializable {
        EMPTY("empty"),
        FILLED1("filled1"),
        FILLED2("filled2"),
        FILLED3("filled3");

        private String blockModelName;

        private EnumDogBowl(String blockModelName) {
            this.blockModelName = blockModelName;
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
    }

    public static final PropertyEnum VARIANT = PropertyEnum.create("variant", EnumDogBowl.class);

    public BlockDogBowl() {
        super(Material.ROCK);
        this.setRegistryName(ModInfo.ID, "SWDogBowl");
        this.setUnlocalizedName("dogbowl");
        this.setCreativeTab(SWTabs.tab);
        this.setSoundType(SoundType.STONE);
        this.setHardness(0.1F);
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
    public void getSubBlocks(Item item, CreativeTabs tab, NonNullList<ItemStack> list) {
        for (int meta = 0; meta < EnumDogBowl.values().length; meta++) {
            list.add(new ItemStack(item, 1, meta));
        }
    }

//    @Override
//    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
//        return super.getItemDropped(state, rand, fortune);
//    }

    @Override
    public int damageDropped(IBlockState state) {
        return ((Enum) state.getValue(VARIANT)).ordinal();
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

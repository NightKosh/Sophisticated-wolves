package sophisticated_wolves.block;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
//TODO
public class BlockKennel {//extends Block {

//    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
//
//    public BlockKennel() {
//        super(Material.WOOD);
//        this.setRegistryName(ModInfo.ID, "swkennel");
//        this.setUnlocalizedName("kennel");
//        this.setCreativeTab(SWTabs.tab);
//        this.setSoundType(SoundType.WOOD);
//        this.setHardness(0.7F);
//    }
//
//    @Override
//    public boolean isOpaqueCube(IBlockState state) {
//        return false;
//    }
//
//    @Override
//    public boolean isFullCube(IBlockState state) {
//        return false;
//    }
//
//
//    private static final AABB BB = new AABB(0.1, 0, 0.1, 0.9, 0.8, 0.9);
//
//    @Override
//    public AABB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
//        return BB;
//    }
//
//    @Nullable
//    @Override
//    public AABB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
//        return BB;
//    }
//
//    @Override
//    public EnumBlockRenderType getRenderType(IBlockState state) {
//        return EnumBlockRenderType.MODEL;
//    }
//
//    @Override
//    public int damageDropped(IBlockState state) {
//        return 0;
//    }
//
//    @Override
//    public void onBlockPlacedBy(Level world, BlockPos pos, IBlockState state, LivingEntity player, ItemStack itemStack) {
//        var enumfacing = EnumFacing.getHorizontal(Mth.floor((double) (player.getYRot() * 4 / 360F) + 0.5) & 3).getOpposite();
//
//        world.setBlockState(pos, state.withProperty(FACING, enumfacing), 2);
//    }
//
//    @Override
//    public IBlockState getStateFromMeta(int meta) {
//        var enumfacing = EnumFacing.getFront(meta);
//
//        if (enumfacing.getAxis() == EnumFacing.Axis.Y) {
//            enumfacing = EnumFacing.NORTH;
//        }
//
//        return this.getDefaultState().withProperty(FACING, enumfacing);
//    }
//
//    @Override
//    public int getMetaFromState(IBlockState state) {
//        return (state.getValue(FACING)).getIndex();
//    }
//
//    @Override
//    protected BlockStateContainer createBlockState() {
//        return new BlockStateContainer(this, new IProperty[]{FACING});
//    }

}

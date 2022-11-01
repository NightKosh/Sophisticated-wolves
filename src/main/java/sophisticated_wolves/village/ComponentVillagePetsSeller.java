package sophisticated_wolves.village;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.Ocelot;
import net.minecraft.world.entity.animal.Parrot;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import sophisticated_wolves.api.EnumWolfSpecies;
import sophisticated_wolves.entity.SophisticatedWolf;
import sophisticated_wolves.item.pet_carrier.CatPetCarrier;
import sophisticated_wolves.item.pet_carrier.ParrotPetCarrier;
import sophisticated_wolves.item.pet_carrier.RabbitPetCarrier;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
//TODO update or remove
public class ComponentVillagePetsSeller {//extends StructureVillagePieces.Village {

    private int averageGroundLevel = -1;
    private static final int HEIGHT = 8;
    private static final int X_LENGTH = 14;
    private static final int Z_LENGTH = 8;

    protected static final UUID uuid = UUID.randomUUID();
    //TODO
//    protected static final Set<Biome> biomeSetSandy = BiomeDictionary.getBiomes(BiomeDictionary.Type.SANDY);
    //TODO
//    protected static final IBlockState OAK_DOOR = Blocks.OAK_DOOR.getDefaultState();
//    protected static final IBlockState COBBLESTONE = Blocks.COBBLESTONE.getDefaultState();
//    protected static final IBlockState SANDSTONE = Blocks.SANDSTONE.getDefaultState();
//    protected static final IBlockState PLANKS_OAK = Blocks.PLANKS.getDefaultState().withProperty(BlockPlanks.VARIANT, BlockPlanks.EnumType.OAK);
//    protected static final IBlockState OAK = Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.OAK).withProperty(BlockOldLog.LOG_AXIS, BlockLog.EnumAxis.Y);
//    protected static final IBlockState GLASS_PANE = Blocks.GLASS_PANE.getDefaultState();
//    protected static final IBlockState OAK_SLAB = Blocks.WOODEN_SLAB.getDefaultState().withProperty(BlockWoodSlab.VARIANT, BlockPlanks.EnumType.OAK);
//    protected static final IBlockState SANDSTONE_SLAB = Blocks.STONE_SLAB.getDefaultState().withProperty(BlockStoneSlab.VARIANT, BlockStoneSlab.EnumType.SAND);
//    protected static final IBlockState OAK_STAIRS = Blocks.OAK_STAIRS.getDefaultState();
//    protected static final IBlockState STONE_STAIRS = Blocks.STONE_STAIRS.getDefaultState();
//    protected static final IBlockState SANDSTONE_STAIRS = Blocks.SANDSTONE_STAIRS.getDefaultState();
//    protected static final IBlockState FENCE = Blocks.OAK_FENCE.getDefaultState();
//    protected static final IBlockState PLATE = Blocks.WOODEN_PRESSURE_PLATE.getDefaultState();
//
//    public ComponentVillagePetsSeller() {
//    }
//
//    public ComponentVillagePetsSeller(StructureVillagePieces.Start startPiece, int componentType, Random random, StructureBoundingBox structureBoundingBox, EnumFacing direction) {
//        super(startPiece, componentType);
//        this.setCoordBaseMode(direction);
//        this.boundingBox = structureBoundingBox;
//    }
//
//    public static ComponentVillagePetsSeller buildComponent(StructureVillagePieces.Start startPiece, List list, Random random, int par3, int par4, int par5, EnumFacing facing, int par7) {
//        StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(par3, par4, par5, 0, 0, 0, X_LENGTH + 1, HEIGHT, Z_LENGTH + 1, facing);
//        return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(list, structureboundingbox) == null ? new ComponentVillagePetsSeller(startPiece, par7, random, structureboundingbox, facing) : null;
//    }
//
//    @Override
//    public boolean addComponentParts(Level level, Random random, StructureBoundingBox boundingBox) {
//        return generateComponent(level, random, boundingBox);
//    }
//
//    public boolean generateComponent(Level level, Random random, StructureBoundingBox boundingBox) {
//        if (this.averageGroundLevel < 0) {
//            this.averageGroundLevel = this.getAverageGroundLevel(level, boundingBox);
//
//            if (this.averageGroundLevel < 0) {
//                return true;
//            }
//            this.boundingBox.offset(0, this.averageGroundLevel - this.boundingBox.maxY + HEIGHT - 1, 0);
//        }
//
//        Biome biome = level.getBiome(new BlockPos(this.getXWithOffset(0, 0), this.getYWithOffset(0), this.getZWithOffset(0, 0)));
//
//        this.fillWithAir(level, boundingBox, 0, 1, 0, X_LENGTH, HEIGHT, Z_LENGTH);
//
//        IBlockState ground;
//        IBlockState wallLogs;
//        IBlockState wallPlanks;
//        IBlockState slab;
//        IBlockState stairsFloor;
//        IBlockState stairsRoof;
//
//        if (biomeSetSandy.contains(biome)) {
//            ground = SANDSTONE;
//            wallLogs = SANDSTONE;//TODO
//            wallPlanks = SANDSTONE;
//            slab = SANDSTONE_SLAB;
//            stairsFloor = SANDSTONE_STAIRS;
//            stairsRoof = SANDSTONE_STAIRS;
//        } else {
//            ground = COBBLESTONE;
//            wallLogs = OAK;
//            wallPlanks = PLANKS_OAK;
//            slab = OAK_SLAB;
//            stairsFloor = STONE_STAIRS;
//            stairsRoof = OAK_STAIRS;
//        }
//
//        stairsFloor = getStairs(stairsFloor, this.getCoordBaseMode());
//        stairsRoof = getStairs(stairsRoof, this.getCoordBaseMode());
//
//        IBlockState stairsRoof_opposite = getStairs(stairsRoof, this.getCoordBaseMode().getOpposite());
//        IBlockState stairsRoof_down = stairsRoof_opposite.withProperty(BlockStairs.HALF, BlockStairs.EnumHalf.TOP);
//        IBlockState stairsRoof_down_opposite = stairsRoof.withProperty(BlockStairs.HALF, BlockStairs.EnumHalf.TOP);
//
//        // ground
//        this.fillWithBlocks(level, boundingBox, 1, 0, 1, 13, 0, 7, ground);
//
//        // walls
//        this.fillWithBlocks(level, boundingBox, 2, 1, 1, 12, 3, 1, wallPlanks);
//        this.fillWithBlocks(level, boundingBox, 2, 1, 7, 12, 3, 7, wallPlanks);
//
//        this.fillWithBlocks(level, boundingBox, 1, 1, 2, 1, 3, 6, wallPlanks);
//        this.fillWithBlocks(level, boundingBox, 13, 1, 2, 13, 3, 6, wallPlanks);
//
//        this.fillWithBlocks(level, boundingBox, 1, 1, 1, 1, 3, 1, wallLogs);
//        this.fillWithBlocks(level, boundingBox, 13, 1, 1, 13, 3, 1, wallLogs);
//        this.fillWithBlocks(level, boundingBox, 1, 1, 7, 1, 3, 7, wallLogs);
//        this.fillWithBlocks(level, boundingBox, 13, 1, 7, 13, 3, 7, wallLogs);
//
//        this.fillWithBlocks(level, boundingBox, 4, 2, 1, 5, 2, 1, GLASS_PANE);
//        this.fillWithBlocks(level, boundingBox, 9, 2, 1, 10, 2, 1, GLASS_PANE);
//        this.fillWithBlocks(level, boundingBox, 4, 2, 7, 5, 2, 7, GLASS_PANE);
//        this.fillWithBlocks(level, boundingBox, 9, 2, 7, 10, 2, 7, GLASS_PANE);
//
//        this.fillWithBlocks(level, boundingBox, 6, 0, 0, 8, 0, 0, stairsFloor);
//
//        // door
//        this.placeDoorCurrentPosition(level, boundingBox, random, 7, 1, 1, EnumFacing.NORTH);
//
//        // roof
//        this.fillWithBlocks(level, boundingBox, 1, 4, 2, 13, 4, 6, wallPlanks);
//        this.fillWithBlocks(level, boundingBox, 1, 5, 3, 13, 5, 5, wallPlanks);
//        this.fillWithBlocks(level, boundingBox, 1, 6, 4, 13, 6, 4, wallPlanks);
//
//        this.fillWithBlocks(level, boundingBox, 0, 3, 0, 14, 3, 0, stairsRoof);
//        this.fillWithBlocks(level, boundingBox, 0, 4, 1, 14, 4, 1, stairsRoof);
//        this.fillWithBlocks(level, boundingBox, 0, 5, 2, 14, 5, 2, stairsRoof);
//        this.fillWithBlocks(level, boundingBox, 0, 6, 3, 14, 6, 3, stairsRoof);
//
//        this.fillWithBlocks(level, boundingBox, 0, 3, 8, 14, 3, 8, stairsRoof_opposite);
//        this.fillWithBlocks(level, boundingBox, 0, 4, 7, 14, 4, 7, stairsRoof_opposite);
//        this.fillWithBlocks(level, boundingBox, 0, 5, 6, 14, 5, 6, stairsRoof_opposite);
//        this.fillWithBlocks(level, boundingBox, 0, 6, 5, 14, 6, 5, stairsRoof_opposite);
//
//        this.fillWithBlocks(level, boundingBox, 0, 7, 4, 14, 7, 4, slab);
//
//        this.setBlockState(level, stairsRoof_down, 0, 3, 1, boundingBox);
//        this.setBlockState(level, stairsRoof_down, 0, 4, 2, boundingBox);
//        this.setBlockState(level, stairsRoof_down, 0, 5, 3, boundingBox);
//        this.setBlockState(level, stairsRoof_down, 14, 3, 1, boundingBox);
//        this.setBlockState(level, stairsRoof_down, 14, 4, 2, boundingBox);
//        this.setBlockState(level, stairsRoof_down, 14, 5, 3, boundingBox);
//
//        this.setBlockState(level, stairsRoof_down_opposite, 0, 3, 7, boundingBox);
//        this.setBlockState(level, stairsRoof_down_opposite, 0, 4, 6, boundingBox);
//        this.setBlockState(level, stairsRoof_down_opposite, 0, 5, 5, boundingBox);
//        this.setBlockState(level, stairsRoof_down_opposite, 14, 3, 7, boundingBox);
//        this.setBlockState(level, stairsRoof_down_opposite, 14, 4, 6, boundingBox);
//        this.setBlockState(level, stairsRoof_down_opposite, 14, 5, 5, boundingBox);
//
//        this.setBlockState(level, wallPlanks, 0, 6, 4, boundingBox);
//        this.setBlockState(level, wallPlanks, 14, 6, 4, boundingBox);
//
//        // inside house
//        this.fillWithBlocks(level, boundingBox, 3, 1, 2, 3, 3, 6, GLASS_PANE);
//        this.fillWithBlocks(level, boundingBox, 11, 1, 2, 11, 3, 6, GLASS_PANE);
//
//        this.fillWithBlocks(level, boundingBox, 2, 1, 3, 2, 3, 3, GLASS_PANE);
//        this.fillWithBlocks(level, boundingBox, 2, 1, 5, 2, 3, 5, GLASS_PANE);
//        this.fillWithBlocks(level, boundingBox, 12, 1, 3, 12, 3, 3, GLASS_PANE);
//        this.fillWithBlocks(level, boundingBox, 12, 1, 5, 12, 3, 5, GLASS_PANE);
//
//        this.fillWithBlocks(level, boundingBox, 6, 1, 5, 8, 1, 5, FENCE);
//        this.fillWithBlocks(level, boundingBox, 6, 2, 5, 8, 2, 5, PLATE);
//
//
//        // torches
//        this.placeTorch(level, this.getCoordBaseMode().getOpposite(), 2, 2, 0, boundingBox);
//        this.placeTorch(level, this.getCoordBaseMode().getOpposite(), 6, 2, 0, boundingBox);
//        this.placeTorch(level, this.getCoordBaseMode().getOpposite(), 8, 2, 0, boundingBox);
//        this.placeTorch(level, this.getCoordBaseMode().getOpposite(), 12, 2, 0, boundingBox);
//
//        this.placeTorch(level, this.getCoordBaseMode().getOpposite(), 6, 2, 6, boundingBox);
//        this.placeTorch(level, this.getCoordBaseMode().getOpposite(), 8, 2, 6, boundingBox);
//
//        this.placeTorch(level, this.getCoordBaseMode(), 6, 2, 2, boundingBox);
//        this.placeTorch(level, this.getCoordBaseMode(), 8, 2, 2, boundingBox);
//
//        // pets
//        spawnPet(level, random, boundingBox, 2, 1, 2);
//        spawnPet(level, random, boundingBox, 2, 1, 4);
//        spawnPet(level, random, boundingBox, 2, 1, 6);
//
//        spawnPet(level, random, boundingBox, 12, 1, 2);
//        spawnPet(level, random, boundingBox, 12, 1, 4);
//        spawnPet(level, random, boundingBox, 12, 1, 6);
//
//
//        for (int i = 1; i < X_LENGTH; i++) {
//            for (int j = 1; j < Z_LENGTH; j++) {
//                this.clearCurrentPositionBlocksUpwards(level, i, 8, j, boundingBox);
//                this.setBlockState(level, ground, i, -1, j, boundingBox);
//            }
//        }
//
//        this.spawnVillagers(level, boundingBox, 7, 1, 4, 1);
//        return true;
//    }
//
//    protected void spawnPet(Level level, Random random, StructureBoundingBox boundingBox, int x, int y, int z) {
//        int xPos = this.getXWithOffset(x, z);
//        int yPos = this.getYWithOffset(y);
//        int zPos = this.getZWithOffset(x, z);
//
//        if (boundingBox.isVecInside(new BlockPos(xPos, yPos, zPos))) {
//            var pet = getPet(level, random);
//            pet.moveTo(xPos + 0.5, yPos, zPos + 0.5, 0, 0);
//            level.spawnEntity(pet);
//        }
//    }
//
//    protected LivingEntity getPet(Level level, Random random) {
//        switch (random.nextInt(6)) {
//            case 0:
//                return new Chicken(level);
//            case 1:
//            default:
//                return getWolf(level);
//            case 2:
//                return getSWolf(level, random);
//            case 3:
//                return getCat(level, random);
//            case 4:
//                return getRabbit(level, random);
//            case 5:
//                return getParrot(level, random);
//        }
//    }
//
//    protected LivingEntity getWolf(Level level) {
//        var pet = new Wolf(level);
//        pet.setTame(true);
//        pet.setOwnerUUID(uuid);
//        return pet;
//    }
//
//    protected LivingEntity getSWolf(Level level, Random random) {
//        var pet = new SophisticatedWolf(level);
//        pet.setTame(true);
//        pet.setOwnerUUID(uuid);
//        pet.updateSpecies(EnumWolfSpecies.values()[random.nextInt(EnumWolfSpecies.values().length)]);
//        return pet;
//    }
//
//    protected LivingEntity getCat(Level level, Random random) {
//        var pet = new Cat(level);
//        pet.setTame(true);
//        pet.setOwnerUUID(uuid);
//        pet.setCatVariant(random.nextInt(CatPetCarrier.EnumCatType.values().length));
//        return pet;
//    }
//
//    protected LivingEntity getRabbit(Level level, Random random) {
//        var pet = new Rabbit(level);
//        pet.setRabbitType(random.nextInt(RabbitPetCarrier.RABBITS_SPECIES.length));
//        return pet;
//    }
//
//    protected LivingEntity getParrot(Level level, Random random) {
//        var pet = new Parrot(level);
//        pet.setTame(true);
//        pet.setOwnerUUID(uuid);
//        pet.setVariant(random.nextInt(ParrotPetCarrier.PARROTS_SPECIES.length));
//        return pet;
//    }
//
//    public static IBlockState getStairs(IBlockState stairs, EnumFacing direction) {
//        return stairs.withProperty(BlockStairs.FACING, direction);
//    }
//
//    protected void placeDoorCurrentPosition(Level level, StructureBoundingBox boundingBoxIn, Random rand, int x, int y, int z, EnumFacing facing) {
//        this.setBlockState(level, OAK_DOOR.withProperty(BlockDoor.FACING, facing), x, y, z, boundingBoxIn);
//        this.setBlockState(level, OAK_DOOR.withProperty(BlockDoor.FACING, facing).withProperty(BlockDoor.HALF, BlockDoor.EnumDoorHalf.UPPER), x, y + 1, z, boundingBoxIn);
//    }
//
//    @Override
//    protected void setBlockState(Level level, IBlockState blockState, int x, int y, int z, StructureBoundingBox boundingBox) {
//        int xPos = this.getXWithOffset(x, z);
//        int yPos = this.getYWithOffset(y);
//        int zPos = this.getZWithOffset(x, z);
//
//        BlockPos pos = new BlockPos(xPos, yPos, zPos);
//        if (boundingBox.isVecInside(pos)) {
//            level.setBlockState(pos, blockState, 2);
//        }
//    }
//
//    protected void fillWithBlocks(Level level, StructureBoundingBox boundingBox, int xMin, int yMin, int zMin,
//                                  int xMax, int yMax, int zMax, IBlockState state) {
//        super.fillWithBlocks(level, boundingBox, xMin, yMin, zMin, xMax, yMax, zMax, state, state, false);
//    }
//
//    @Override
//    protected VillagerRegistry.VillagerProfession chooseForgeProfession(int count, net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession prof) {
//        return VillagersHandler.petsSellerProfession;
//    }

}

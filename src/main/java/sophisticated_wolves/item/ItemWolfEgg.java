package sophisticated_wolves.item;

import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import org.apache.commons.lang3.StringUtils;
import sophisticated_wolves.SWEntity;
import sophisticated_wolves.SWTabs;
import sophisticated_wolves.SophisticatedWolvesMod;
import sophisticated_wolves.api.EnumWolfSpecies;
import sophisticated_wolves.api.ModInfo;
import sophisticated_wolves.entity.EntitySophisticatedWolf;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class ItemWolfEgg extends ItemMonsterPlacer {
    public static enum EnumEggs {
        SOPHISTICATED_WOLF(SWEntity.SW_NAME, 14144467, 13545366);

        private String name;
        private int backgroundColor;
        private int foregroundColor;

        private EnumEggs(String name, int backgroundColor, int foregroundColor) {
            this.name = name;
            this.backgroundColor = backgroundColor;
            this.foregroundColor = foregroundColor;
        }

        public static EnumEggs getById(int id) {
            if (id > EnumEggs.values().length || id < 0) {
                return SOPHISTICATED_WOLF;
            }
            return EnumEggs.values()[id];
        }

        public String getName() {
            return name;
        }

        public int getBackgroundColor() {
            return backgroundColor;
        }

        public int getForegroundColor() {
            return foregroundColor;
        }
    }

    public ItemWolfEgg() {
        this.setHasSubtypes(true);
        this.setCreativeTab(SWTabs.tab);
        this.setUnlocalizedName("monsterPlacer");
        this.setRegistryName(ModInfo.ID, "SWDogEgg");
    }

    @Override
    public String getItemStackDisplayName(ItemStack itemStack) {
        StringBuilder str = new StringBuilder();
        str.append(SophisticatedWolvesMod.proxy.getLocalizedString(this.getUnlocalizedName() + ".name").trim());

        String name = EnumEggs.SOPHISTICATED_WOLF.getName();
        if (StringUtils.isNotBlank(name)) {
            str.append(" ").append(SophisticatedWolvesMod.proxy.getLocalizedString("entity." + name + ".name"))
                    .append(" - ").append(SophisticatedWolvesMod.proxy.getLocalizedString("wolf_type." + EnumWolfSpecies.getSpeciesByNum(itemStack.getItemDamage()).toString().toLowerCase()));
        }

        return str.toString();
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos blockPos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack item = player.getHeldItem(hand);

        if (world.isRemote) {
            return EnumActionResult.SUCCESS;
        } else {
            IBlockState block = world.getBlockState(blockPos);
            double d0 = 0;
            if (facing == EnumFacing.UP && block instanceof BlockFence) {
                d0 = 0.5;
            }

            blockPos = blockPos.offset(facing);
            Entity entity = spawnCreature(world, item.getItemDamage(), blockPos.getX() + 0.5, blockPos.getY() + d0, blockPos.getZ() + 0.5);
            if (entity != null) {
                if (entity instanceof EntityLivingBase && item.hasDisplayName()) {
                    entity.setCustomNameTag(item.getDisplayName());
                }

                if (!player.capabilities.isCreativeMode) {
                    item.shrink(1);
                }
            }

            return EnumActionResult.SUCCESS;
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack item = player.getHeldItem(hand);

        if (world.isRemote) {
            return new ActionResult(EnumActionResult.PASS, item);
        } else {
            RayTraceResult rayTraceEesult = this.rayTrace(world, player, true);

            if (rayTraceEesult == null) {
                return new ActionResult(EnumActionResult.PASS, item);
            } else {
                if (rayTraceEesult.typeOfHit == RayTraceResult.Type.BLOCK) {
                    if (!world.canMineBlockBody(player, rayTraceEesult.getBlockPos()) ||
                            !player.canPlayerEdit(rayTraceEesult.getBlockPos(), rayTraceEesult.sideHit, item)) {
                        return new ActionResult(EnumActionResult.PASS, item);
                    }

                    if (world.getBlockState(rayTraceEesult.getBlockPos()).getBlock() instanceof BlockLiquid) {
                        Entity entity = spawnCreature(world, item.getItemDamage(), rayTraceEesult.getBlockPos().getX(),
                                rayTraceEesult.getBlockPos().getY(), rayTraceEesult.getBlockPos().getZ());

                        if (entity != null) {
                            if (entity instanceof EntityLivingBase && item.hasDisplayName()) {
                                entity.setCustomNameTag(item.getDisplayName());
                            }

                            if (!player.capabilities.isCreativeMode) {
                                item.shrink(1);
                            }
                        }
                    }
                }

                return new ActionResult(EnumActionResult.SUCCESS, item);
            }
        }
    }

    public static Entity spawnCreature(World world, int eggMeta, double x, double y, double z) {
        EntitySophisticatedWolf wolf = new EntitySophisticatedWolf(world);

        wolf.setLocationAndAngles(x, y, z, MathHelper.wrapDegrees(world.rand.nextFloat() * 360), 0);
        wolf.rotationYawHead = wolf.rotationYaw;
        wolf.renderYawOffset = wolf.rotationYaw;
        wolf.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(wolf)), null);
        wolf.updateSpecies(EnumWolfSpecies.getSpeciesByNum(eggMeta));
        world.spawnEntity(wolf);
        wolf.playLivingSound();

        return wolf;
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (this.isInCreativeTab(tab)) {
            for (int i = 0; i < EnumWolfSpecies.values().length; i++) {
                items.add(new ItemStack(this, 1, i));
            }
        }
    }
}
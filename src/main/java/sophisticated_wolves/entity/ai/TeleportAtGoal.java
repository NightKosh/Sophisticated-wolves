package sophisticated_wolves.entity.ai;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.Level;
import sophisticated_wolves.entity.SophisticatedWolf;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class TeleportAtGoal extends Goal {

    protected final SophisticatedWolf wolf;
    protected final Level level;
    protected final PathNavigation navigation;

    protected LivingEntity owner;
    protected boolean isActive = false;

    public TeleportAtGoal(SophisticatedWolf wolf) {
        this.wolf = wolf;
        this.owner = wolf.getOwner();
        this.level = wolf.level();
        this.navigation = wolf.getNavigation();
    }

    @Override
    public boolean canUse() {
        return canContinueToUse();
    }

    @Override
    public boolean canContinueToUse() {
        if (this.isActive &&
                this.wolf.isTame()) {
            if (this.owner == null) {
                this.owner = this.wolf.getOwner();
            }

            return this.owner != null && this.customConditions();
        }
        return false;
    }

    protected boolean customConditions() {
        return true;
    }

    @Override
    public void start() {
    }

    @Override
    public void stop() {
        this.setActive(false);
    }

    @Override
    public void tick() {
        int xPos = Mth.floor(this.owner.getX()) - 2;
        int yPos = Mth.floor(this.owner.getBoundingBox().minY);
        int zPos = Mth.floor(this.owner.getZ()) - 2;

        for (int x = -2; x <= 2; x++) {
            for (int z = -2; z <= 2; z++) {
                var pos = new BlockPos(xPos + x, yPos, zPos + z);
                var state = this.level.getBlockState(pos);
                if (state.isAir()) {
                    this.wolf.moveTo(xPos + x + 0.5, yPos, zPos + z + 0.5,
                            this.wolf.getYRot(), this.wolf.getXRot());
                    this.navigation.stop();
                    return;
                }
            }
        }
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

}

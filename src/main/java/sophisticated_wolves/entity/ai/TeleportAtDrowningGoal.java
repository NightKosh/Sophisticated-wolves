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
public class TeleportAtDrowningGoal extends Goal {

    private final SophisticatedWolf wolf;
    private final Level level;
    private final PathNavigation navigation;
    private LivingEntity owner;

    private boolean isDrowning = false;

    public TeleportAtDrowningGoal(SophisticatedWolf wolf) {
        this.wolf = wolf;
        this.owner = wolf.getOwner();
        this.level = wolf.getLevel();
        this.navigation = wolf.getNavigation();
    }

    @Override
    public boolean canUse() {
        return canContinueToUse();
    }

    @Override
    public boolean canContinueToUse() {
        if (this.isDrowning &&
                this.wolf.isInWater() &&
                this.wolf.isTame()) {
            if (this.owner == null) {
                this.owner = this.wolf.getOwner();
            }

            return this.owner != null;
        }
        return false;
    }

    @Override
    public void start() {
    }

    @Override
    public void stop() {
        this.setDrowning(false);
    }

    @Override
    public void tick() {
        int xPos = Mth.floor(this.owner.getX()) - 2;
        int yPos = Mth.floor(this.owner.getBoundingBox().minY);
        int zPos = Mth.floor(this.owner.getZ()) - 2;

        for (int x = -2; x <= 2; ++x) {
            for (int z = -2; z <= 2; ++z) {
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

    public void setDrowning(boolean isDrowning) {
        this.isDrowning = isDrowning;
    }

}

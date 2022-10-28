package sophisticated_wolves.entity.ai;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
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

    protected SophisticatedWolf wolf;
    protected Level level;
    protected PathNavigation petPathfinder;

    public TeleportAtDrowningGoal(SophisticatedWolf animal) {
        this.wolf = animal;
        this.level = animal.getLevel();
        this.petPathfinder = animal.getNavigation();
    }

    @Override
    public boolean canUse() {
        return this.wolf.isDrowning() &&
                this.wolf.isInWater() &&
                this.wolf.isTame() &&
                this.wolf.getOwner() != null;
    }

    @Override
    public boolean canContinueToUse() {
        return this.wolf.isDrowning() &&
                this.wolf.isInWater() &&
                this.wolf.isTame() &&
                this.wolf.getOwner() != null;
    }

    @Override
    public void start() {
    }

    @Override
    public void stop() {
        this.wolf.setDrowning(false);
    }

    @Override
    public void tick() {
        int xPos = Mth.floor(this.wolf.getOwner().getX()) - 2;
        int yPos = Mth.floor(this.wolf.getOwner().getBoundingBox().minY);
        int zPos = Mth.floor(this.wolf.getOwner().getZ()) - 2;

        for (int x = -2; x <= 2; ++x) {
            for (int z = -2; z <= 2; ++z) {
                var pos = new BlockPos(xPos + x, yPos, zPos + z);
                var state = this.level.getBlockState(pos);
                if (state.isAir()) {
                    this.wolf.moveTo(xPos + x + 0.5, yPos, zPos + z + 0.5,
                            this.wolf.getYRot(), this.wolf.getXRot());
                    this.petPathfinder.stop();
                    return;
                }
            }
        }
    }

}

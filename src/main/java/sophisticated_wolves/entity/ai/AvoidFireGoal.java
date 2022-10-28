package sophisticated_wolves.entity.ai;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

/**
 * Sophisticated Wolves
 *
 * @author metroidfood
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class AvoidFireGoal extends Goal {

    private final PathfinderMob mob;
    private final double moveSpeed;
    private final double minSpeed;
    private final double maxSpeed;
    private final Level level;

    private double xPath;
    private double yPath;
    private double zPath;

    public AvoidFireGoal(PathfinderMob mob, double minSpeed, double maxSpeed) {
        this.mob = mob;
        this.moveSpeed = this.mob.getMoveControl().getSpeedModifier();
        this.minSpeed = minSpeed;
        this.maxSpeed = maxSpeed;
        this.level = this.mob.getLevel();
    }

    /**
     * Returns whether the Goal should begin execution.
     */
    @Override
    public boolean canUse() {
        if (!this.mob.isOnGround()) {
            return false;
        }

        Vec3 vec3 = DefaultRandomPos.getPos(mob, 10, 7);

        if (vec3 == null) {
            return false;
        } else {
            this.xPath = vec3.x;
            this.yPath = vec3.y;
            this.zPath = vec3.z;
            //TODO
            return false;
//            return this.level.isFlammableWithin(this.mob.getBoundingBox().contract(0.001, 0.001, 0.001));
        }
    }

    /**
     * Returns whether an in-progress Goal should continue executing
     */
    @Override
    public boolean canContinueToUse() {
        return !this.mob.getNavigation().isDone();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
    public void start() {
        this.mob.getNavigation().moveTo(this.xPath, this.yPath, this.zPath, this.moveSpeed);
    }

    @Override
    public void tick() {
        this.mob.getNavigation().setSpeedModifier(this.mob.isOnFire() ?
                this.maxSpeed :
                this.minSpeed);
    }

}

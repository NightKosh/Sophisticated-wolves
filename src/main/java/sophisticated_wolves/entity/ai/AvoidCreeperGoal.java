package sophisticated_wolves.entity.ai;

import com.google.common.base.Predicates;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

import java.util.function.Predicate;

/**
 * Sophisticated Wolves
 *
 * @author metroidfood
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class AvoidCreeperGoal extends Goal {

    private final Predicate<Entity> canBeSeenSelector;
    private final PathNavigation pathNavigation;

    protected PathfinderMob mob;
    protected double regSpeed;
    protected double sprintSpeed;
    protected Creeper creeper;
    protected int distance;
    protected int minDistToCharged;
    protected int minDist;
    protected Path pathEntity;
    protected float listSize;

    public AvoidCreeperGoal(PathfinderMob mob, int distance, int minDistToExplode, int minDist, double regSpeed, double sprintSpeed) {
        this.mob = mob;
        this.distance = distance;
        this.minDistToCharged = minDistToExplode;
        this.minDist = minDist;
        this.regSpeed = regSpeed;
        this.sprintSpeed = sprintSpeed;
        this.pathNavigation = mob.getNavigation();

        this.canBeSeenSelector = prEntity ->
                prEntity.isAlive() &&
                mob.hasLineOfSight(prEntity);
    }

    /**
     * Returns whether the Goal should begin execution.
     */
    @Override
    public boolean canUse() {
        var list = this.mob.getLevel().getEntitiesOfClass(
                Creeper.class,
                this.mob.getBoundingBox()
                        .inflate(this.distance, 3, this.distance),
                this.canBeSeenSelector);

        if (list.isEmpty()) {
            return false;
        } else {
            this.creeper = null;
            this.listSize = list.size();

            for (int cr = 0; cr < this.listSize; cr++) {
                this.creeper = (Creeper) list.get(cr);

                if (this.creeper.isIgnited()) {
                    if (this.mob.distanceTo(creeper) < this.minDistToCharged * this.minDistToCharged) {
                        return moveAway(16, 7);
                    }
                } else if (this.mob.distanceTo(creeper) < this.minDist * this.minDist) {
                    return moveAway(this.minDist, this.minDist);
                }
            }
        }
        return false;
    }

    protected boolean moveAway(int x, int y) {
        Vec3 vec3 = DefaultRandomPos.getPosAway(this.mob, x, y, this.creeper.position());

        if (vec3 != null) {
            if (this.creeper.distanceToSqr(vec3.x, vec3.y, vec3.z) > this.creeper.distanceToSqr(this.mob)) {
                this.pathEntity = this.pathNavigation.createPath(vec3.x, vec3.y, vec3.z, 0);

                return this.pathEntity != null;
            }
        }
        return false;
    }

    /**
     * Returns whether an in-progress Goal should continue executing
     */
    @Override
    public boolean canContinueToUse() {
        return !this.pathNavigation.isDone();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
    public void start() {
        this.pathNavigation.moveTo(this.pathEntity, regSpeed);
    }

    /**
     * Resets the task
     */
    @Override
    public void stop() {
        this.creeper = null;
    }

    /**
     * Updates the task
     */
    @Override
    public void tick() {
        this.mob.getNavigation().setSpeedModifier(this.mob.distanceTo(this.creeper) < 49 ?
                this.sprintSpeed :
                this.regSpeed);
    }

}

package sophisticated_wolves.entity.ai;

import net.minecraft.world.entity.Entity;
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

    private final PathfinderMob mob;
    private final double regSpeed;
    private final double sprintSpeed;
    private final int distance;
    private final int minDistToCharged;
    private final int minDist;
    private final int minDist2;
    private Creeper creeper;
    private Path pathEntity;

    public AvoidCreeperGoal(PathfinderMob mob, int distance, int minDistToExplode, int minDist, double regSpeed, double sprintSpeed) {
        this.mob = mob;
        this.distance = distance;
        this.minDistToCharged = minDistToExplode * minDistToExplode;
        this.minDist = minDist;
        this.minDist2 = minDist * minDist;
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
        var creepers = this.mob.getLevel().getEntitiesOfClass(
                Creeper.class,
                this.mob.getBoundingBox()
                        .inflate(this.distance, 3, this.distance),
                this.canBeSeenSelector);
        for (var creeper : creepers) {
            this.creeper = creeper;

            if (this.creeper.isIgnited()) {
                if (this.mob.distanceTo(this.creeper) < this.minDistToCharged) {
                    return moveAway(16, 7);
                }
            } else if (this.mob.distanceTo(this.creeper) < this.minDist2) {
                return moveAway(this.minDist, this.minDist);
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
        return this.creeper.isAlive() && !this.pathNavigation.isDone();
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

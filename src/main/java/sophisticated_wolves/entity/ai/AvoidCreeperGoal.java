package sophisticated_wolves.entity.ai;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.pathfinder.Path;
import sophisticated_wolves.entity.SophisticatedWolf;

import java.util.function.Predicate;

/**
 * Sophisticated Wolves
 *
 * @author metroidfood
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class AvoidCreeperGoal extends Goal {

    private static final int MIN_DIST_TO_CHARGED_CREEPER = 36;
    private static final int SPRINT_DISTANCE = 49;

    protected final Predicate<Entity> canBeSeenSelector;
    private final PathNavigation pathNavigation;

    protected final SophisticatedWolf wolf;
    protected final double regSpeed;
    protected final double sprintSpeed;
    protected final int distance;
    protected final int minDist;
    protected final int minDist2;
    protected Mob monster;
    private Path pathEntity;

    public AvoidCreeperGoal(SophisticatedWolf wolf, int distance, int minDist, double regSpeed, double sprintSpeed) {
        this.wolf = wolf;
        this.distance = distance;
        this.minDist = minDist;
        this.minDist2 = minDist * minDist;
        this.regSpeed = regSpeed;
        this.sprintSpeed = sprintSpeed;
        this.pathNavigation = wolf.getNavigation();

        this.canBeSeenSelector = entity -> entity.isAlive() && wolf.hasLineOfSight(entity);
    }

    /**
     * Returns whether the Goal should begin execution.
     */
    @Override
    public boolean canUse() {
        var creepers = this.wolf.getLevel().getEntitiesOfClass(
                Creeper.class,
                this.wolf.getBoundingBox()
                        .inflate(this.distance, 3, this.distance),
                this.canBeSeenSelector);
        for (var creeper : creepers) {

            if (creeper.isIgnited()) {
                if (this.wolf.distanceTo(creeper) < MIN_DIST_TO_CHARGED_CREEPER && moveAway(creeper, 16, 7)) {
                    this.monster = creeper;
                    return true;
                }
            } else if (this.wolf.distanceTo(creeper) < this.minDist2 && moveAway(creeper)) {
                this.monster = creeper;
                return true;
            }
        }
        return false;
    }

    protected boolean moveAway(Mob mob) {
        return moveAway(mob, this.minDist, this.minDist);
    }

    private boolean moveAway(Mob mob, int x, int y) {
        var vec3 = DefaultRandomPos.getPosAway(this.wolf, x, y, mob.position());

        if (vec3 != null && mob.distanceToSqr(vec3.x, vec3.y, vec3.z) > mob.distanceToSqr(this.wolf)) {
            this.pathEntity = this.pathNavigation.createPath(vec3.x, vec3.y, vec3.z, 0);
            return this.pathEntity != null;
        }
        return false;
    }

    /**
     * Returns whether an in-progress Goal should continue executing
     */
    @Override
    public boolean canContinueToUse() {
        return this.monster.isAlive() && !this.pathNavigation.isDone();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
    public void start() {
        this.pathNavigation.moveTo(this.pathEntity, this.regSpeed);
    }

    /**
     * Resets the task
     */
    @Override
    public void stop() {
        this.monster = null;
    }

    /**
     * Updates the task
     */
    @Override
    public void tick() {
        this.wolf.getNavigation().setSpeedModifier(this.wolf.distanceTo(this.monster) < SPRINT_DISTANCE ?
                this.sprintSpeed :
                this.regSpeed);
    }

}

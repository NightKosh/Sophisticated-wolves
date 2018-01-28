package sophisticated_wolves.entity.ai;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.Vec3d;

import java.util.List;

/**
 * Sophisticated Wolves
 *
 * @author metroidfood
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class EntityAIAvoidCreeper extends EntityAIBase {

    protected EntityCreature entity;
    protected double regSpeed;
    protected double sprintSpeed;
    protected EntityCreeper creeper;
    protected int distance;
    protected int minDistToCharged;
    protected int minDist;
    protected Path pathEntity;
    protected float listSize;

    private final Predicate<Entity> canBeSeenSelector;

    private PathNavigate entityPathNavigate;

    public EntityAIAvoidCreeper(EntityCreature entity, int distance, int minDistToExplode, int minDist, double regSpeed, double sprintSpeed) {
        this.entity = entity;
        this.distance = distance;
        this.minDistToCharged = minDistToExplode;
        this.minDist = minDist;
        this.regSpeed = regSpeed;
        this.sprintSpeed = sprintSpeed;
        this.entityPathNavigate = entity.getNavigator();
        this.setMutexBits(1);

        this.canBeSeenSelector = prEntity -> prEntity.isEntityAlive() && entity.getEntitySenses().canSee(prEntity) && !entity.isOnSameTeam(prEntity);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    @Override
    public boolean shouldExecute() {
        List list = this.entity.world.getEntitiesWithinAABB(EntityCreeper.class, this.entity.getEntityBoundingBox().grow(this.distance, 3, this.distance),
                Predicates.and(EntitySelectors.CAN_AI_TARGET, this.canBeSeenSelector));

        if (list.isEmpty()) {
            return false;
        } else {
            this.creeper = null;
            this.listSize = list.size();

            for (int cr = 0; cr < this.listSize; cr++) {
                this.creeper = (EntityCreeper) list.get(cr);

                if (this.creeper.getCreeperState() >= 0) {
                    if (this.entity.getDistance(creeper) < this.minDistToCharged * this.minDistToCharged) {
                        return moveAway(16, 7);
                    }
                } else if (this.entity.getDistance(creeper) < this.minDist * this.minDist) {
                    return moveAway(this.minDist, this.minDist);
                }
            }
        }
        return false;
    }

    protected boolean moveAway(int xz, int y) {
        Vec3d vec3d = RandomPositionGenerator.findRandomTargetBlockAwayFrom(this.entity, xz, y, new Vec3d(this.creeper.posX, this.creeper.posY, this.creeper.posZ));

        if (vec3d != null) {
            if (this.creeper.getDistanceSq(vec3d.x, vec3d.y, vec3d.z) > this.creeper.getDistanceSq(this.entity)) {
                this.pathEntity = this.entityPathNavigate.getPathToXYZ(vec3d.x, vec3d.y, vec3d.z);

                return this.pathEntity != null;
            }
        }
        return false;
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    @Override
    public boolean shouldContinueExecuting() {
        return !this.entityPathNavigate.noPath();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
    public void startExecuting() {
        this.entityPathNavigate.setPath(this.pathEntity, regSpeed);
    }

    /**
     * Resets the task
     */
    @Override
    public void resetTask() {
        this.creeper = null;
    }

    /**
     * Updates the task
     */
    @Override
    public void updateTask() {
        if (this.entity.getDistance(this.creeper) < 49) {
            this.entity.getNavigator().setSpeed(this.sprintSpeed);
        } else {
            this.entity.getNavigator().setSpeed(this.regSpeed);
        }
    }
}

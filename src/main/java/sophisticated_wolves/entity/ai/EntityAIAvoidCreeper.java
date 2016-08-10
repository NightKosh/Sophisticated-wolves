package sophisticated_wolves.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathNavigate;
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

    /**
     * The PathNavigate of our entity
     */
    private PathNavigate entityPathNavigate;

    /**
     * The class of the entity we should avoid
     */

    public EntityAIAvoidCreeper(EntityCreature entity, int distance, int minDistToExplode, int minDist, double regSpeed, double sprintSpeed) {
        this.entity = entity;
        this.distance = distance;
        this.minDistToCharged = minDistToExplode;
        this.minDist = minDist;
        this.regSpeed = regSpeed;
        this.sprintSpeed = sprintSpeed;
        this.entityPathNavigate = entity.getNavigator();
        this.setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    @Override
    public boolean shouldExecute() {
        List list = this.entity.worldObj.getEntitiesWithinAABB(EntityCreeper.class, this.entity.getEntityBoundingBox().expand(this.distance, 3, this.distance));

        if (list.isEmpty()) {
            return false;
        }

        this.creeper = null;
        this.listSize = list.size();

        for (int cr = 0; cr < this.listSize; cr++) {
            this.creeper = (EntityCreeper) list.get(cr);

            if (this.entity.getDistanceSqToEntity(creeper) < this.minDistToCharged * this.minDistToCharged && this.creeper.getCreeperState() > 0) {
                Vec3d vec3d = RandomPositionGenerator.findRandomTargetBlockAwayFrom(this.entity, 16, 7, new Vec3d(this.creeper.posX, this.creeper.posY, this.creeper.posZ));

                if (vec3d != null) {
                    if (this.creeper.getDistanceSq(vec3d.xCoord, vec3d.yCoord, vec3d.zCoord) > this.creeper.getDistanceSqToEntity(this.entity)) {
                        this.pathEntity = this.entityPathNavigate.getPathToXYZ(vec3d.xCoord, vec3d.yCoord, vec3d.zCoord);

                        return this.pathEntity != null;
                    }
                }
            }

            if (this.entity.getDistanceSqToEntity(creeper) < this.minDist * this.minDist) {
                Vec3d vec3d = RandomPositionGenerator.findRandomTargetBlockAwayFrom(this.entity, this.minDist, this.minDist, new Vec3d(this.creeper.posX, this.creeper.posY, this.creeper.posZ));

                if (vec3d != null) {
                    if (this.creeper.getDistanceSq(vec3d.xCoord, vec3d.yCoord, vec3d.zCoord) > this.creeper.getDistanceSqToEntity(this.entity)) {
                        this.pathEntity = this.entityPathNavigate.getPathToXYZ(vec3d.xCoord, vec3d.yCoord, vec3d.zCoord);

                        return this.pathEntity != null;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    @Override
    public boolean continueExecuting() {
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
        if (this.entity.getDistanceSqToEntity(this.creeper) < 49) {
            this.entity.getNavigator().setSpeed(this.sprintSpeed);
        } else {
            this.entity.getNavigator().setSpeed(this.regSpeed);
        }
    }
}

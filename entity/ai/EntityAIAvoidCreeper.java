package sophisticated_wolves.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.Vec3;

import java.util.List;

public class EntityAIAvoidCreeper extends EntityAIBase {
    /**
     * The entity we are attached to
     */
    private EntityCreature theEntity;
    private double RegSpeed;
    private double SprintSpeed;
    private EntityCreeper CreeperEntity;
    private float Distance;
    private float minDist;
    private PathEntity pathEntity;
    private float listSize;

    /**
     * The PathNavigate of our entity
     */
    private PathNavigate entityPathNavigate;

    /**
     * The class of the entity we should avoid
     */

    public EntityAIAvoidCreeper(EntityCreature entity, float par2, float par3, double par4, double par5) {
        this.theEntity = entity;
        this.Distance = par2;
        this.minDist = par3;
        this.RegSpeed = par4;
        this.SprintSpeed = par5;
        this.entityPathNavigate = entity.getNavigator();
        this.setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    @Override
    public boolean shouldExecute() {
        List list = this.theEntity.worldObj.getEntitiesWithinAABB(EntityCreeper.class, this.theEntity.boundingBox.expand(this.Distance, 3D, this.Distance));

        if (list.isEmpty()) {
            return false;
        }

        this.CreeperEntity = null;
        this.listSize = list.size();

        for (int cr = 0; cr < this.listSize; cr++) {
            this.CreeperEntity = (EntityCreeper) list.get(cr);

            if (this.theEntity.getDistanceSqToEntity(CreeperEntity) < this.minDist * this.minDist) {
                if (this.CreeperEntity.getCreeperState() > 0) {
                    Vec3 vec3d = RandomPositionGenerator.findRandomTargetBlockAwayFrom(this.theEntity, 16, 7, this.theEntity.worldObj.getWorldVec3Pool().getVecFromPool(this.CreeperEntity.posX, this.CreeperEntity.posY, this.CreeperEntity.posZ));

                    if (vec3d != null) {
                        if (this.CreeperEntity.getDistanceSq(vec3d.xCoord, vec3d.yCoord, vec3d.zCoord) > this.CreeperEntity.getDistanceSqToEntity(this.theEntity)) {
                            this.pathEntity = this.entityPathNavigate.getPathToXYZ(vec3d.xCoord, vec3d.yCoord, vec3d.zCoord);

                            if (this.pathEntity != null) {
                                return pathEntity.isDestinationSame(vec3d);
                            }
                        }
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
        this.entityPathNavigate.setPath(this.pathEntity, RegSpeed);
    }

    /**
     * Resets the task
     */
    @Override
    public void resetTask() {
        this.CreeperEntity = null;
    }

    /**
     * Updates the task
     */
    @Override
    public void updateTask() {
        if (this.theEntity.getDistanceSqToEntity(this.CreeperEntity) < 49D) {
            this.theEntity.getNavigator().setSpeed(this.SprintSpeed);
        } else {
            this.theEntity.getNavigator().setSpeed(this.RegSpeed);
        }
    }
}

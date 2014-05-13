package sophisticated_wolves.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityAIAvoidFire extends EntityAIBase {
    private EntityCreature entity;
    private double xPath;
    private double yPath;
    private double zPath;
    private double moveSpeed;
    private double minSpeed;
    private double maxSpeed;
    private World theWorld;

    public EntityAIAvoidFire(EntityCreature entity, double par2, double par3) {
        this.entity = entity;
        this.moveSpeed = this.entity.getMoveHelper().getSpeed();
        this.minSpeed = par2;
        this.maxSpeed = par3;
        this.theWorld = this.entity.worldObj;
        this.setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    @Override
    public boolean shouldExecute() {
        if (!this.entity.onGround) {
            return false;
        }

        Vec3 vec3d = RandomPositionGenerator.findRandomTarget(entity, 10, 7);

        if (vec3d == null) {
            return false;
        } else {
            this.xPath = vec3d.xCoord;
            this.yPath = vec3d.yCoord;
            this.zPath = vec3d.zCoord;
            return this.theWorld.func_147470_e(this.entity.boundingBox.contract(0.001D, 0.001D, 0.001D));
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    @Override
    public boolean continueExecuting() {
        return !this.entity.getNavigator().noPath();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
    public void startExecuting() {
        this.entity.getNavigator().tryMoveToXYZ(this.xPath, this.yPath, this.zPath, this.moveSpeed);
    }

    @Override
    public void updateTask() {
        if (this.entity.isBurning()) {
            this.entity.getNavigator().setSpeed(this.maxSpeed);
        } else {
            this.entity.getNavigator().setSpeed(this.minSpeed);
        }
    }
}

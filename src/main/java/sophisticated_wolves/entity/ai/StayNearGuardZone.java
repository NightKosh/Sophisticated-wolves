package sophisticated_wolves.entity.ai;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import sophisticated_wolves.entity.SophisticatedWolf;
import sophisticated_wolves.util.LevelUtils;

/**
 * Sophisticated Wolves
 *
 * @author metroidfood
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.ht
 * ml)
 */
public class StayNearGuardZone extends Goal {

    private final SophisticatedWolf wolf;
    private final PathNavigation navigation;
    private final double speedModifier;

    private int timeToRecalcPath;
    private float oldWaterCost;

    public StayNearGuardZone(SophisticatedWolf wolf, double speedModifier) {
        this.wolf = wolf;
        this.navigation = wolf.getNavigation();
        this.speedModifier = speedModifier;
    }

    @Override
    public boolean canUse() {
        if (!this.wolf.isLeashed() &&
                !this.wolf.isPassenger() &&
                !this.wolf.isOrderedToSit() &&
                this.wolf.isTame() &&
                this.wolf.getWolfCommands().guardZone()) {
            var commands = this.wolf.getWolfCommands();
            return (this.wolf.getTarget() == null || this.wolf.getTarget().distanceToSqr(
                    commands.guardX(), commands.guardY(), commands.guardZ()) >= SophisticatedWolf.DISTANCE_TO_TELEPORT_TO_OWNER_SQR) &&
                    (this.wolf.getLastHurtByMob() == null || this.wolf.getLastHurtByMob().distanceToSqr(
                    commands.guardX(), commands.guardY(), commands.guardZ()) >= SophisticatedWolf.DISTANCE_TO_TELEPORT_TO_OWNER_SQR);
        }
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        if (!this.wolf.isLeashed() &&
                !this.wolf.isPassenger() &&
                !this.wolf.isOrderedToSit() &&
                this.wolf.isTame() &&
                !this.navigation.isDone() &&
                this.wolf.getWolfCommands().guardZone()) {
            var commands = this.wolf.getWolfCommands();
            return (this.wolf.getTarget() == null || this.wolf.getTarget().distanceToSqr(
                    commands.guardX(), commands.guardY(), commands.guardZ()) >= SophisticatedWolf.DISTANCE_TO_TELEPORT_TO_OWNER_SQR) &&
                    (this.wolf.getLastHurtByMob() == null || this.wolf.getLastHurtByMob().distanceToSqr(
                            commands.guardX(), commands.guardY(), commands.guardZ()) >= SophisticatedWolf.DISTANCE_TO_TELEPORT_TO_OWNER_SQR);
        }
        return false;
    }

    @Override
    public void start() {
        this.timeToRecalcPath = 0;
        this.oldWaterCost = this.wolf.getPathfindingMalus(BlockPathTypes.WATER);
        this.wolf.setPathfindingMalus(BlockPathTypes.WATER, 0);
    }

    @Override
    public void stop() {
        this.navigation.stop();
        this.wolf.setPathfindingMalus(BlockPathTypes.WATER, this.oldWaterCost);
    }

    @Override
    public void tick() {
        this.timeToRecalcPath--;
        if (this.timeToRecalcPath <= 0) {
            this.timeToRecalcPath = this.adjustedTickDelay(10);
            var commands = this.wolf.getWolfCommands();
            if (this.wolf.distanceToSqr(commands.guardX(), commands.guardY(), commands.guardZ()) >=
                    SophisticatedWolf.DISTANCE_TO_TELEPORT_TO_OWNER_SQR) {
                LevelUtils.teleportTo(this.wolf, commands.guardX(), commands.guardY(), commands.guardZ());
            } else {
                this.wolf.getNavigation().moveTo(
                        commands.guardX(), commands.guardY(), commands.guardZ(),
                        this.speedModifier);
            }
        }
    }

}

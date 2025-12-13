package sophisticated_wolves.entity.ai;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Slime;
import sophisticated_wolves.core.SWConfiguration;
import sophisticated_wolves.entity.SophisticatedWolf;

/**
 * Sophisticated Wolves
 *
 * @author metroidfood
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class FleeGoal extends AvoidCreeperGoal {

    public FleeGoal(SophisticatedWolf wolf, int distance, int minDist, double regSpeed, double sprintSpeed) {
        super(wolf, distance, minDist, regSpeed, sprintSpeed);
    }

    @Override
    public boolean canUse() {
        return this.shouldFlee() && (this.isMonsterNearby(Monster.class) || this.isMonsterNearby(Slime.class));
    }

    private boolean isMonsterNearby(Class<? extends Mob> clazz) {
        var monsters = this.wolf.level().getEntitiesOfClass(
                clazz,
                this.wolf.getBoundingBox()
                        .inflate(this.distance, 3, this.distance),
                this.canBeSeenSelector);
        for (var monster : monsters) {
            if (monster != null && this.wolf.distanceTo(monster) < this.minDist2 && moveAway(monster)) {
                this.monster = monster;
                return true;
            }
        }
        return false;
    }

    public boolean shouldFlee() {
        return this.wolf.isTame() && this.wolf.getHealth() <= SWConfiguration.WOLVES_HEALTH_FLEE.get();
    }

}

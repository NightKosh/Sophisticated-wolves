package sophisticated_wolves.entity.ai;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Creeper;
import sophisticated_wolves.entity.SophisticatedWolf;

/**
 * Sophisticated Wolves
 *
 * @author metroidfood
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class CreeperAlertGoal extends Goal {

    protected final SophisticatedWolf wolf;
    protected final int distance;
    protected int ticks;

    public CreeperAlertGoal(SophisticatedWolf wolf, int distance) {
        this.wolf = wolf;
        this.distance = distance;
    }

    @Override
    public boolean canUse() {
        return this.wolf.isTame() && !this.wolf.level()
                .getEntitiesOfClass(
                        Creeper.class,
                        this.wolf.getBoundingBox().expandTowards(distance, 5, distance))
                .isEmpty();
    }

    @Override
    public boolean canContinueToUse() {
        return ticks < 50;// 2.5 seconds
    }

    @Override
    public void start() {
        this.ticks = 0;
        this.wolf.playSound(this.wolf.getSoundVariant().value().growlSound().value(), this.wolf.getSoundVolume(),
                (this.wolf.getRandom().nextFloat() - this.wolf.getRandom().nextFloat()) * 0.2F + 1);
    }

    @Override
    public void tick() {
        this.ticks++;
    }
}

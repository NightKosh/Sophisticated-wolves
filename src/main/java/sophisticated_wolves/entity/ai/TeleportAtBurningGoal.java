package sophisticated_wolves.entity.ai;

import sophisticated_wolves.entity.SophisticatedWolf;
import sophisticated_wolves.util.LevelUtils;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class TeleportAtBurningGoal extends TeleportAtGoal {

    private static final double DEFAULT_SPEED_MODIFIER = 1;
    private static final double MAX_SPEED_MODIFIER = 1.4;

    public TeleportAtBurningGoal(SophisticatedWolf wolf) {
        super(wolf);
    }

    @Override
    protected boolean customConditions() {
        return this.wolf.isOnFire() && LevelUtils.containsAnyInFire(
                this.level, this.wolf.getBoundingBox().contract(0.001, 0.001, 0.001));
    }

    @Override
    public void tick() {
        super.tick();
        this.wolf.getNavigation().setSpeedModifier(MAX_SPEED_MODIFIER);
    }

    @Override
    public void stop() {
        super.stop();
        this.wolf.getNavigation().setSpeedModifier(DEFAULT_SPEED_MODIFIER);
    }

}

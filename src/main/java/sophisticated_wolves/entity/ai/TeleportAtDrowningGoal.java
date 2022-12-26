package sophisticated_wolves.entity.ai;

import sophisticated_wolves.entity.SophisticatedWolf;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class TeleportAtDrowningGoal extends TeleportAtGoal {

    public TeleportAtDrowningGoal(SophisticatedWolf wolf) {
        super(wolf);
    }

    @Override
    protected boolean customConditions() {
        return this.wolf.isInWater();
    }

}

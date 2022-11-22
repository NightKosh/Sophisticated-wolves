package sophisticated_wolves.entity.ai;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import sophisticated_wolves.entity.SophisticatedWolf;

import javax.annotation.Nullable;
import java.util.EnumSet;

/**
 * Sophisticated Wolves
 *
 * @author metroidfood
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class SWBegGoal extends Goal {

    private final SophisticatedWolf wolf;
    private final Level level;
    private final float lookDistance;
    private final TargetingConditions begTargeting;

    @Nullable
    private Player player;
    private int lookTime;

    public SWBegGoal(SophisticatedWolf wolf, float lookDistance) {
        this.wolf = wolf;
        this.level = wolf.getLevel();
        this.lookDistance = lookDistance * lookDistance;
        this.begTargeting = TargetingConditions.forNonCombat().range(lookDistance);
        this.setFlags(EnumSet.of(Goal.Flag.LOOK));
    }

    /**
     * Returns whether the Goal should begin execution.
     */
    @Override
    public boolean canUse() {
        this.player = this.level.getNearestPlayer(this.begTargeting, this.wolf);
        return this.player != null && this.playerHoldingInteresting(this.player);
    }

    /**
     * Returns whether an in-progress Goal should continue executing
     */
    @Override
    public boolean canContinueToUse() {
        if (this.player != null && this.player.isAlive()) {
            if (this.wolf.distanceToSqr(this.player) <= this.lookDistance) {
                return this.lookTime > 0 && this.playerHoldingInteresting(this.player);
            }
        }
        return false;
    }

    /**
     * Execute a one shot Goal or start executing a continuous Goal
     */
    @Override
    public void start() {
        this.wolf.setIsInterested(true);
        this.lookTime = this.adjustedTickDelay(40 + this.wolf.getRandom().nextInt(40));
    }

    /**
     * Resets Goal
     */
    public void stop() {
        this.wolf.setIsInterested(false);
        this.player = null;
    }

    /**
     * Updates Goal
     */
    @Override
    public void tick() {
        this.wolf.getLookControl().setLookAt(
                this.player.getX(),
                this.player.getEyeY(),
                this.player.getZ(),
                10,
                this.wolf.getMaxHeadXRot());
        this.lookTime--;
    }

    private boolean playerHoldingInteresting(Player player) {
        for (InteractionHand interactionhand : InteractionHand.values()) {
            var stack = player.getItemInHand(interactionhand);

            if (!this.wolf.isTame() && Items.BONE.equals(stack.getItem())) {
                return true;
            } else {
                return this.wolf.isTame() && this.wolf.isInterestingItem(stack);
            }
        }

        return false;
    }

}

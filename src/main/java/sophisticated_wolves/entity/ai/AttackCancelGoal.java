package sophisticated_wolves.entity.ai;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;

/**
 * Sophisticated Wolves
 *
 * @author metroidfood
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class AttackCancelGoal extends Goal {

    private final TamableAnimal pet;
    private final PathNavigation petPathfinder;

    private LivingEntity owner;
    private int sneakCounter;

    public AttackCancelGoal(TamableAnimal animal) {
        this.pet = animal;
        this.petPathfinder = animal.getNavigation();
        this.sneakCounter = 0;
    }

    /**
     * Returns whether the Goal should begin execution.
     */
    @Override
    public boolean canUse() {
        if (!this.pet.isTame()) {
            return false;
        }

        var owner = this.pet.getOwner();
        if (owner == null ||
                this.pet.isOrderedToSit() ||
                !this.pet.isOnGround()) {
            return false;
        }

        if (owner.isShiftKeyDown()) {
            this.owner = owner;
            return true;
        }
        return false;
    }

    /**
     * Returns whether an in-progress Goal should continue executing
     */
    @Override
    public boolean canContinueToUse() {
        return this.owner.isShiftKeyDown() && !this.pet.isOrderedToSit();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
    public void start() {
        this.petPathfinder.stop();
    }

    /**
     * Resets the task
     */
    @Override
    public void stop() {
        this.petPathfinder.stop();
        this.sneakCounter = 0;
    }

    /**
     * Updates the task
     */
    @Override
    public void tick() {
        //adds to sneakCounter if owner is sneaking
        if (this.owner != null) {
            if (this.owner.isShiftKeyDown()) {
                this.sneakCounter++;
                if (this.sneakCounter > 30) {
                    this.petPathfinder.stop();
                    this.pet.setTarget(null);
                    this.pet.setLastHurtByMob(null);

                    this.owner.setLastHurtByMob(null);
                    this.sneakCounter = 0;
                }
            } else {
                this.sneakCounter = 0;
            }
        }
    }

}

package sophisticated_wolves.entity.ai;

import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import sophisticated_wolves.entity.SophisticatedWolf;

/**
 * Sophisticated Wolves
 *
 * @author metroidfood
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class MoveCancelAtMiningGoal extends Goal {

    private final SophisticatedWolf pet;
    private final PathNavigation pathNavigation;
    private final float dist;

    private LivingEntity owner;

    public MoveCancelAtMiningGoal(SophisticatedWolf entity, float distance) {
        this.pet = entity;
        this.owner = entity.getOwner();
        this.pathNavigation = entity.getNavigation();
        this.dist = distance * distance * 4;
    }

    /**
     * Returns whether the Goal should begin execution.
     */
    @Override
    public boolean canUse() {
        if (!this.pet.isTame() ||
                this.pet.isOrderedToSit() ||
                !this.pet.onGround() ||
                this.pet.isInLove() ||
                this.pet.getTarget() != null) {
            return false;
        }

        if (this.owner == null) {
            this.owner = this.pet.getOwner();
        }
        if (this.owner == null || this.pet.distanceTo(this.owner) > this.dist) {
            return false;
        }
        return this.owner.swinging && this.pet.followOwner() && isDiggerItem(this.owner);
    }

    private static boolean isDiggerItem(LivingEntity owner) {
        var stack = owner.getMainHandItem();
        return stack.is(ItemTags.PICKAXES) ||
                stack.is(ItemTags.SHOVELS) ||
                stack.is(ItemTags.AXES) ||
                stack.is(ItemTags.HOES);
    }

    /**
     * Returns whether an in-progress Goal should continue executing
     */
    @Override
    public boolean canContinueToUse() {
        return !this.pet.isOrderedToSit() &&
                !this.pet.isInLove() &&
                this.pet.getTarget() == null &&
                this.owner.swinging &&
                isDiggerItem(this.owner);
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
    public void start() {
        this.pathNavigation.stop();
    }

    @Override
    public void tick() {
        this.pathNavigation.stop();
    }

}

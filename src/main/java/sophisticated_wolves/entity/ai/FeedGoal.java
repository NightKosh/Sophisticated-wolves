package sophisticated_wolves.entity.ai;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import sophisticated_wolves.FoodHelper;
import sophisticated_wolves.SWConfiguration;
import sophisticated_wolves.entity.SophisticatedWolf;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class FeedGoal extends Goal {

    private final Level level;
    private final SophisticatedWolf pet;

    protected ItemEntity foodEntity;

    public FeedGoal(SophisticatedWolf pet) {
        this.pet = pet;
        this.level = pet.getLevel();
    }

    /**
     * Returns whether the Goal should begin execution.
     */
    @Override
    public boolean canUse() {
        if (!this.pet.isTame() ||
                this.pet.isInSittingPose() ||
                this.pet.getHealth() >= SWConfiguration.wolvesHealthTamed) {
            return false;
        } else {
            var foodList = this.level.getEntitiesOfClass(ItemEntity.class,
                    new AABB(this.pet.getX() - 30, this.pet.getY() - 30, this.pet.getZ() - 30,
                            this.pet.getX() + 30, this.pet.getY() + 30, this.pet.getZ() + 30));
            for (ItemEntity foodEntity : foodList) {
                ItemStack stack = foodEntity.getItem();
                if ((FoodHelper.isFoodItem(stack) || FoodHelper.isBone(stack)) &&
                        FoodHelper.isWolfFood(pet, stack)) {
                    this.foodEntity = foodEntity;
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns whether an in-progress Goal should continue executing
     */
    @Override
    public boolean canContinueToUse() {
        return this.pet.getHealth() < SWConfiguration.wolvesHealthTamed && this.foodEntity != null;
    }

    /**
     * Updates Goal
     */
    @Override
    public void tick() {
        if (this.foodEntity != null) {
            this.pet.getNavigation().moveTo(this.foodEntity, 1);
            if (this.pet.distanceTo(this.foodEntity) <= 1) {
                this.pet.getLookControl().setLookAt(
                        this.foodEntity.getX(), this.foodEntity.getY(), this.foodEntity.getZ(),
                        0.25F, 0.25F);
                this.pet.heal(FoodHelper.getHealPoints(this.foodEntity.getItem()));
                this.foodEntity.getItem().shrink(1);
                if (this.foodEntity.getItem().isEmpty() || this.pet.getHealth() >= SWConfiguration.wolvesHealthTamed) {
                    if (this.foodEntity.getItem().isEmpty()) {
                        //TODO .setDead() ???
                        this.foodEntity.remove(Entity.RemovalReason.DISCARDED);
                    }
                    this.foodEntity = null;
                    this.pet.getNavigation().stop();
                }
            }
        }
    }

    /**
     * Resets Goal
     */
    @Override
    public void stop() {
        this.foodEntity = null;
        this.pet.getNavigation().stop();
    }

}
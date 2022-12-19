package sophisticated_wolves.entity.ai;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.phys.AABB;
import sophisticated_wolves.core.SWConfiguration;
import sophisticated_wolves.entity.SophisticatedWolf;
import sophisticated_wolves.util.FoodUtils;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class FeedGoal extends AFeedGoal<ItemEntity> {

    public FeedGoal(SophisticatedWolf wolf) {
        super(wolf);
    }

    @Override
    protected boolean findFeedObject() {
        var foodList = this.level.getEntitiesOfClass(ItemEntity.class,
                new AABB(this.pet.getX() - 30, this.pet.getY() - 30, this.pet.getZ() - 30,
                        this.pet.getX() + 30, this.pet.getY() + 30, this.pet.getZ() + 30));
        for (ItemEntity foodEntity : foodList) {
            var stack = foodEntity.getItem();
            if ((FoodUtils.isFoodItem(stack) || FoodUtils.isBone(stack)) &&
                    FoodUtils.isWolfFood(pet, stack) &&
                    //wolf should be in guard mode or item should be closer than teleportation range
                    (this.pet.getWolfCommands().guardZone() ||
                        this.owner.distanceToSqr(foodEntity) < SophisticatedWolf.DISTANCE_TO_TELEPORT_TO_OWNER_SQR)) {
                this.feedObject = foodEntity;
                return true;
            }
        }
        return false;
    }

    @Override
    protected boolean ifFeedObjectAlive() {
        return this.feedObject.isAlive();
    }

    @Override
    protected double getFeedObjectPosX() {
        return this.feedObject.getX();
    }

    @Override
    protected double getFeedObjectPosY() {
        return this.feedObject.getY();
    }

    @Override
    protected double getFeedObjectPosZ() {
        return this.feedObject.getZ();
    }

    @Override
    protected void moveTo() {
        this.pet.getNavigation().moveTo(this.feedObject, 1);
    }

    @Override
    protected void feed() {
        this.pet.heal(FoodUtils.getHealPoints(this.feedObject.getItem()));
        this.feedObject.getItem().shrink(1);
        if (this.feedObject.getItem().isEmpty() || this.pet.getHealth() >= SWConfiguration.WOLVES_HEALTH_TAMED.get()) {
            if (this.feedObject.getItem().isEmpty()) {
                this.feedObject.remove(Entity.RemovalReason.DISCARDED);
            }
            this.stop();
        }
    }

}

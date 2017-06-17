package sophisticated_wolves.entity.ai;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import sophisticated_wolves.FoodHelper;

import java.util.List;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class AIFeed extends EntityAIBase {

    private EntityTameable pet;
    private World world;
    protected EntityItem foodEntity;

    public AIFeed(EntityTameable pet) {
        this.pet = pet;
        this.world = pet.world;
    }

    @Override
    public boolean shouldExecute() {
        if (this.pet.isSitting() || this.pet.getHealth() >= 20) {
            return false;
        } else {
            List<EntityItem> foodList = this.world.getEntitiesWithinAABB(EntityItem.class,
                    new AxisAlignedBB(this.pet.posX - 30, this.pet.posY - 30, this.pet.posZ - 30,
                            this.pet.posX + 30, this.pet.posY + 30, this.pet.posZ + 30));
            for (EntityItem foodEntity : foodList) {
                ItemStack stack = foodEntity.getEntityItem();
                if (FoodHelper.isFoodItem(stack) && FoodHelper.isWolfFood(stack)) {
                    this.foodEntity = foodEntity;
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean continueExecuting() {
        return this.pet.getHealth() < 20 && this.foodEntity != null;
    }

    @Override
    public void updateTask() {
        if (this.foodEntity != null) {
            this.pet.getNavigator().setPath(this.pet.getNavigator().getPathToXYZ(this.foodEntity.posX, this.foodEntity.posY, this.foodEntity.posZ), 1);
            if (this.pet.getDistanceSqToEntity(this.foodEntity) <= 0.8) {
                this.pet.heal(FoodHelper.getHealPoints(this.foodEntity.getEntityItem()));
                this.foodEntity.getEntityItem().shrink(1);
                if (this.foodEntity.getEntityItem().isEmpty()) {
                    this.foodEntity.setDead();
                    this.foodEntity = null;
                    this.pet.getNavigator().clearPathEntity();
                }
            }
        }
    }
}

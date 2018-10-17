package sophisticated_wolves.entity.ai;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import sophisticated_wolves.FoodHelper;
import sophisticated_wolves.SWConfiguration;
import sophisticated_wolves.entity.EntitySophisticatedWolf;

import java.util.List;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class AIFeed extends EntityAIBase {

    private EntitySophisticatedWolf pet;
    private World world;
    protected EntityItem foodEntity;

    public AIFeed(EntitySophisticatedWolf pet) {
        this.pet = pet;
        this.world = pet.world;
    }

    @Override
    public boolean shouldExecute() {
        if (!this.pet.isTamed() || this.pet.isSitting() || this.pet.getHealth() >= SWConfiguration.wolvesHealthTamed) {
            return false;
        } else {
            List<EntityItem> foodList = this.world.getEntitiesWithinAABB(EntityItem.class,
                    new AxisAlignedBB(this.pet.posX - 30, this.pet.posY - 30, this.pet.posZ - 30,
                            this.pet.posX + 30, this.pet.posY + 30, this.pet.posZ + 30));
            for (EntityItem foodEntity : foodList) {
                ItemStack stack = foodEntity.getItem();
                if ((FoodHelper.isFoodItem(stack) || FoodHelper.isBone(stack)) && FoodHelper.isWolfFood(pet, stack)) {
                    this.foodEntity = foodEntity;
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean shouldContinueExecuting() {
        return this.pet.getHealth() < SWConfiguration.wolvesHealthTamed && this.foodEntity != null;
    }

    @Override
    public void updateTask() {
        if (this.foodEntity != null) {
            this.pet.getNavigator().setPath(this.pet.getNavigator().getPathToXYZ(this.foodEntity.posX, this.foodEntity.posY, this.foodEntity.posZ), 1);
            if (this.pet.getDistance(this.foodEntity) <= 1) {
                this.pet.getLookHelper().setLookPosition(this.foodEntity.posX, this.foodEntity.posY, this.foodEntity.posZ, 0.25F, 0.25F);
                this.pet.heal(FoodHelper.getHealPoints(this.foodEntity.getItem()));
                this.foodEntity.getItem().shrink(1);
                if (this.foodEntity.getItem().isEmpty() || this.pet.getHealth() >= SWConfiguration.wolvesHealthTamed) {
                    if (this.foodEntity.getItem().isEmpty()) {
                        this.foodEntity.setDead();
                    }
                    this.foodEntity = null;
                    this.pet.getNavigator().clearPath();
                }
            }
        }
    }

    @Override
    public void resetTask() {
        this.foodEntity = null;
        this.pet.getNavigator().clearPath();
    }
}

package sophisticated_wolves.entity.ai;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.phys.AABB;
import sophisticated_wolves.entity.SophisticatedWolf;
import sophisticated_wolves.entity.WolfTargets;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

/**
 * Sophisticated Wolves
 *
 * @author metroidfood
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class SWNearestAttackableTargetGoal extends TargetGoal {

    protected final int randomInterval;

    @Nullable
    protected LivingEntity target;
    protected TargetingConditions targetConditions;

    public SWNearestAttackableTargetGoal(SophisticatedWolf wolf) {
        this(wolf, false, false, null);
    }

    public SWNearestAttackableTargetGoal(
            SophisticatedWolf wolf, boolean mustSee, boolean mustReach, @Nullable Predicate<LivingEntity> selector) {
        super(wolf, mustSee, mustReach);
        this.randomInterval = reducedTickDelay(10);
        this.targetConditions = TargetingConditions.forCombat().range(this.getFollowDistance()).selector(selector);
        this.setFlags(EnumSet.of(Goal.Flag.TARGET));
    }

    @Override
    public boolean canUse() {
        if (this.randomInterval > 0 && this.mob.getRandom().nextInt(this.randomInterval) != 0) {
            return false;
        } else {
            return this.findTarget() != null;
        }
    }

    @Override
    public void start() {
        this.mob.setTarget(this.target);
        super.start();
    }

    protected LivingEntity findTarget() {
        var wolfTargets = ((SophisticatedWolf) this.mob).getWolfTargets();
        var target = findTarget(null, wolfTargets.attackSkeletons(), WolfTargets.SKELETONS_CLASSES);
        target = findTarget(target, wolfTargets.attackZombies(), WolfTargets.ZOMBIES_CLASSES);
        target = findTarget(target, wolfTargets.attackSpiders(), WolfTargets.SPIDERS_CLASSES);
        target = findTarget(target, wolfTargets.attackSlimes(), WolfTargets.SLIME_CLASSES);
        target = findTarget(target, wolfTargets.attackSlimes(), WolfTargets.SLIME_CLASSES);
        target = findTarget(target, wolfTargets.attackNether(), WolfTargets.NETHER_CLASSES);
        target = findTarget(target, wolfTargets.attackRaider(), WolfTargets.RAIDERS_CLASSES);

        this.target = target;
        return target;
    }

    protected LivingEntity findTarget(LivingEntity previousTarget, boolean shouldAttack, List<Class<? extends LivingEntity>> mobClasses) {
        if (previousTarget == null && shouldAttack) {
            return findTarget(mobClasses);
        }
        return previousTarget;
    }

    protected LivingEntity findTarget(List<Class<? extends LivingEntity>> classes) {
        for (var clazz : classes) {
            var mob = this.mob.level().getNearestEntity(
                    this.mob.level().getEntitiesOfClass(
                            clazz,
                            this.getTargetSearchArea(this.getFollowDistance()),
                            (x) -> true),
                    this.targetConditions,
                    this.mob,
                    this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
            if (mob != null) {
                return mob;
            }
        }
        return null;
    }

    protected AABB getTargetSearchArea(double followDistance) {
        return this.mob.getBoundingBox().inflate(followDistance, 10, followDistance);
    }

}
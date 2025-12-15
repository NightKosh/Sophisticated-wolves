package sophisticated_wolves.entity.ai;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;
import net.minecraft.world.entity.monster.skeleton.AbstractSkeleton;
import net.minecraft.world.entity.monster.spider.Spider;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.phys.AABB;
import sophisticated_wolves.entity.SophisticatedWolf;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;

/**
 * Sophisticated Wolves
 *
 * @author metroidfood
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class SWNearestAttackableTargetGoal extends TargetGoal {

    // all skeletons: normal, wither & stray
    public static final List<Class<? extends LivingEntity>> SKELETONS_CLASSES = List.of(AbstractSkeleton.class);
    // zombies, husks, drowned, zombie villagers & zombie piglins
    public static final List<Class<? extends LivingEntity>> ZOMBIES_CLASSES = List.of(Zombie.class);
    // spiders, cave spiders, silverfishes & endermites
    public static final List<Class<? extends LivingEntity>> SPIDERS_CLASSES = List.of(Spider.class, Silverfish.class, Endermite.class);
    // slimes & magma cubes
    public static final List<Class<? extends LivingEntity>> SLIME_CLASSES = List.of(Slime.class);
    // enderman, blaze, hoglin, zoglin, brute piglin
    public static final List<Class<? extends LivingEntity>> NETHER_CLASSES = List.of(
            EnderMan.class, Blaze.class, Hoglin.class, Zoglin.class, PiglinBrute.class);

    // all raiders & witches
    public static final List<Class<? extends LivingEntity>> RAIDERS_CLASSES = List.of(Raider.class);

    protected final int randomInterval;

    @Nullable
    protected LivingEntity target;
    protected TargetingConditions targetConditions;

    public SWNearestAttackableTargetGoal(SophisticatedWolf wolf) {
        this(wolf, false, false, null);
    }

    public SWNearestAttackableTargetGoal(
            SophisticatedWolf wolf, boolean mustSee, boolean mustReach,
            TargetingConditions.@org.jspecify.annotations.Nullable Selector selector) {
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
        var wolf = ((SophisticatedWolf) this.mob);
        var target = findTarget(null, wolf.attackSkeletons(), SKELETONS_CLASSES);
        target = findTarget(target, wolf.attackZombies(), ZOMBIES_CLASSES);
        target = findTarget(target, wolf.attackSpiders(), SPIDERS_CLASSES);
        target = findTarget(target, wolf.attackSlimes(), SLIME_CLASSES);
        target = findTarget(target, wolf.attackSlimes(), SLIME_CLASSES);
        target = findTarget(target, wolf.attackNether(), NETHER_CLASSES);
        target = findTarget(target, wolf.attackRaider(), RAIDERS_CLASSES);

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
            var mob = getServerLevel(this.mob).getNearestEntity(
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
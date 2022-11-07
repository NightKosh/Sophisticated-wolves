package sophisticated_wolves.entity.ai;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.Level;
import sophisticated_wolves.entity.SophisticatedWolf;
import sophisticated_wolves.util.LevelUtils;

/**
 * Sophisticated Wolves
 *
 * @author metroidfood
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class ShakeGoal extends Goal {

    private final SophisticatedWolf wolf;
    private final Level level;
    private final PathNavigation petPathfinder;
    private boolean isPoisoned;
    private boolean isBurning;

    public ShakeGoal(SophisticatedWolf wolf) {
        this.wolf = wolf;
        this.level = wolf.getLevel();
        this.petPathfinder = this.wolf.getNavigation();
    }

    /**
     * Returns whether the Goal should begin execution.
     */
    @Override
    public boolean canUse() {
        if (!this.wolf.isTame() || !this.wolf.isOnGround()) {
            return false;
        }

        return canContinueToUse();
    }

    /**
     * Returns whether an in-progress Goal should continue executing
     */
    @Override
    public boolean canContinueToUse() {
        if (this.wolf.isWet()) {//not wet & not in water
            return false;
        }
        if (this.wolf.isOnFire() && !LevelUtils.containsAnyInFire(
                this.level, this.wolf.getBoundingBox().contract(0.001, 0.001, 0.001))) {
            this.isBurning = true;
            return true;
        }
        if (this.wolf.hasEffect(MobEffects.POISON) || this.wolf.hasEffect(MobEffects.WITHER)) {
            this.isPoisoned = true;
            return true;
        }
        return false;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
    public void start() {
        this.petPathfinder.stop();

        this.wolf.isShaking = true;
        this.wolf.shakeAnim = 0;
        this.wolf.shakeAnimO = 0;
    }

    /**
     * Resets the task
     */
    @Override
    public void stop() {
        this.isBurning = false;
        this.isPoisoned = false;
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    /**
     * Updates the task
     */
    @Override
    //TODO fix particles and shake animation
    public void tick() {
        if (this.wolf.shakeAnim == 0) {
            if (this.isBurning) {
                this.wolf.playSound(SoundEvents.WOLF_SHAKE, this.wolf.getSoundVolume(), 1);
            } else if (this.isPoisoned) {
                this.wolf.playSound(SoundEvents.FROG_LAY_SPAWN, this.wolf.getSoundVolume(), 1);
            }
        }
        if (this.wolf.shakeAnim > 0.35) {
            if (this.isBurning) {
                var moveVec = this.wolf.getDeltaMovement();

                for (int i = 0; i < 7; i++) {
                    this.level.addParticle(
                            ParticleTypes.SMOKE,
                            this.wolf.getRandomX(1),
                            this.wolf.getRandomY() + 0.5,
                            this.wolf.getRandomZ(1),
                            moveVec.x(), moveVec.y(), moveVec.z());
                }
            }
        }
        if (this.wolf.shakeAnimO >= 1.95) {
            if (this.isBurning) {
                this.wolf.clearFire();
                this.wolf.playSound(SoundEvents.FIRE_EXTINGUISH, this.wolf.getSoundVolume(), 1.6F);
                this.isBurning = false;
            } else if (this.isPoisoned) {
                this.wolf.removeEffect(MobEffects.POISON);
                this.wolf.removeEffect(MobEffects.WITHER);
                this.isPoisoned = false;
            }
        }

    }

}

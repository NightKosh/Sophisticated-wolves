package sophisticated_wolves.util;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import sophisticated_wolves.entity.SophisticatedWolf;

import java.util.stream.StreamSupport;

/**
 * This is from DoggyTalentsNext, but it is my code, so it is okay if i
 * reuse it here.
 * <p>
 * Items to utilize when dealing with various doggy stuffs
 * NOTE: Functions here will not check if the thing involved is null or not
 * ex: dog.getOwner(), that must be already done outside of here.
 *
 * @author DashieDev
 */
public class DogUtil {

    /**
     * Dog teleport with considerations from owner and always success when there is available block :
     * <p>1. Chose the best block according to {@link DogUtil#chooseSafePosNearOwner} .</p>
     * <p>2. Set dog fall distance to 0</p>
     * <p>3. Teleport</p>
     *
     * @param dog    The Dog who will teleport
     * @param radius Radius of the area to search for block to teleport
     * @return true if teleport is success.
     */
    public static boolean searchAndTeleportToOwner(SophisticatedWolf dog, int radius) {
        var target = chooseSafePosNearOwner(dog, radius);
        if (target == null) {
            return false;
        }
        teleportInternal(dog, target);

        return true;
    }

    /**
     * This function will search for all of the eligible position into a list,
     * then pick a random item and return;
     *
     * @param dog    The Dog
     * @param radius Radius of the area to search for best pos
     * @return the best block or null if no block is found.
     */
    public static BlockPos chooseSafePosNearOwner(SophisticatedWolf dog, int radius) {
        var ownerPosition = dog.getOwner().blockPosition();

        // Get BlockPos Around the owner
        var blockPoses = BlockPos.betweenClosed(
                ownerPosition.offset(-radius, -1, -radius),
                ownerPosition.offset(radius, 1, radius));

        return StreamSupport.stream(blockPoses.spliterator(), false)
                .filter(blockPos -> wantToTeleportToThePosition(dog, blockPos))
                .findAny()
                .orElse(null);
    }

    /**
     * Dog will pick randomly 10 block around the owner per call to this function
     * and teleport to one of them if it is eligible. This is the default behaviour,
     * and may require several calls to success even if there is a spot.
     *
     * @param dog    The Dog
     * @param radius Radius of the area to guess
     * @return true if success
     */
    public static boolean guessAndTryToTeleportToOwner(SophisticatedWolf dog, int radius) {
        var ownerPosition = dog.getOwner().blockPosition();

        for (int i = 0; i < 10; i++) {
            int randX = ownerPosition.getX() + dog.getRandom().nextIntBetweenInclusive(-radius, radius);
            int randY = ownerPosition.getY() + dog.getRandom().nextIntBetweenInclusive(-1, 1);
            int randZ = ownerPosition.getZ() + dog.getRandom().nextIntBetweenInclusive(-radius, radius);
            var pos = new BlockPos(randX, randY, randZ);

            if (wantToTeleportToThePosition(dog, pos)) {
                teleportInternal(dog, pos);
                return true;
            }
        }

        return false;
    }

    /**
     * this function returns whether the position is eligible for the dog or not
     * a position is eligible if :
     * <p><b>1.</b> The position is not closer than 2 blocks from the owner</p>
     * <p><b>2.</b> The block is safe for the dog to teleport, a safe block is defined by
     * the function {@link DogUtil#isTeleportSafeBlock}</p>
     * <p><b>3.</b> If the owner is sprinting then the dog must not obstruct the owner's
     * sprint path, check based on {@link DogUtil#posWillCollideWithOwnerMovingForward}</p>
     *
     * @param dog The dog
     * @param pos The position
     */
    public static boolean wantToTeleportToThePosition(SophisticatedWolf dog, BlockPos pos) {
        var ownerPosition = dog.getOwner().blockPosition();

        // Not too close to owner
        return !(Mth.abs(ownerPosition.getX() - pos.getX()) < 2 &&
                Mth.abs(ownerPosition.getZ() - pos.getZ()) < 2) &&
                isTeleportSafeBlock(dog, pos, false) &&
                // if Owner is sprinting then don't obstruct the owner sprint path
                !(dog.getOwner().isSprinting() && posWillCollideWithOwnerMovingForward(dog, pos));
    }

    /**
     * this function will check if the position the dog is trying to teleport
     * is not going to obstruct the owner when he is moving towards a direction by teleporting
     * in front of the ray that the owner is moving towards.
     * dog.getOwner() must not be null before the call or else this function will crash the game.
     *
     * @param dog The dog who is teleporting
     * @param pos The position to check
     */
    public static boolean posWillCollideWithOwnerMovingForward(SophisticatedWolf dog, BlockPos pos) {
        final var DISTANCE_AWAY = 1.5;

        var ownerPos = dog.getOwner().position();

        //get owner position and target position
        var ownerPos2d = new Vec3(ownerPos.x(), 0, ownerPos.z());
        var targetPos2d = new Vec3(pos.getX() + 0.5, 0, pos.getZ() + 0.5);

        //get owner look vector
        var a1 = dog.getOwner().getYHeadRot();
        var dx1 = -Mth.sin(a1 * Mth.DEG_TO_RAD);
        var dz1 = Mth.cos(a1 * Mth.DEG_TO_RAD);
        var ownerLookUnitVector = new Vec3(dx1, 0, dz1);

        //Check according to the below function
        var x = distanceFromPointToLineOfUnitVector2DSqr(targetPos2d, ownerPos2d, ownerLookUnitVector);

        if (x < 0 || x > DISTANCE_AWAY) {
            return false;
        }
        return true;
    }

    /**
     * Check if the dog can see the owner at that position
     *
     * @param dog The Dog
     * @param pos Block to consider
     */
    public static boolean hasLineOfSightToOwnerAtPos(SophisticatedWolf dog, BlockPos pos) {
        var pos1 = new Vec3(
                pos.getX() + 0.5,
                pos.getY() + dog.getOwner().getEyeHeight(),
                pos.getZ() + 0.5);
        var pos2 = new Vec3(
                dog.getOwner().getX(),
                dog.getOwner().getY() + dog.getOwner().getEyeHeight(),
                dog.getOwner().getZ());
        if (pos1.distanceTo(pos2) > 128) {
            return false;
        } else {
            return dog.getLevel().clip(new ClipContext(pos1, pos2, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, dog))
                    .getType() == HitResult.Type.MISS;
        }
    }

    //TODO will check is Safe Block according to the IDogAlteration
    public static boolean isTeleportSafeBlock(SophisticatedWolf dog, BlockPos pos, boolean teleportToLeaves) {
        var pathNodeType = WalkNodeEvaluator.getBlockPathTypeStatic(dog.level, pos.mutable());
        if (pathNodeType != BlockPathTypes.WALKABLE) {
            return false;
        } else {
            if (!teleportToLeaves && dog.getLevel().getBlockState(pos.below()).getBlock() instanceof LeavesBlock) {
                return false;
            } else {
                var blockPos = pos.subtract(dog.blockPosition());
                return dog.getLevel().noCollision(dog, dog.getBoundingBox().move(blockPos));
            }
        }
    }

    /**
     * <p>Let the context be in a 2d Cartesian system</p>
     * <p>Let A be a point</p>
     * <p>Let B be another point</p>
     * <p>Let v be a unit vector</p>
     * <p>Let d be a line from B and v</p>
     * <p>Let u be vector from B to A</p>
     * If Angle(u, v) < 90 this function will return the squared distance between A and d
     * else this function will return -1.
     *
     * @param A
     * @param B
     * @param v
     * @return the distance squared, -1 if Angle(u, v) > 90
     * @author DashieDev
     */
    public static double distanceFromPointToLineOfUnitVector2DSqr(Vec3 A, Vec3 B, Vec3 v) {
        var u = A.add(B.scale(-1));
        var dotUV = u.dot(v);
        if (dotUV < 0) {
            return -1;
        }
        return u.lengthSqr() - (dotUV * dotUV);
    }

    private static void teleportInternal(SophisticatedWolf dog, BlockPos target) {
        dog.fallDistance = 0; //TODO I am not sure if Sophisticated Wolves want this...
        dog.moveTo(target.getX() + 0.5, target.getY(), target.getZ() + 0.5, dog.getYRot(), dog.getXRot());
        dog.getNavigation().stop();
    }

}

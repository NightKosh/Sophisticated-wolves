package sophisticated_wolves.entity.ai;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.item.DiggerItem;
import sophisticated_wolves.entity.SophisticatedWolf;
import sophisticated_wolves.util.DogUtil;

public class SWRandomStrollGoal extends WaterAvoidingRandomStrollGoal {

    private SophisticatedWolf dog;
    private int tickCountStopMiningCautious;

    public SWRandomStrollGoal(SophisticatedWolf dog, double speedModifier) {
        super(dog, speedModifier);
        this.dog = dog;
    }

    @Override
    public boolean canUse() {
        if (this.ownerMayBeMining()) {
            this.tickCountStopMiningCautious = this.dog.tickCount + 600; // keep checking for 30 seconds
        }
        return super.canUse();
    }

    //  I can see people is going to need this cause i once got my TorchDog into lava
    //Because this reason here.., and it is also kinda annoying to see dogs going in 
    //front of owner when mining.
    //
    //  If Owner Swing with a DiggerItem in hand, go into Mining Cautious Mode, 
    //retains the mode for 30 seconds when the owner stops swining. 
    //
    //  In the mode, upon moving towards a random block (even when out of reach)
    //the dog will actively checks the path if 
    @Override
    public void tick() {
        super.tick();

        if (this.dog.tickCount < this.tickCountStopMiningCautious) {
            if (this.pathObstructOwnerMining()) {
                this.stop();
            }
        }
    }

    //TODO Mining genius : the dog will follow owner while putting torch down 
    //Closely and also run with him
    //And lead any other dogs which is close too .... 
    //TODO CHANGE : make the logic make more sense and efficent
    //Check if owner is swinging with a digger item in hand.
    private boolean ownerMayBeMining() {
        var owner = this.dog.getOwner();
        if (owner == null) {
            return false;
        } else {
            return owner.swinging &&
                    owner.getMainHandItem().getItem() instanceof DiggerItem;
        }
    }

    /**
     * Check if the forward nodes in dog's path is obstructing owner mining,
     * an obstructing path is defined in {@link DogUtil#posWillCollideWithOwnerMovingForward}
     */
    private boolean pathObstructOwnerMining() {
        var navigation = this.dog.getNavigation();
        var path = navigation.getPath();
        if (path != null) {
            //Iterate through the next 5 blocks of the path and check if obstruct owner.
            int nodeIndex = path.getNextNodeIndex();
            int endIndex = Mth.clamp(nodeIndex + 5, nodeIndex, path.getNodeCount() - 1);
            for (int i = nodeIndex; i < endIndex; i++) {
                boolean flag = DogUtil.posWillCollideWithOwnerMovingForward(dog, path.getNodePos(i));

                if (flag) {
                    return true;
                }
            }
        }

        return false;
    }

}

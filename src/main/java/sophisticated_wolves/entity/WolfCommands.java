package sophisticated_wolves.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import sophisticated_wolves.util.CompoundTagUtils;

/**
 * Sophisticated Wolves
 *
 * @author metroidfood
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public record WolfCommands(boolean followOwner, boolean guardZone, Integer guardX, Integer guardY, Integer guardZ) {

    public WolfCommands() {
        this(true, false, null, null, null);
    }

    public WolfCommands(boolean followOwner, boolean guardZone, BlockPos blockPos) {
        this(followOwner, guardZone, blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }

    public static CompoundTag toTag(WolfCommands wolfCommands) {
        var tag = new CompoundTag();
        wolfCommands.saveData(tag);
        return tag;
    }

    public void saveData(CompoundTag tag) {
        var commands = new CompoundTag();
        commands.putBoolean("FollowOwner", this.followOwner);
        commands.putBoolean("GuardZone", this.guardZone);
        CompoundTagUtils.putInt(commands, "GuardX", this.guardX);
        CompoundTagUtils.putInt(commands, "GuardY", this.guardY);
        CompoundTagUtils.putInt(commands, "GuardZ", this.guardZ);
        tag.put("WolfCommands", commands);
    }

    public static WolfCommands getFromTag(CompoundTag tag) {
        if (tag.contains("WolfCommands")) {
            var commands = tag.getCompound("WolfCommands");
            return new WolfCommands(
                    CompoundTagUtils.getFromTag(commands, "FollowOwner"),
                    CompoundTagUtils.getFromTag(commands, "GuardZone"),
                    CompoundTagUtils.getFromIntTag(commands, "GuardX"),
                    CompoundTagUtils.getFromIntTag(commands, "GuardY"),
                    CompoundTagUtils.getFromIntTag(commands, "GuardZ"));
        }
        return new WolfCommands();
    }

    public void saveData(FriendlyByteBuf buffer) {
        buffer.writeBoolean(this.followOwner);
        buffer.writeBoolean(this.guardZone);
        buffer.writeInt(this.guardX == null ? 0 : this.guardX);
        buffer.writeInt(this.guardY == null ? 0 : this.guardY);
        buffer.writeInt(this.guardZ == null ? 0 : this.guardZ);
    }

    public static WolfCommands getFromByteBuf(FriendlyByteBuf buffer) {
        return new WolfCommands(
                buffer.readBoolean(),
                buffer.readBoolean(),
                buffer.readInt(),
                buffer.readInt(),
                buffer.readInt());
    }

}

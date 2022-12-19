package sophisticated_wolves.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;

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

    public void saveData(CompoundTag tag) {
        var commands = new CompoundTag();
        commands.putBoolean("FollowOwner", this.followOwner);
        commands.putBoolean("GuardZone", this.guardZone);
        putInt(commands, "GuardX", this.guardX);
        putInt(commands, "GuardY", this.guardY);
        putInt(commands, "GuardZ", this.guardZ);
        tag.put("WolfCommands", commands);
    }

    public static WolfCommands getFromTag(CompoundTag tag) {
        if (tag.contains("WolfCommands")) {
            var commands = tag.getCompound("WolfCommands");
            return new WolfCommands(
                    getFromTag(commands, "FollowOwner"),
                    getFromTag(commands, "GuardZone"),
                    getFromIntTag(commands, "GuardX"),
                    getFromIntTag(commands, "GuardY"),
                    getFromIntTag(commands, "GuardZ"));
        }
        return new WolfCommands();
    }

    public void saveData(FriendlyByteBuf buffer) {
        buffer.writeBoolean(this.followOwner);
        buffer.writeBoolean(this.guardZone);
    }

    public static WolfCommands getFromByteBuf(FriendlyByteBuf buffer) {
        return new WolfCommands(
                buffer.readBoolean(),
                buffer.readBoolean(),
                buffer.readInt(),
                buffer.readInt(),
                buffer.readInt());
    }

    private static boolean getFromTag(CompoundTag tag, String name) {
        return tag.contains(name) && tag.getBoolean(name);
    }

    private static Integer getFromIntTag(CompoundTag tag, String name) {
        return tag.contains(name) ? tag.getInt(name) : null;
    }

    private static void putInt(CompoundTag tag, String name, Integer value) {
        if (value != null) {
            tag.putInt(name, value);
        }
    }

}

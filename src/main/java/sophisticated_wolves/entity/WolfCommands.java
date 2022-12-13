package sophisticated_wolves.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;

/**
 * Sophisticated Wolves
 *
 * @author metroidfood
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public record WolfCommands(boolean followOwner, boolean guardZone) {

    public WolfCommands() {
        this(true, false);
    }

    public void saveData(CompoundTag tag) {
        var commands = new CompoundTag();
        commands.putBoolean("FollowOwner", this.followOwner);
        commands.putBoolean("GuardZone", this.guardZone);
        tag.put("WolfCommands", commands);
    }

    public static WolfCommands getFromTag(CompoundTag tag) {
        if (tag.contains("WolfCommands")) {
            var commands = tag.getCompound("WolfCommands");
            return new WolfCommands(
                    getFromTag(commands, "FollowOwner"),
                    getFromTag(commands, "GuardZone"));
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
                buffer.readBoolean());
    }

    private static boolean getFromTag(CompoundTag tag, String name) {
        return tag.contains(name) && tag.getBoolean(name);
    }

}

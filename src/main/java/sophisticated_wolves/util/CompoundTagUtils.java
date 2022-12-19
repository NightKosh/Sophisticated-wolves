package sophisticated_wolves.util;

import net.minecraft.nbt.CompoundTag;

/**
 * Sophisticated Wolves
 *
 * @author metroidfood
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class CompoundTagUtils {

    public static boolean getFromTag(CompoundTag tag, String name) {
        return tag.contains(name) && tag.getBoolean(name);
    }

    public static Integer getFromIntTag(CompoundTag tag, String name) {
        return tag.contains(name) ? tag.getInt(name) : null;
    }

    public static void putInt(CompoundTag tag, String name, Integer value) {
        if (value != null) {
            tag.putInt(name, value);
        }
    }

}

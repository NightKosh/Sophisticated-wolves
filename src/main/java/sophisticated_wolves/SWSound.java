package sophisticated_wolves;


import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class SWSound {

    private static int id;
    public static SoundEvent WHISTLE_SHORT;
    public static SoundEvent WHISTLE_LONG;

    public static final String WHISTLE_SHORT_ID = "player.whistle.short";
    public static final String WHISTLE_LONG_ID = "player.whistle.long";

    public static void registration() {
        id = SoundEvent.REGISTRY.getKeys().size();
        WHISTLE_SHORT = registration(Resources.WHISTLE_SHORT);
        WHISTLE_LONG = registration(Resources.WHISTLE_LONG);
    }

    private static SoundEvent registration(ResourceLocation resource) {
        SoundEvent sound = new SoundEvent(resource);
        SoundEvent.REGISTRY.register(id, resource, sound);
        id++;
        return sound;
    }
}

package sophisticated_wolves.core;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import sophisticated_wolves.api.ModInfo;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class SWSound {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, ModInfo.ID);

    private static final RegistryObject<SoundEvent> WHISTLE_SHORT = registerSoundEvent("player.whistle.short");
    private static final RegistryObject<SoundEvent> WHISTLE_LONG = registerSoundEvent("player.whistle.long");

    private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        return SOUND_EVENTS.register(name, () -> new SoundEvent(new ResourceLocation(ModInfo.ID, name)));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }

    public static SoundEvent getWhistleShort() {
        return WHISTLE_SHORT.get();
    }

    public static SoundEvent getWhistleLong() {
        return WHISTLE_LONG.get();
    }

}

package sophisticated_wolves.core;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import sophisticated_wolves.api.ModInfo;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class SWSound {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS_REGISTER =
            DeferredRegister.create(Registries.SOUND_EVENT, ModInfo.ID);

    private static final DeferredHolder<SoundEvent, SoundEvent> WHISTLE_SHORT = registerSoundEvent("player.whistle.short");
    private static final DeferredHolder<SoundEvent, SoundEvent> WHISTLE_LONG = registerSoundEvent("player.whistle.long");

    private static DeferredHolder<SoundEvent, SoundEvent> registerSoundEvent(String name) {
        return SOUND_EVENTS_REGISTER.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ModInfo.ID, name)));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS_REGISTER.register(eventBus);
    }

    public static SoundEvent getWhistleShort() {
        return WHISTLE_SHORT.get();
    }

    public static SoundEvent getWhistleLong() {
        return WHISTLE_LONG.get();
    }

}

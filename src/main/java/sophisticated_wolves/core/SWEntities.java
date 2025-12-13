package sophisticated_wolves.core;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import sophisticated_wolves.api.ModInfo;
import sophisticated_wolves.api.SophisticatedWolvesAPI;
import sophisticated_wolves.entity.SophisticatedWolf;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class SWEntities {

    public static final String SW_NAME = "entity.sophisticated_wolves.sophisticated_wolf";

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES_REGISTER =
            DeferredRegister.create(Registries.ENTITY_TYPE, ModInfo.ID);

    public static final DeferredHolder<EntityType<?>, EntityType<SophisticatedWolf>> SOPHISTICATED_WOLF =
            ENTITY_TYPES_REGISTER.register(SophisticatedWolvesAPI.SOPHISTICATED_WOLF_ID,
                    () -> EntityType.Builder.of(SophisticatedWolf::new, MobCategory.CREATURE)
                            .sized(0.8f, 0.6f)
                            .build(new ResourceLocation(ModInfo.ID, SophisticatedWolvesAPI.SOPHISTICATED_WOLF_ID).toString()));


    public static void register(IEventBus eventBus) {
        ENTITY_TYPES_REGISTER.register(eventBus);
    }

    public static EntityType<SophisticatedWolf> getSophisticatedWolfType() {
        return SOPHISTICATED_WOLF.get();
    }

}

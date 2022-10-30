package sophisticated_wolves.core;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import sophisticated_wolves.api.ModInfo;
import sophisticated_wolves.entity.SophisticatedWolf;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
@Mod.EventBusSubscriber(modid = ModInfo.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SWEntity {

    public static final String SW_NAME = "entity.sophisticated_wolves.sophisticated_wolf";

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES_REGISTER =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, ModInfo.ID);

    public static final RegistryObject<EntityType<SophisticatedWolf>> SOPHISTICATED_WOLF =
            ENTITY_TYPES_REGISTER.register("sophisticated_wolf",
                    () -> EntityType.Builder.of(SophisticatedWolf::new, MobCategory.CREATURE)
                            .sized(0.8f, 0.6f)
                            .build(new ResourceLocation(ModInfo.ID, "sophisticated_wolf").toString()));


    public static void register(IEventBus eventBus) {
        ENTITY_TYPES_REGISTER.register(eventBus);
    }

    @SubscribeEvent
    public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(getSophisticatedWolfType(), SophisticatedWolf.createAttributeSupplier());
    }

    public static EntityType<SophisticatedWolf> getSophisticatedWolfType() {
        return SOPHISTICATED_WOLF.get();
    }

}

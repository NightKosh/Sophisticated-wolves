package sophisticated_wolves.core;

import com.google.common.collect.ImmutableSet;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import sophisticated_wolves.api.ModInfo;
import sophisticated_wolves.api.SophisticatedWolvesAPI;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class SWVillagers {

    public static final DeferredRegister<PoiType> POI_TYPES_REGISTER =
            DeferredRegister.create(ForgeRegistries.POI_TYPES, ModInfo.ID);

    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS_REGISTER =
            DeferredRegister.create(ForgeRegistries.VILLAGER_PROFESSIONS, ModInfo.ID);

    public static final RegistryObject<PoiType> KENNEL_BLOCK_POI = POI_TYPES_REGISTER.register(
            "kennel_poi",
            () -> new PoiType(ImmutableSet.copyOf(SWBlocks.getKennel().getStateDefinition().getPossibleStates()),
                    1, 1));
    public static final RegistryObject<PoiType> DOG_BOWL_POI = POI_TYPES_REGISTER.register(
            "dog_bowl_poi",
            () -> new PoiType(ImmutableSet.copyOf(SWBlocks.getDogBowl().getStateDefinition().getPossibleStates()),
                    1, 1));

    public static final RegistryObject<VillagerProfession> ZOOLOGIST = VILLAGER_PROFESSIONS_REGISTER.register(
            SophisticatedWolvesAPI.VILLAGER_ID,
            () -> new VillagerProfession(
                    SophisticatedWolvesAPI.VILLAGER_ID,
                    x -> x.get() == KENNEL_BLOCK_POI.get(),
                    x -> x.get() == DOG_BOWL_POI.get(),
                    ImmutableSet.of(),
                    ImmutableSet.of(),
                    SoundEvents.VILLAGER_WORK_CLERIC));

    public static void register(IEventBus eventBus) {
        POI_TYPES_REGISTER.register(eventBus);
        VILLAGER_PROFESSIONS_REGISTER.register(eventBus);
    }

}

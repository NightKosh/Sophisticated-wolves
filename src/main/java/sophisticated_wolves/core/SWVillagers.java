package sophisticated_wolves.core;

import com.google.common.collect.ImmutableSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
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
            DeferredRegister.create(Registries.POINT_OF_INTEREST_TYPE, ModInfo.ID);

    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS_REGISTER =
            DeferredRegister.create(Registries.VILLAGER_PROFESSION, ModInfo.ID);

    public static final DeferredHolder<PoiType, PoiType> KENNEL_BLOCK_POI = POI_TYPES_REGISTER.register(
            "kennel_poi",
            () -> new PoiType(ImmutableSet.copyOf(SWBlocks.getKennel().getStateDefinition().getPossibleStates()),
                    1, 1));
    public static final DeferredHolder<PoiType, PoiType> DOG_BOWL_POI = POI_TYPES_REGISTER.register(
            "dog_bowl_poi",
            () -> new PoiType(ImmutableSet.copyOf(SWBlocks.getDogBowl().getStateDefinition().getPossibleStates()),
                    1, 1));

    public static final DeferredHolder<VillagerProfession, VillagerProfession> ZOOLOGIST = VILLAGER_PROFESSIONS_REGISTER.register(
            SophisticatedWolvesAPI.VILLAGER_ID,
            () -> new VillagerProfession(
                    SophisticatedWolvesAPI.VILLAGER_ID,
                    x -> x.getDelegate().is(KENNEL_BLOCK_POI),
                    x -> x.getDelegate().is(DOG_BOWL_POI),
                    ImmutableSet.of(),
                    ImmutableSet.of(),
                    SoundEvents.VILLAGER_WORK_CLERIC));

    public static void register(IEventBus eventBus) {
        POI_TYPES_REGISTER.register(eventBus);
        VILLAGER_PROFESSIONS_REGISTER.register(eventBus);
    }

}

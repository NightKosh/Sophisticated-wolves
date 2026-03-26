package sophisticated_wolves.core;

import com.google.common.collect.ImmutableSet;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import sophisticated_wolves.api.ModInfo;
import sophisticated_wolves.api.SophisticatedWolvesAPI;

import static net.minecraft.resources.Identifier.fromNamespaceAndPath;

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

    public static final ResourceKey<VillagerProfession> ZOOLOGIST_KEY =
            ResourceKey.create(Registries.VILLAGER_PROFESSION, fromNamespaceAndPath(ModInfo.ID, SophisticatedWolvesAPI.VILLAGER_ID));

    public static final DeferredHolder<PoiType, PoiType> KENNEL_BLOCK_POI = POI_TYPES_REGISTER.register(
            "kennel_poi",
            () -> new PoiType(ImmutableSet.copyOf(SWBlocks.getKennel().getStateDefinition().getPossibleStates()),
                    1, 1));

    public static final DeferredHolder<VillagerProfession, VillagerProfession> ZOOLOGIST = VILLAGER_PROFESSIONS_REGISTER.register(
            SophisticatedWolvesAPI.VILLAGER_ID,
            () -> new VillagerProfession(
                    Component.translatable("entity." + ModInfo.ID + ".villager." + SophisticatedWolvesAPI.VILLAGER_ID),
                    x -> x.getDelegate().is(KENNEL_BLOCK_POI),
                    x -> x.getDelegate().is(KENNEL_BLOCK_POI),
                    ImmutableSet.of(),
                    ImmutableSet.of(),
                    SoundEvents.VILLAGER_WORK_CLERIC,
                    Int2ObjectMap.ofEntries(
                            Int2ObjectMap.entry(
                                    1, ResourceKey.create(Registries.TRADE_SET,
                                            fromNamespaceAndPath(ModInfo.ID, SophisticatedWolvesAPI.VILLAGER_ID + "/level_1"))),
                            Int2ObjectMap.entry(
                                    2, ResourceKey.create(Registries.TRADE_SET,
                                            fromNamespaceAndPath(ModInfo.ID, SophisticatedWolvesAPI.VILLAGER_ID + "/level_2"))),
                            Int2ObjectMap.entry(
                                    3, ResourceKey.create(Registries.TRADE_SET,
                                            fromNamespaceAndPath(ModInfo.ID, SophisticatedWolvesAPI.VILLAGER_ID + "/level_3"))),
                            Int2ObjectMap.entry(
                                    4, ResourceKey.create(Registries.TRADE_SET,
                                            fromNamespaceAndPath(ModInfo.ID, SophisticatedWolvesAPI.VILLAGER_ID + "/level_4"))),
                            Int2ObjectMap.entry(
                                    5, ResourceKey.create(Registries.TRADE_SET,
                                            fromNamespaceAndPath(ModInfo.ID, SophisticatedWolvesAPI.VILLAGER_ID + "/level_5")))
                    )));

    public static void register(IEventBus eventBus) {
        POI_TYPES_REGISTER.register(eventBus);
        VILLAGER_PROFESSIONS_REGISTER.register(eventBus);
    }

}

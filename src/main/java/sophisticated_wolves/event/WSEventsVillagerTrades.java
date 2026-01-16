package sophisticated_wolves.event;

import net.minecraft.core.component.DataComponents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.animal.armadillo.Armadillo;
import net.minecraft.world.entity.animal.chicken.Chicken;
import net.minecraft.world.entity.animal.feline.Cat;
import net.minecraft.world.entity.animal.feline.Ocelot;
import net.minecraft.world.entity.animal.fox.Fox;
import net.minecraft.world.entity.animal.parrot.Parrot;
import net.minecraft.world.entity.animal.rabbit.Rabbit;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;
import sophisticated_wolves.api.ModInfo;
import sophisticated_wolves.core.SWConfiguration;
import sophisticated_wolves.core.SWItems;
import sophisticated_wolves.entity.SophisticatedWolf;
import sophisticated_wolves.item.pet_carrier.PetCarrierHelper;

import static sophisticated_wolves.SophisticatedWolvesMod.LOGGER;
import static sophisticated_wolves.core.SWVillagers.ZOOLOGIST_KEY;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
@EventBusSubscriber(modid = ModInfo.ID)
public class WSEventsVillagerTrades {

    @SubscribeEvent
    public static void addTrades(VillagerTradesEvent event) {
        if (event.getType() == ZOOLOGIST_KEY) {
            if (SWConfiguration.DEBUG_MODE.get()) {
                LOGGER.info("VillagerTradesEvent event fro zoologist villager triggered");
            }
            var trades = event.getTrades();

            trades.get(1).add((trader, entity, rand) -> getCarrierOffer(Chicken.class, 10, 15, rand));
            trades.get(1).add((trader, entity, rand) -> getCarrierOffer(Rabbit.class, 10, 15, rand));

            trades.get(2).add((trader, entity, rand) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, rand.nextInt(15, 25)),
                    new ItemStack(Items.AXOLOTL_BUCKET),
                    10, 30, 0.02F));

            trades.get(2).add((trader, entity, rand) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, rand.nextInt(15, 25)),
                    new ItemStack(Items.TADPOLE_BUCKET),
                    10, 30, 0.02F));

            trades.get(3).add((trader, entity, rand) -> getCarrierOffer(Parrot.class, 25, 35, rand));
            trades.get(3).add((trader, entity, rand) -> getCarrierOffer(Armadillo.class, 25, 35, rand));

            trades.get(4).add((trader, entity, rand) -> getCarrierOffer(Cat.class, 35, 45, rand));
            trades.get(4).add((trader, entity, rand) -> getCarrierOffer(SophisticatedWolf.class, 35, 45, rand));

            trades.get(5).add((trader, entity, rand) -> getCarrierOffer(Ocelot.class, 45, 55, rand));
            trades.get(5).add((trader, entity, rand) -> getCarrierOffer(Fox.class, 45, 55, rand));
        }
    }

    private static MerchantOffer getCarrierOffer(Class petClass, int minPrice, int maxPrice, RandomSource random) {
        return new MerchantOffer(
                new ItemCost(Items.EMERALD, random.nextInt(minPrice, maxPrice)),
                getCarrierForTrade(petClass, random),
                10, 50, 0.02F);
    }

    private static ItemStack getCarrierForTrade(Class petClass, RandomSource random) {
        var stack = new ItemStack(SWItems.getPetCarrier());

        var petCarrier = PetCarrierHelper.getPetCarrier(petClass);
        if (petCarrier != null) {
            var tags = petCarrier.getDefaultPetCarriers();
            if (tags != null) {
                stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tags.get(random.nextInt(tags.size()))));
            }
        }
        return stack;
    }

}

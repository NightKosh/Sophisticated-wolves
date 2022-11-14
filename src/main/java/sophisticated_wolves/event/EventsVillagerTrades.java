package sophisticated_wolves.event;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.animal.Ocelot;
import net.minecraft.world.entity.animal.Parrot;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import sophisticated_wolves.api.ModInfo;
import sophisticated_wolves.core.SWItems;
import sophisticated_wolves.core.SWVillagers;
import sophisticated_wolves.entity.SophisticatedWolf;
import sophisticated_wolves.item.pet_carrier.PetCarrierHelper;

import java.util.ArrayList;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
@Mod.EventBusSubscriber(modid = ModInfo.ID)
public class EventsVillagerTrades {

    @SubscribeEvent
    public static void addTrades(VillagerTradesEvent event) {
        if (event.getType() == SWVillagers.ZOOLOGIST.get()) {
            var trades = event.getTrades();

            trades.get(1).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 1),
                    new ItemStack(SWItems.getDogTag(), 5),
                    20, 8, 0.02F));
            trades.get(1).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 1),
                    new ItemStack(SWItems.getDogTreat(), 5),
                    20, 8, 0.02F));

            trades.get(2).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, rand.nextInt(8, 12)),
                    new ItemStack(SWItems.getPetCarrier(), 1),
                    10, 15, 0.02F));
            trades.get(2).add((trader, rand) -> getCarrierOffer(Chicken.class, 15, 20, rand));

            trades.get(3).add((trader, rand) -> getCarrierOffer(Rabbit.class, 15, 20, rand));
            trades.get(3).add((trader, rand) -> getCarrierOffer(Wolf.class, 20, 25, rand));

            trades.get(4).add((trader, rand) -> getCarrierOffer(Parrot.class, 25, 30, rand));
            trades.get(4).add((trader, rand) -> getCarrierOffer(Cat.class, 30, 40, rand));

            trades.get(5).add((trader, rand) -> getCarrierOffer(SophisticatedWolf.class, 30, 40, rand));
            trades.get(5).add((trader, rand) -> getCarrierOffer(Ocelot.class, 45, 55, rand));
            trades.get(5).add((trader, rand) -> getCarrierOffer(Fox.class, 45, 55, rand));
        }
    }

    private static MerchantOffer getCarrierOffer(Class petClass, int minPrice, int maxPrice, RandomSource random) {
        return new MerchantOffer(
                new ItemStack(Items.EMERALD, random.nextInt(minPrice, maxPrice)),
                getCarrierForTrade(petClass, random),
                10, 30, 0.02F);
    }

    private static ItemStack getCarrierForTrade(Class petClass, RandomSource random) {
        var stack = new ItemStack(SWItems.getPetCarrier());

        var list = new ArrayList<ItemStack>();
        var petCarrier = PetCarrierHelper.PETS_MAP.get(petClass.getSimpleName());
        if (petCarrier != null) {
            var tags = petCarrier.getDefaultPetCarriers();
            if (tags != null) {
                for (CompoundTag tag : tags) {
                    stack.setTag(tag);
                    list.add(stack);
                }
            }
        }
        return list.get(random.nextInt(list.size()));
    }

}

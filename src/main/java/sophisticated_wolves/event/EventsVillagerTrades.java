package sophisticated_wolves.event;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.Chicken;
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
import sophisticated_wolves.core.SWBlocks;
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
        if (event.getType() == SWVillagers.PET_SELLER.get()) {
            var trades = event.getTrades();

            trades.get(1).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 1),
                    new ItemStack(SWItems.getDogTag(), 5),
                    10, 8, 0.02F));
            trades.get(1).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 1),
                    new ItemStack(SWItems.getDogTreat(), 5),
                    10, 8, 0.02F));

            trades.get(2).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, rand.nextInt(3, 6)),
                    new ItemStack(SWBlocks.getDogBowl(), 1),
                    10, 8, 0.02F));
            trades.get(2).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 10),
                    new ItemStack(SWItems.getPetCarrier(), 1),
                    10, 8, 0.02F));

            trades.get(3).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, rand.nextInt(15, 20)),
                    getCarrierForTrade(Chicken.class, rand),
                    10, 8, 0.02F));
            trades.get(3).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, rand.nextInt(15, 20)),
                    getCarrierForTrade(Rabbit.class, rand),
                    10, 8, 0.02F));
            trades.get(3).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, rand.nextInt(20, 25)),
                    getCarrierForTrade(Wolf.class, rand),
                    10, 8, 0.02F));

            trades.get(4).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, rand.nextInt(25, 30)),
                    getCarrierForTrade(Parrot.class, rand),
                    10, 8, 0.02F));
            trades.get(4).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, rand.nextInt(30, 40)),
                    getCarrierForTrade(SophisticatedWolf.class, rand),
                    10, 8, 0.02F));

            trades.get(4).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, rand.nextInt(30, 40)),
                    getCarrierForTrade(Cat.class, rand),
                    10, 8, 0.02F));
            //TODO Ocelot
        }
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

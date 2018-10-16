package sophisticated_wolves.village;

import net.minecraft.entity.passive.*;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraft.village.MerchantRecipe;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import sophisticated_wolves.Resources;
import sophisticated_wolves.SWBlocks;
import sophisticated_wolves.SWItems;
import sophisticated_wolves.api.IVillagerHandler;
import sophisticated_wolves.api.pet_carrier.PetCarrier;
import sophisticated_wolves.entity.EntitySophisticatedWolf;
import sophisticated_wolves.item.pet_carrier.PetCarrierHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class VillagersHandler implements IVillagerHandler {

    public static final VillagersHandler INSTANCE = new VillagersHandler();

    public static VillagerRegistry.VillagerProfession petsSellerProfession;
    public static VillagerRegistry.VillagerCareer petsSellerCareer;

    public static void registerVillagers() {
        petsSellerProfession = new VillagerRegistry.VillagerProfession(PETS_SELLER_ID, Resources.PETS_SELLER, Resources.PETS_SELLER_ZOMBIE);
        IForgeRegistry<VillagerRegistry.VillagerProfession> villagerProfessions = ForgeRegistries.VILLAGER_PROFESSIONS;
        villagerProfessions.register(petsSellerProfession);

        petsSellerCareer = new VillagerRegistry.VillagerCareer(petsSellerProfession, PETS_SELLER_ID);
        petsSellerCareer.addTrade(1,
                new EntityVillager.ListItemForEmeralds(new ItemStack(SWItems.DOG_TAG), new EntityVillager.PriceInfo(1, 2)),
                new EntityVillager.ListItemForEmeralds(new ItemStack(SWItems.DOG_TREAT), new EntityVillager.PriceInfo(1, 3))
        );
        petsSellerCareer.addTrade(2,
                new EntityVillager.ListItemForEmeralds(new ItemStack(SWBlocks.DOG_BOWL), new EntityVillager.PriceInfo(5, 10)),
                new EntityVillager.ListItemForEmeralds(new ItemStack(SWItems.PET_CARRIER), new EntityVillager.PriceInfo(6, 12))
        );
        petsSellerCareer.addTrade(3,
                (merchant, recipeList, random) -> {
                    recipeList.add(getMerchantRecipe(EntityChicken.class, random, 13, 15));
                },
                (merchant, recipeList, random) -> {
                    recipeList.add(getMerchantRecipe(EntityRabbit.class, random, 15, 20));
                },
                (merchant, recipeList, random) -> {
                    recipeList.add(getMerchantRecipe(EntityWolf.class, random, 20, 25));
                }
        );
        petsSellerCareer.addTrade(4,
                (merchant, recipeList, random) -> {
                    recipeList.add(getMerchantRecipe(EntityParrot.class, random, 20, 25));
                },
                (merchant, recipeList, random) -> {
                    recipeList.add(getMerchantRecipe(EntityOcelot.class, random, 30, 40));
                },
                (merchant, recipeList, random) -> {
                    recipeList.add(getMerchantRecipe(EntitySophisticatedWolf.class, random, 30, 40));
                }
        );
    }

    protected static MerchantRecipe getMerchantRecipe(Class petClass, Random random, int minPrice, int maxPrice) {
        return new MerchantRecipe(new ItemStack(Items.EMERALD, MathHelper.getInt(random, minPrice, maxPrice)), getCarrierForTrade(petClass, random));
    }

    protected static ItemStack getCarrierForTrade(Class petClass, Random random) {
        ItemStack stack = new ItemStack(SWItems.PET_CARRIER);

        List<ItemStack> list = new ArrayList<>();
        PetCarrier petCarrier = PetCarrierHelper.PETS_MAP.get(petClass.getSimpleName());
        if (petCarrier != null) {
            List<NBTTagCompound> nbtList = petCarrier.getDefaultPetCarriers();
            if (nbtList != null) {
                for (NBTTagCompound nbt : nbtList) {
                    stack.setTagCompound(nbt);
                    list.add(stack);
                }
            }
        }
        return list.get(random.nextInt(list.size()));
    }

    @Override
    public VillagerRegistry.VillagerProfession getPetSellerProfession() {
        return petsSellerProfession;
    }

    @Override
    public VillagerRegistry.VillagerCareer getPetSellerCareer() {
        return petsSellerCareer;
    }

    @Override
    public String getPetsSellerName() {
        return "entity.Villager.sophisticatedwolves:pets_seller";
    }
}

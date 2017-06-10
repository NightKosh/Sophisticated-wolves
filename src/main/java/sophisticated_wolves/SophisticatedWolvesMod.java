package sophisticated_wolves;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sophisticated_wolves.api.ModInfo;
import sophisticated_wolves.api.SophisticatedWolvesAPI;
import sophisticated_wolves.entity.EntitySophisticatedWolf;
import sophisticated_wolves.item.pet_carrier.PetCarrierHelper;
import sophisticated_wolves.proxy.CommonProxy;

import java.util.Set;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
@Mod(modid = ModInfo.ID, name = ModInfo.NAME, version = ModInfo.VERSION)
public class SophisticatedWolvesMod {

    @Mod.Instance("SophisticatedWolves")
    public static SophisticatedWolvesMod instance;
    @SidedProxy(clientSide = "sophisticated_wolves.proxy.ClientProxy", serverSide = "sophisticated_wolves.proxy.CommonProxy")
    public static CommonProxy proxy;

    public static Logger logger = LogManager.getLogger(ModInfo.ID);

    public static final String SW_NAME = "SWWolf";

    public SophisticatedWolvesMod() {
        instance = this;
        SophisticatedWolvesAPI.entityHandler = new APIEntityHandler();
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        SWConfiguration.getInstance(event.getSuggestedConfigurationFile());

        MessageHandler.init();
    }

    @Mod.EventHandler
    public void load(FMLInitializationEvent event) {
        SWItems.itemsRegistration();

        Recipes.recipesRegistration();

        EntityRegistry.registerModEntity(Resources.brownWolf, EntitySophisticatedWolf.class, SW_NAME, 0, ModInfo.ID, 100, 1, true);
        if (SWConfiguration.respawningWolves) {
            Set<Biome> biomeSet = BiomeDictionary.getBiomes(BiomeDictionary.Type.FOREST);
            Biome[] biomeArray = new Biome[biomeSet.size()];
            biomeSet.toArray(biomeArray);
            EntityRegistry.addSpawn(EntitySophisticatedWolf.class, 1, 4, 4, EnumCreatureType.MONSTER, biomeArray);
        }

        proxy.registerRenderers();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        PetCarrierHelper.addPetCarriers();

        if (Loader.isModLoaded("Thaumcraft")) {
            try {
//            ThaumcraftApi.registerEntityTag(SW_NAME, new AspectList().add(Aspect.BEAST, 3).add(Aspect.EARTH, 3).add(Aspect.MIND, 3));
//
//            // items
//            ThaumcraftApi.registerObjectTag(new ItemStack(SWItems.dogTag), new AspectList().add(Aspect.BEAST, 2).add(Aspect.MIND, 2));
//            ThaumcraftApi.registerObjectTag(new ItemStack(SWItems.dogTreat), new AspectList().add(Aspect.HUNGER, 2).add(Aspect.CROP, 4)
//                    .add(Aspect.FLESH, 3).add(Aspect.LIFE, 1).add(Aspect.BEAST, 1));
            } catch (Exception e) {
                logger.log(Level.ERROR, "Thaumcraft integration error!!");
                e.printStackTrace();
            }
        }
    }
}
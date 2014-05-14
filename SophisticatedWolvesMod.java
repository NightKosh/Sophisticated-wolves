package sophisticated_wolves;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.BiomeDictionary;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sophisticated_wolves.entity.SophisticatedWolf;
import sophisticated_wolves.proxy.CommonProxy;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;


@Mod(modid = ModInfo.ID, name = ModInfo.NAME, version = ModInfo.VERSION)
public class SophisticatedWolvesMod {

    @Mod.Instance("SophisticatedWolves")
    public static SophisticatedWolvesMod instance;
    @SidedProxy(clientSide = "sophisticated_wolves.proxy.ClientProxy", serverSide = "sophisticated_wolves.proxy.CommonProxy")
    public static CommonProxy proxy;

    public static Logger logger = LogManager.getLogger(ModInfo.ID);

    private static final String SW_NAME = "SWWolf";

    public SophisticatedWolvesMod() {
        instance = this;
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        SWConfiguration.getInstance(event.getSuggestedConfigurationFile());
        SWLocalization.init();
    }

    @Mod.EventHandler
    public void load(FMLInitializationEvent event) {
        SWItems.itemsRegistration();

        Recipes.recipesRegistration();

        EntityRegistry.registerGlobalEntityID(SophisticatedWolf.class, SW_NAME, EntityRegistry.findGlobalUniqueEntityId(), 14144467, 13545366);
        if (SWConfiguration.respawningWolves) {
            EntityRegistry.addSpawn(SophisticatedWolf.class, 1, 4, 4, EnumCreatureType.monster, BiomeDictionary.getBiomesForType(BiomeDictionary.Type.FOREST));
        }

        proxy.registerRenderers();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        try {
            ThaumcraftApi.registerEntityTag(SW_NAME, new AspectList().add(Aspect.BEAST, 3).add(Aspect.EARTH, 3).add(Aspect.MIND, 3));

            // items
            ThaumcraftApi.registerObjectTag(new ItemStack(SWItems.dogTag), new AspectList().add(Aspect.BEAST, 2).add(Aspect.MIND, 2));
            ThaumcraftApi.registerObjectTag(new ItemStack(SWItems.dogTreat), new AspectList().add(Aspect.HUNGER, 2).add(Aspect.CROP, 4)
                    .add(Aspect.FLESH, 3).add(Aspect.LIFE, 1).add(Aspect.BEAST, 1));
        } catch (Exception e) {
            logger.log(Level.ERROR, "Error in thaumcraft integration");
            e.printStackTrace();
        }
    }
}
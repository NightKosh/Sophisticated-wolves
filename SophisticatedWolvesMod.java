package sophisticated_wolves;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import net.minecraft.entity.EnumCreatureType;
import net.minecraftforge.common.BiomeDictionary;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sophisticated_wolves.entity.SophisticatedWolf;
import sophisticated_wolves.proxy.CommonProxy;


@Mod(modid = ModInfo.ID, name = ModInfo.NAME, version = ModInfo.VERSION)
public class SophisticatedWolvesMod {

    @Mod.Instance("SophisticatedWolves")
    public static SophisticatedWolvesMod instance;
    @SidedProxy(clientSide = "sophisticated_wolves.proxy.ClientProxy", serverSide = "sophisticated_wolves.proxy.CommonProxy")
    public static CommonProxy proxy;

    public static Logger logger = LogManager.getLogger(ModInfo.ID);

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

        EntityRegistry.registerGlobalEntityID(SophisticatedWolf.class, "SWWolf", EntityRegistry.findGlobalUniqueEntityId(), 14144467, 13545366);
        if (SWConfiguration.respawningWolves) {
            EntityRegistry.addSpawn(SophisticatedWolf.class, 1, 4, 4, EnumCreatureType.monster, BiomeDictionary.getBiomesForType(BiomeDictionary.Type.FOREST));
        }

        proxy.registerRenderers();
    }
}
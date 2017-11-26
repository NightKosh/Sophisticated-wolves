package sophisticated_wolves;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sophisticated_wolves.api.ModInfo;
import sophisticated_wolves.api.SophisticatedWolvesAPI;
import sophisticated_wolves.compatibility.Compatibility;
import sophisticated_wolves.item.pet_carrier.PetCarrierHelper;
import sophisticated_wolves.proxy.CommonProxy;

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


    public SophisticatedWolvesMod() {
        instance = this;
        SophisticatedWolvesAPI.entityHandler = new APIEntityHandler();
        SophisticatedWolvesAPI.petCarrierHandler = PetCarrierHelper.INSTANCE;
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        SWConfiguration.getInstance(event.getSuggestedConfigurationFile());

        MessageHandler.init();

        SWTabs.registration();

        SWTileEntity.registration();
    }

    @Mod.EventHandler
    public void load(FMLInitializationEvent event) {
        Recipes.recipesRegistration();

        SWEntity.registration();

        NetworkRegistry.INSTANCE.registerGuiHandler(this, new SWGui());

        proxy.registerRenderers();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        PetCarrierHelper.INSTANCE.addPetCarriers();

        Compatibility.checkMods();
    }
}
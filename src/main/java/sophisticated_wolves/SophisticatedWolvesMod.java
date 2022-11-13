package sophisticated_wolves;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sophisticated_wolves.api.ModInfo;
import sophisticated_wolves.api.SophisticatedWolvesAPI;
import sophisticated_wolves.core.SWBlockEntities;
import sophisticated_wolves.core.SWBlocks;
import sophisticated_wolves.core.SWConfiguration;
import sophisticated_wolves.core.SWEntities;
import sophisticated_wolves.core.SWItems;
import sophisticated_wolves.core.SWMenu;
import sophisticated_wolves.core.SWMessages;
import sophisticated_wolves.core.SWSound;
import sophisticated_wolves.core.SWVillagers;
import sophisticated_wolves.item.pet_carrier.PetCarrierHelper;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
@Mod(ModInfo.ID)
public class SophisticatedWolvesMod {

    public static SophisticatedWolvesMod instance;

    public static Logger logger = LogManager.getLogger(ModInfo.ID);

    public SophisticatedWolvesMod() {
        instance = this;
        //TODO ??
        SophisticatedWolvesAPI.entityHandler = new APIEntityHandler();
        SophisticatedWolvesAPI.petCarrierHandler = PetCarrierHelper.INSTANCE;

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, SWConfiguration.SPEC, ModInfo.ID + ".toml");


        var eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        SWItems.register(eventBus);
        SWBlocks.register(eventBus);
        SWBlockEntities.register(eventBus);
        SWMenu.register(eventBus);

        SWSound.register(eventBus);
        //TODO do at post init ????
        PetCarrierHelper.INSTANCE.addPetCarriers();

        SWEntities.register(eventBus);

        SWVillagers.register(eventBus);

        eventBus.addListener(this::setup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        SWMessages.register();
    }

//TODO remove?
//    @Mod.EventHandler
//    public void postInit(FMLPostInitializationEvent event) {
//        PetCarrierHelper.INSTANCE.addPetCarriers();
//
//        Compatibility.checkMods();
//    }

}
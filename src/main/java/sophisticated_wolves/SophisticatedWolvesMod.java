package sophisticated_wolves;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sophisticated_wolves.api.ModInfo;
import sophisticated_wolves.core.*;
import sophisticated_wolves.item.pet_carrier.PetCarrierHelper;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
@Mod(ModInfo.ID)
public class SophisticatedWolvesMod {

    public static SophisticatedWolvesMod INSTANCE;

    public static final Logger LOGGER = LogManager.getLogger(ModInfo.ID);

    public SophisticatedWolvesMod(IEventBus eventBus) {
        INSTANCE = this;

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, SWConfiguration.SPEC, ModInfo.ID + ".toml");

        SWTabs.register(eventBus);
        SWItems.register(eventBus);
        SWBlocks.register(eventBus);
        SWBlockEntities.register(eventBus);
        SWMenu.register(eventBus);

        SWSound.register(eventBus);
        SWEntities.register(eventBus);
        SWVillagers.register(eventBus);

        PetCarrierHelper.addPetCarriers();
    }

//TODO remove?
//    @Mod.EventHandler
//    public void postInit(FMLPostInitializationEvent event) {
//
//        Compatibility.checkMods();
//    }

}
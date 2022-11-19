package sophisticated_wolves.core;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import sophisticated_wolves.api.ModInfo;
import sophisticated_wolves.gui.menu.DogBowlContainerMenu;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class SWMenu {

    public static final DeferredRegister<MenuType<?>> MENUS_REGISTER =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, ModInfo.ID);

    public static final RegistryObject<MenuType<DogBowlContainerMenu>> DOG_BOWL =
            registerMenuType(DogBowlContainerMenu::new, "dog_bowl");

    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerMenuType(
            IContainerFactory<T> factory, String name) {
        return MENUS_REGISTER.register(name, () -> IForgeMenuType.create(factory));
    }

    public static void register(IEventBus eventBus) {
        MENUS_REGISTER.register(eventBus);
    }

}

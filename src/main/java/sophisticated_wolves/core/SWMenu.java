package sophisticated_wolves.core;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
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
            DeferredRegister.create(Registries.MENU, ModInfo.ID);

    public static final DeferredHolder<MenuType<?>, MenuType<DogBowlContainerMenu>> DOG_BOWL =
            registerMenuType(DogBowlContainerMenu::new, "dog_bowl");

    private static <T extends AbstractContainerMenu> DeferredHolder<MenuType<?>, MenuType<T>> registerMenuType(
            IContainerFactory<T> factory, String name) {
        return MENUS_REGISTER.register(name, () -> IMenuTypeExtension.create(factory));
    }

    public static void register(IEventBus eventBus) {
        MENUS_REGISTER.register(eventBus);
    }

}

package sophisticated_wolves.api;

import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.world.World;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class SophisticatedWolvesAPI {
    
    public static EntityWolf getSophisticatedWolf(World world) {
        try {
            Class mobClass = Class.forName("sophisticated_wolves.entity.SophisticatedWolf");
            Constructor<EntityWolf> constructor = mobClass.getConstructor(World.class);
            return constructor.newInstance(new Object[]{world});
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }
}

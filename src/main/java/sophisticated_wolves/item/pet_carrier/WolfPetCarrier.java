package sophisticated_wolves.item.pet_carrier;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class WolfPetCarrier extends CustomPetCarrier {

    @Override
    public String getPetId() {
        return "Wolf";
    }

    @Override
    public EntityLiving spawnPet(World world, EntityPlayer player) {
        return new EntityWolf(world);
    }
}

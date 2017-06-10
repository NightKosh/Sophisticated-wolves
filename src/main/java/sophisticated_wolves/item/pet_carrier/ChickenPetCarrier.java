package sophisticated_wolves.item.pet_carrier;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class ChickenPetCarrier extends CustomPetCarrier {

    @Override
    public String getPetId() {
        return "Chicken";
    }

    @Override
    public EntityLiving spawnPet(World world, EntityPlayer player) {
        return new EntityChicken(world);
    }
}

package sophisticated_wolves.item.pet_carrier;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import sophisticated_wolves.api.pet_carrier.PetCarrier;

import java.util.ArrayList;
import java.util.List;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class ChickenPetCarrier extends PetCarrier {

    @Override
    public Class getPetClass() {
        return Chicken.class;
    }

    @Override
    public String getPetNameLocalizationKey() {
        return "entity.minecraft.chicken";
    }

    @Override
    public Mob spawnPet(Level level, Player player) {
        return EntityType.CHICKEN.create(level);
    }

    @Override
    public List<CompoundTag> getDefaultPetCarriers() {
        var list = new ArrayList<CompoundTag>();
        list.add(getDefaultPetCarrier(null, null));

        return list;
    }

}

package sophisticated_wolves.item.pet_carrier;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Parrot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import sophisticated_wolves.SophisticatedWolvesMod;
import sophisticated_wolves.api.pet_carrier.PetCarrier;

import java.util.ArrayList;
import java.util.List;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class ParrotPetCarrier extends PetCarrier {

    public static final int[] PARROTS_SPECIES = {0, 1, 2, 3, 4};
    //0 = red, 1 = blue, 2 = green, 3 = cyan, 4 = silver.

    @Override
    public Class getPetClass() {
        return Parrot.class;
    }

    @Override
    public String getPetNameLocalizationKey() {
        return "entity.minecraft.parrot";
    }

    @Override
    public Mob spawnPet(Level level, Player player) {
        return EntityType.PARROT.create(level);
    }

    @Override
    public List<Component> getInfo(CompoundTag infoTag) {
        if (infoTag.contains("Variant")) {
            var list = new ArrayList<Component>(1);
            list.add(Component.translatable("carrier.parrot_type")
                    .append(" - ")
                    .append(Component.translatable("parrot_type." + infoTag.getInt("Variant"))));
            return list;
        }
        return null;
    }

    @Override
    public CompoundTag getInfo(LivingEntity pet) {
        var tag = new CompoundTag();
        tag.putInt("Variant", ((Parrot) pet).getVariant());

        return tag;
    }

    @Override
    public List<CompoundTag> getDefaultPetCarriers() {
        var list = new ArrayList<CompoundTag>();
        for (int species : PARROTS_SPECIES) {
            var infoTag = new CompoundTag();
            infoTag.putInt("Variant", species);

            var entityTag = new CompoundTag();
            entityTag.putInt("Variant", species);

            list.add(getDefaultPetCarrier(infoTag, entityTag));
        }

        return list;
    }

}

package sophisticated_wolves.item.pet_carrier;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.Ocelot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import sophisticated_wolves.SophisticatedWolvesMod;
import sophisticated_wolves.api.pet_carrier.PetCarrier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class CatPetCarrier extends PetCarrier {

    public enum EnumCatType {
        //TODO CatVariant
        OCELOT,
        BLACK,
        RED,
        SIAMESE;

        public static EnumCatType getSpeciesByNum(int num) {
            if (num >= 0 && num < EnumCatType.values().length) {
                return EnumCatType.values()[num];
            } else {
                return OCELOT;
            }
        }

    }

    @Override
    public Class getPetClass() {
        return Cat.class;
    }

    @Override
    public String getPetNameLocalizationKey() {
        return "entity.minecraft.cat";
    }

    @Override
    public Mob spawnPet(Level level, Player player) {
        return EntityType.CAT.create(level);
    }

    @Override
    public List<Component> getInfo(CompoundTag infoTag) {
        if (infoTag.contains("CatType")) {
            var list = new ArrayList<Component>(1);
            list.add(Component.translatable("carrier.cat_type")
                    .append(" - ")
                    .append(Component.translatable(
                            "cat_type." + EnumCatType.getSpeciesByNum(infoTag.getInt("CatType"))
                                    .toString().toLowerCase())));
            return list;
        }
        return null;
    }

    @Override
    public CompoundTag getInfo(LivingEntity pet) {
        var tag = new CompoundTag();
        //TODO CatVariant
//        tag.putInt("CatType", ((Ocelot) pet).getTameSkin());

        return tag;
    }

    @Override
    public List<CompoundTag> getDefaultPetCarriers() {
        var list = new ArrayList<CompoundTag>();
        for (EnumCatType species : EnumCatType.values()) {
            var infoTag = new CompoundTag();
            infoTag.putInt("CatType", species.ordinal());

            var entityTag = new CompoundTag();
            entityTag.putInt("CatType", species.ordinal());

            list.add(getDefaultPetCarrier(infoTag, entityTag));
        }

        return list;
    }

}

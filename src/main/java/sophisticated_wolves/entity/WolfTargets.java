package sophisticated_wolves.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Endermite;
import net.minecraft.world.entity.monster.Silverfish;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.monster.Zoglin;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;
import net.minecraft.world.entity.raid.Raider;

import java.util.List;

/**
 * Sophisticated Wolves
 *
 * @author metroidfood
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public record WolfTargets(boolean attackSkeletons, boolean attackZombies, boolean attackSpiders,
                          boolean attackSlimes, boolean attackNether, boolean attackRaider) {

    // all skeletons: normal, wither & stray
    public static final List<Class<? extends LivingEntity>> SKELETONS_CLASSES = List.of(AbstractSkeleton.class);
    // zombies, husks, drowned, zombie villagers & zombie piglins
    public static final List<Class<? extends LivingEntity>> ZOMBIES_CLASSES = List.of(Zombie.class);
    // spiders, cave spiders, silverfishes & endermites
    public static final List<Class<? extends LivingEntity>> SPIDERS_CLASSES = List.of(Spider.class, Silverfish.class, Endermite.class);
    // slimes & magma cubes
    public static final List<Class<? extends LivingEntity>> SLIME_CLASSES = List.of(Slime.class);
    // enderman, blaze, hoglin, zoglin, brute piglin
    public static final List<Class<? extends LivingEntity>> NETHER_CLASSES = List.of(
            EnderMan.class, Blaze.class, Hoglin.class, Zoglin.class, PiglinBrute.class);
    // all raiders & witches
    public static final List<Class<? extends LivingEntity>> RAIDERS_CLASSES = List.of(Raider.class);

    public WolfTargets() {
        this(true, false, false, false, false, false);
    }

    public void saveData(CompoundTag tag) {
        var allowedFoodTag = new CompoundTag();
        allowedFoodTag.putBoolean("AttackSkeletons", this.attackSkeletons);
        allowedFoodTag.putBoolean("AttackZombies", this.attackZombies);
        allowedFoodTag.putBoolean("AttackSpiders", this.attackSpiders);
        allowedFoodTag.putBoolean("AttackSlimes", this.attackSlimes);
        allowedFoodTag.putBoolean("AttackNether", this.attackNether);
        allowedFoodTag.putBoolean("AttackRaider", this.attackRaider);
        tag.put("WolfTargets", allowedFoodTag);
    }

    public static WolfTargets getFromTag(CompoundTag tag) {
        if (tag.contains("WolfTargets")) {
            var allowedFoodTag = tag.getCompound("WolfTargets");
            return new WolfTargets(
                    getFromTag(allowedFoodTag, "AttackSkeletons"),
                    getFromTag(allowedFoodTag, "AttackZombies"),
                    getFromTag(allowedFoodTag, "AttackSpiders"),
                    getFromTag(allowedFoodTag, "AttackSlimes"),
                    getFromTag(allowedFoodTag, "AttackNether"),
                    getFromTag(allowedFoodTag, "AttackRaider"));
        }
        return new WolfTargets();
    }

    public void saveData(FriendlyByteBuf buffer) {
        buffer.writeBoolean(this.attackSkeletons);
        buffer.writeBoolean(this.attackZombies);
        buffer.writeBoolean(this.attackSpiders);
        buffer.writeBoolean(this.attackSlimes);
        buffer.writeBoolean(this.attackNether);
        buffer.writeBoolean(this.attackRaider);
    }

    public static WolfTargets getFromByteBuf(FriendlyByteBuf buffer) {
        return new WolfTargets(
                buffer.readBoolean(),
                buffer.readBoolean(),
                buffer.readBoolean(),
                buffer.readBoolean(),
                buffer.readBoolean(),
                buffer.readBoolean());
    }

    private static boolean getFromTag(CompoundTag tag, String name) {
        return tag.contains(name) && tag.getBoolean(name);
    }

}

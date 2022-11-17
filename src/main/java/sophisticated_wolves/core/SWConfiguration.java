package sophisticated_wolves.core;

import net.minecraftforge.common.ForgeConfigSpec;
import sophisticated_wolves.entity.SophisticatedWolf;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class SWConfiguration {

    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static ForgeConfigSpec.ConfigValue<Boolean> CUSTOM_WOLF_TEXTURES;
    public static ForgeConfigSpec.ConfigValue<Boolean> CUSTOM_BREEDING;
    public static ForgeConfigSpec.ConfigValue<Boolean> NAME_TAG_FOR_ANY_PETS;
    public static ForgeConfigSpec.ConfigValue<Boolean> IMMUNE_TO_CACTI;
    public static ForgeConfigSpec.ConfigValue<Boolean> WOLVES_WALKS_THROUGH_EACH_OTHER;
    public static ForgeConfigSpec.ConfigValue<Boolean> WOLVES_WALKS_THROUGH_OWNER;
    public static ForgeConfigSpec.ConfigValue<Integer> WOLVES_HEALTH_WILD;
    public static ForgeConfigSpec.ConfigValue<Integer> WOLVES_HEALTH_TAMED;
    public static ForgeConfigSpec.ConfigValue<Integer> WOLVES_DAMAGE_TAMED;

    public static ForgeConfigSpec.ConfigValue<Boolean> ATTACK_ANIMALS;
    public static ForgeConfigSpec.ConfigValue<Boolean> ATTACK_SKELETONS;

    public static ForgeConfigSpec.ConfigValue<Boolean> ALWAYS_SHOW_WOLF_NAME;

    static {
        BUILDER.push("Configs for Sophisticated Wolves Mod");

        CUSTOM_WOLF_TEXTURES = BUILDER.comment("Should sophisticated wolves use custom textures(or be like vanilla wolves)?")
                .define("Use Custom Wolf Textures", true);
        CUSTOM_BREEDING = BUILDER.comment("Should sophisticated wolves breed in its own way?")
                .define("Custom Breeding", true);
        NAME_TAG_FOR_ANY_PETS = BUILDER.comment("Should name tags be used for any kind of pet?")
                .define("Name Tag For Any Pets", true);
        IMMUNE_TO_CACTI = BUILDER.comment("Should sophisticated wolves be immune to cacti damage?")
                .define("Immune To Cacti", true);
        WOLVES_WALKS_THROUGH_EACH_OTHER = BUILDER.comment("Should sophisticated wolves walks through each other?")
                .define("Wolves Walks Through Each Other", true);
        WOLVES_WALKS_THROUGH_OWNER = BUILDER.comment("Should sophisticated wolves walks through owner?")
                .define("Wolves Walks Through Owner", true);
        WOLVES_HEALTH_WILD = BUILDER.comment("Amount of health wild sophisticated wolves have.")
                .defineInRange("Wild Wolves Health", SophisticatedWolf.DEFAULT_WILD_WOLF_HEALTH, 1, Integer.MAX_VALUE);
        WOLVES_HEALTH_TAMED = BUILDER.comment("Amount of health tamed sophisticated wolves have.")
                .defineInRange("Tamed Wolves Health", SophisticatedWolf.DEFAULT_TAMED_WOLF_HEALTH, 1, Integer.MAX_VALUE);
        WOLVES_DAMAGE_TAMED = BUILDER.comment("Attack damage of tamed sophisticated wolves.")
                .defineInRange("Tamed Wolves Damage", SophisticatedWolf.DEFAULT_TAMED_WOLF_DAMAGE, 1, Integer.MAX_VALUE);

        ATTACK_ANIMALS = BUILDER.comment("Should non tamed sophisticated wolves attack other animals?")
                .define("Attack Animals", false);
        ATTACK_SKELETONS = BUILDER.comment("Should sophisticated wolves hunt on skeletons?")
                .define("Attack Skeletons", false);

        ALWAYS_SHOW_WOLF_NAME = BUILDER.comment("Always show the wolf's name.")
                .define( "Always Show Wolves' Name", true);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }

}

package sophisticated_wolves.core;

import net.minecraftforge.common.ForgeConfigSpec;
import sophisticated_wolves.entity.SophisticatedWolf;

/**
 * Sophisticated Wolves
 *
 * 
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

public class SWConfiguration {

    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static ForgeConfigSpec.ConfigValue<Boolean> CUSTOM_WOLF_TEXTURES;
    //TODO remove ???
    public static ForgeConfigSpec.ConfigValue<Boolean> RESPAWNING_WOLVES;
    public static ForgeConfigSpec.ConfigValue<Boolean> CUSTOM_BREEDING;
    public static ForgeConfigSpec.ConfigValue<Boolean> NAME_TAG_FOR_ANY_PETS;
    public static ForgeConfigSpec.ConfigValue<Boolean> IMMUNE_TO_CACTI;
    public static ForgeConfigSpec.ConfigValue<Boolean> WOLVES_WALKS_THROUGH_EACH_OTHER;
    public static ForgeConfigSpec.ConfigValue<Integer> WOLVES_HEALTH_WILD;
    public static ForgeConfigSpec.ConfigValue<Integer> WOLVES_HEALTH_TAMED;

    public static ForgeConfigSpec.ConfigValue<Boolean> ATTACK_ANIMALS;
    public static ForgeConfigSpec.ConfigValue<Boolean> ATTACK_SKELETONS;

    public static ForgeConfigSpec.ConfigValue<Boolean> ALWAYS_SHOW_WOLF_NAME;

    //TODO remove ???
//    public static ForgeConfigSpec.ConfigValue<Boolean> ENABLE_PETS_SELLER;

    static {
        BUILDER.push("Configs for Sophisticated Wolves Mod");

        CUSTOM_WOLF_TEXTURES = BUILDER.comment("Should sophisticated wolves use custom textures(or be like vanilla wolves)?")
                .define("Use Custom Wolf Textures", true);
        //TODO i still think some people kinda want this ... should remain as an option that people could always
        //disable. Or.... DoggyTalents could be an addon for this, with dog bed usage. 
//        RESPAWNING_WOLVES = BUILDER.comment("Should sophisticated wolves respawn(and despawn for non tamed)?")
//                .define("Respawning Wolves", true);
        CUSTOM_BREEDING = BUILDER.comment("Should sophisticated wolves breed in its own way?")
                .define("Custom Breeding", true);
        NAME_TAG_FOR_ANY_PETS = BUILDER.comment("Should name tags be used for any kind of pet?")
                .define("Name Tag For Any Pets", true);
        IMMUNE_TO_CACTI = BUILDER.comment("Should sophisticated wolves be immune to cacti damage?")
                .define("Immune To Cacti", true);
        WOLVES_WALKS_THROUGH_EACH_OTHER = BUILDER.comment("Should sophisticated wolves walks through each other?")
                .define("Wolves Walks Through Each Other", true);
        WOLVES_HEALTH_WILD = BUILDER.comment("Amount of health wild sophisticated wolves have.")
                .defineInRange("Wild Wolves Health", SophisticatedWolf.DEFAULT_WILD_WOLF_HEALTH, 1, Integer.MAX_VALUE);
        WOLVES_HEALTH_TAMED = BUILDER.comment("Amount of health tamed sophisticated wolves have.")
                .defineInRange("Tamed Wolves Health", SophisticatedWolf.DEFAULT_TAMED_WOLF_HEALTH, 1, Integer.MAX_VALUE);

        ATTACK_ANIMALS = BUILDER.comment("Should non tamed sophisticated wolves attack other animals?")
                .define("Attack Animals", false);
        ATTACK_SKELETONS = BUILDER.comment("Should sophisticated wolves hunt on skeletons?")
                .define("Attack Skeletons", false);

        ALWAYS_SHOW_WOLF_NAME = BUILDER.comment("Always show the wolf's name.")
                .define( "Always Show Wolves' Name", true);

        //TODO ???
//        ENABLE_PETS_SELLER = BUILDER.comment("Add new villager - pets seller")
//                .define("Enable Pets Seller", true);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }

}

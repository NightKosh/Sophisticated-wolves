package sophisticated_wolves;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class SWConfiguration {

    private static SWConfiguration instance;
    private static Configuration config;


    public static boolean customWolfTextures;
    public static boolean respawningWolves;
    public static boolean customBreeding;
    public static boolean nameTagForAnyPets;
    public static boolean immuneToCacti;

    public static boolean attackAnimals;
    public static boolean attackSkeletons;

    public static int spawnProbability;
    public static int spawnMinCount;
    public static int spawnMaxCount;

    public static boolean enablePetsSeller;

    public static boolean wolvesWalksThroughEachOther;

    private SWConfiguration(File configFile) {
        this.config = new Configuration(configFile);
        getConfigs();
    }

    public static SWConfiguration getInstance(File configFile) {
        if (instance == null) {
            return new SWConfiguration(configFile);
        } else {
            return instance;
        }
    }

    public final void getConfigs() {
        config.load();

        customWolfTextures = config.get(Configuration.CATEGORY_GENERAL, "CustomWolfTextures", true).getBoolean();
        respawningWolves = config.get(Configuration.CATEGORY_GENERAL, "RespawningWolves", true).getBoolean();
        customBreeding = config.get(Configuration.CATEGORY_GENERAL, "CustomBreeding", true).getBoolean();
        nameTagForAnyPets = config.get(Configuration.CATEGORY_GENERAL, "NameTagForAnyPets", true).getBoolean();
        immuneToCacti = config.get(Configuration.CATEGORY_GENERAL, "ImmuneToCacti", true).getBoolean();
        attackAnimals = config.get(Configuration.CATEGORY_GENERAL, "AttackAnimals", false).getBoolean();
        attackSkeletons = config.get(Configuration.CATEGORY_GENERAL, "AttackSkeletons", true).getBoolean();

        spawnProbability = config.get(Configuration.CATEGORY_GENERAL, "SpawnProbability", SWEntity.DEFAULT_SPAWN_PROBABILITY).getInt();
        spawnMinCount = config.get(Configuration.CATEGORY_GENERAL, "SpawnMinCount", SWEntity.DEFAULT_SPAWN_MIN_COUNT).getInt();
        spawnMaxCount = config.get(Configuration.CATEGORY_GENERAL, "SpawnMaxCount", SWEntity.DEFAULT_SPAWN_MAX_COUNT).getInt();

        if (spawnMinCount < 0) {
            spawnMinCount = 0;
        }
        if (spawnMaxCount < spawnMinCount) {
            spawnMaxCount = spawnMinCount;
        }

        enablePetsSeller = config.get(Configuration.CATEGORY_GENERAL, "EnablePetsSeller", true).getBoolean();

        wolvesWalksThroughEachOther = config.get(Configuration.CATEGORY_GENERAL, "WolvesWalksThroughEachOther", true).getBoolean();

        config.save();
    }
}

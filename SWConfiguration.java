package sophisticated_wolves;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class SWConfiguration {

    private static SWConfiguration instance;
    private static Configuration config;


    public static boolean customWolfTextures = true;
    public static boolean respawningWolves = true;
    public static boolean customBreeding = true;
    public static boolean nameTagForAnyPets = true;
    public static boolean logWolfErrors = true;

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

        customWolfTextures = config.get(Configuration.CATEGORY_GENERAL, "CustomWolfTextures", true).getBoolean(true);
        respawningWolves = config.get(Configuration.CATEGORY_GENERAL, "RespawningWolves", true).getBoolean(true);
        customBreeding = config.get(Configuration.CATEGORY_GENERAL, "CustomBreeding", true).getBoolean(true);
        nameTagForAnyPets = config.get(Configuration.CATEGORY_GENERAL, "NameTagForAnyPets", true).getBoolean(true);
        logWolfErrors = config.get(Configuration.CATEGORY_GENERAL, "LogWolfErrors", true).getBoolean(true);

        config.save();
    }
}

package sophisticated_wolves;

import cpw.mods.fml.common.registry.LanguageRegistry;

/**
 * Created by Fox on 14.05.2014.
 */
public class SWLocalization {
    private SWLocalization() {

    }

    private static final String[] LOCALE_NAMES = {
            "en_US",
            "ru_RU"
    };


    public static void init() {
        for (String localeName : LOCALE_NAMES) {
            LanguageRegistry.instance().loadLocalization(Resources.LOCALIZATION_LOCATION + localeName + ".lang", localeName, false);
        }
    }
}

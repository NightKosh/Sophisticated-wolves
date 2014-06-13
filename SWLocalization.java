package sophisticated_wolves;

import cpw.mods.fml.common.registry.LanguageRegistry;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
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

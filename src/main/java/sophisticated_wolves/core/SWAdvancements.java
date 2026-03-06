package sophisticated_wolves.core;

import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import sophisticated_wolves.api.ModInfo;

import static net.minecraft.resources.Identifier.fromNamespaceAndPath;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class SWAdvancements {

    public static final Identifier SOPHISTICATED_TASTE = fromNamespaceAndPath(ModInfo.ID, "sophisticated_taste");
    public static final Identifier NAME_TO_REMEMBER = fromNamespaceAndPath(ModInfo.ID, "name_to_remember");
    public static final Identifier PORTABLE_FRIEND = fromNamespaceAndPath(ModInfo.ID, "portable_friend");
    public static final Identifier BACK_TO_THE_WILD = fromNamespaceAndPath(ModInfo.ID, "back_to_the_wild");
    public static final Identifier ANTIDOTE = fromNamespaceAndPath(ModInfo.ID, "antidote");
    public static final Identifier FIREPROOF = fromNamespaceAndPath(ModInfo.ID, "fireproof");
    public static final Identifier CREEPER_ALERT = fromNamespaceAndPath(ModInfo.ID, "creeper_alert");
    public static final Identifier STAND_DOWN = fromNamespaceAndPath(ModInfo.ID, "stand_down");
    public static final Identifier TACTICAL_RETREAT = fromNamespaceAndPath(ModInfo.ID, "tactical_retreat");
    public static final Identifier COME_HERE = fromNamespaceAndPath(ModInfo.ID, "come_here");
    public static final Identifier WELL_FED = fromNamespaceAndPath(ModInfo.ID, "well_fed");
    public static final Identifier HOME_SWEET_HOME = fromNamespaceAndPath(ModInfo.ID, "home_sweet_home");
    public static final Identifier NEW_TRICKS = fromNamespaceAndPath(ModInfo.ID, "new_tricks");
    public static final Identifier SELF_SERVICE = fromNamespaceAndPath(ModInfo.ID, "self_service");
    public static final Identifier TERRITORIAL = fromNamespaceAndPath(ModInfo.ID, "territorial");
    public static final Identifier THE_TERMINAL_LIST = fromNamespaceAndPath(ModInfo.ID, "the_terminal_list");

    public static void giveAdvancement(Player player, Level level, Identifier advancement) {
        if (player instanceof ServerPlayer serverPlayer) {
            var adv = level.getServer().getAdvancements().get(advancement);
            if (adv != null) {
                var playerAdv = serverPlayer.getAdvancements();
                if (!playerAdv.getOrStartProgress(adv).isDone()) {
                    playerAdv.award(adv, "triggered");
                }
            }
        }
    }

}

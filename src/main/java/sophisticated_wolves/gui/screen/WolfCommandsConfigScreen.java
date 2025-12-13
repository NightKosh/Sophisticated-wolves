package sophisticated_wolves.gui.screen;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import sophisticated_wolves.core.SWMessages;
import sophisticated_wolves.core.SWResources;
import sophisticated_wolves.entity.SophisticatedWolf;
import sophisticated_wolves.gui.component.GuiCheckbox;
import sophisticated_wolves.packets.WolfCommandsConfigMessageToServer;

import java.util.List;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class WolfCommandsConfigScreen extends WolfConfigScreen {

    private GuiCheckbox followOwner;
    private GuiCheckbox guardZone;

    public WolfCommandsConfigScreen(SophisticatedWolf wolf) {
        super(wolf, Component.translatable("gui.sophisticated_wolves.wolf_commands_configs.title"));
    }

    @Override
    protected void initCustomComponents(int x, int y) {
        this.commandsScreenButton.setTabActive(true);

        var commands = wolf.getWolfCommands();
        this.addRenderableWidget(this.followOwner = new GuiCheckbox(x + COLUMN_1, y + LINE_2, 80,
                Component.translatable("gui.sophisticated_wolves.wolf_commands_configs.follow_owner_button"),
                () -> List.of(guardZone),
                commands.followOwner()));
        this.addRenderableWidget(this.guardZone = new GuiCheckbox(x + COLUMN_2, y + LINE_2, 80,
                Component.translatable("gui.sophisticated_wolves.wolf_commands_configs.guard_zone_button"),
                () -> List.of(followOwner),
                commands.guardZone()));
    }

    @Override
    protected void onScreenClosed() {
        wolf.updateCommands(this.followOwner.isEnabled(), this.guardZone.isEnabled());
        SWMessages.sendToServer(WolfCommandsConfigMessageToServer.getFromWolf(this.wolf));
    }

    @Override
    protected ResourceLocation getBackground() {
        return SWResources.COMMANDS_GUI;
    }

}

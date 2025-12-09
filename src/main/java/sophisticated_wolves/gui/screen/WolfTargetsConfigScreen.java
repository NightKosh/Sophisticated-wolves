package sophisticated_wolves.gui.screen;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import sophisticated_wolves.core.SWResources;
import sophisticated_wolves.core.SWMessages;
import sophisticated_wolves.entity.SophisticatedWolf;
import sophisticated_wolves.gui.component.GuiCheckbox;
import sophisticated_wolves.packets.WolfTargetsConfigMessageToServer;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class WolfTargetsConfigScreen extends WolfConfigScreen {

    private GuiCheckbox attackSkeletons;
    private GuiCheckbox attackZombies;
    private GuiCheckbox attackSpiders;
    private GuiCheckbox attackSlimes;
    private GuiCheckbox attackNether;
    private GuiCheckbox attackRaider;

    public WolfTargetsConfigScreen(SophisticatedWolf wolf) {
        super(wolf, Component.translatable("gui.sophisticated_wolves.wolf_targets_configs.title"));
    }

    @Override
    protected void initCustomComponents(int x, int y) {
        this.targetsScreenButton.setTabActive(true);

        var wolfTargets = wolf.getWolfTargets();
        this.addRenderableWidget(this.attackSlimes = new GuiCheckbox(x + COLUMN_1, y + LINE_1, wolfTargets.attackSlimes()));
        this.addRenderableWidget(this.attackSkeletons = new GuiCheckbox(x + COLUMN_2, y + LINE_1, wolfTargets.attackSkeletons()));
        this.addRenderableWidget(this.attackSpiders = new GuiCheckbox(x + COLUMN_1, y + LINE_2, wolfTargets.attackSpiders()));
        this.addRenderableWidget(this.attackZombies = new GuiCheckbox(x + COLUMN_2, y + LINE_2, wolfTargets.attackZombies()));
        this.addRenderableWidget(this.attackNether = new GuiCheckbox(x + COLUMN_1, y + LINE_3, wolfTargets.attackNether()));
        this.addRenderableWidget(this.attackRaider = new GuiCheckbox(x + COLUMN_2, y + LINE_3, wolfTargets.attackRaider()));
    }

    @Override
    protected void onScreenClosed() {
        wolf.updateTargets(this.attackSkeletons.isEnabled(), this.attackZombies.isEnabled(), this.attackSpiders.isEnabled(),
                this.attackSlimes.isEnabled(), this.attackNether.isEnabled(), this.attackRaider.isEnabled());
        SWMessages.sendToServer(new WolfTargetsConfigMessageToServer(this.wolf));
    }

    @Override
    protected ResourceLocation getBackground() {
        return SWResources.TARGET_GUI;
    }

}

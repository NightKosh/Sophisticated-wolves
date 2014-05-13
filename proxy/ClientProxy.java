package sophisticated_wolves.proxy;

import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.client.model.ModelWolf;
import sophisticated_wolves.RenderSophisticatedWolf;
import sophisticated_wolves.entity.SophisticatedWolf;

public class ClientProxy extends CommonProxy {

    @Override
    public void registerRenderers() {
        // Mobs renderers
        registerMobsRenderers();
    }

    private void registerMobsRenderers() {
        // zombie dog
        RenderingRegistry.registerEntityRenderingHandler(SophisticatedWolf.class, new RenderSophisticatedWolf(new ModelWolf(), new ModelWolf()));
    }
}

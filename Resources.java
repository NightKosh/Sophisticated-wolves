package sophisticated_wolves;

import net.minecraft.util.ResourceLocation;

public class Resources {
    private Resources() {
        
    }

    private static final String MOD_NAME = ModInfo.ID.toLowerCase();
    private static final String ENTITY_LOCATION = MOD_NAME + ":textures/entity/";

    //items
    public static final String DOG_TAG = MOD_NAME + ":dogtag";
    public static final String DOG_TREAT = MOD_NAME + ":dogtreat";

    //Brown Wolf
    public static final ResourceLocation brownWolf = new ResourceLocation(ENTITY_LOCATION + "brown/wolf.png");
    public static final ResourceLocation brownWolfTame = new ResourceLocation(ENTITY_LOCATION + "brown/wolf_tame.png");
    public static final ResourceLocation brownWolfAngry = new ResourceLocation(ENTITY_LOCATION + "brown/wolf_angry.png");

    //Black Wolf
    public static final ResourceLocation blackWolf = new ResourceLocation(ENTITY_LOCATION + "black/wolf.png");
    public static final ResourceLocation blackWolfTame = new ResourceLocation(ENTITY_LOCATION + "black/wolf_tame.png");
    public static final ResourceLocation blackWolfAngry = new ResourceLocation(ENTITY_LOCATION + "black/wolf_angry.png");

    //Forest Wolf
    public static final ResourceLocation forestWolf = new ResourceLocation(ENTITY_LOCATION + "forest/wolf.png");
    public static final ResourceLocation forestWolfTame = new ResourceLocation(ENTITY_LOCATION + "forest/wolf_tame.png");
    public static final ResourceLocation forestWolfAngry = new ResourceLocation(ENTITY_LOCATION + "forest/wolf_angry.png");

    //Forced Default Wolf
    public static final ResourceLocation defaultWolf = new ResourceLocation(ENTITY_LOCATION + "default/wolf.png");
    public static final ResourceLocation defaultWolfTame = new ResourceLocation(ENTITY_LOCATION + "default/wolf_tame.png");
    public static final ResourceLocation defaultWolfAngry = new ResourceLocation(ENTITY_LOCATION + "default/wolf_angry.png");
}

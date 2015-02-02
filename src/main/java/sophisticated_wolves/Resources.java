package sophisticated_wolves;

import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class Resources {
    private Resources() {
        
    }

    private static final String MOD_NAME = ModInfo.ID.toLowerCase();
    private static final String ENTITY_LOCATION = MOD_NAME + ":textures/entity/";
    // localization path
    public static final String LOCALIZATION_LOCATION = "/assets/" + MOD_NAME + "/lang/";

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

    // MODEL RESOURCES
    public static final ModelResourceLocation dogTagModel = new ModelResourceLocation(MOD_NAME + ":" + SWItems.DOG_TAG, "inventory");
    public static final ModelResourceLocation dogTreatModel = new ModelResourceLocation(MOD_NAME + ":" + SWItems.DOG_TREAT, "inventory");
}

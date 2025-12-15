package sophisticated_wolves.event;

import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.equipment.ArmorMaterials;
import net.minecraft.world.item.equipment.Equippable;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.ModifyDefaultComponentsEvent;
import sophisticated_wolves.api.ModInfo;
import sophisticated_wolves.core.SWConfiguration;
import sophisticated_wolves.core.SWEntities;

import static sophisticated_wolves.SophisticatedWolvesMod.LOGGER;

/**
 * Sophisticated Wolves
 *
 * @author metroidfood
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)git
 */
@EventBusSubscriber(modid = ModInfo.ID)
public class SWItemComponentPatches {

    @SubscribeEvent
    public static void modifyDefaultComponents(ModifyDefaultComponentsEvent event) {
        event.modify(Items.WOLF_ARMOR, builder -> {
            if (SWConfiguration.DEBUG_MODE.get()) {
                LOGGER.info("ModifyDefaultComponentsEvent event for WOLF_ARMOR item triggered");
            }

            var oldEq = Items.WOLF_ARMOR.components().get(DataComponents.EQUIPPABLE);
            if (oldEq != null) {
                HolderSet<EntityType<?>> allowed = HolderSet.direct(
                        EntityType.WOLF.builtInRegistryHolder(),
                        SWEntities.SOPHISTICATED_WOLF.getDelegate());

                builder.set(DataComponents.EQUIPPABLE,
                        Equippable.builder(oldEq.slot())
                                .setEquipSound(oldEq.equipSound())// was in original item
                                .setAsset(ArmorMaterials.ARMADILLO_SCUTE.assetId())// was in original item
                                .setAllowedEntities(allowed)// was in original item, changed
                                .setDispensable(oldEq.dispensable())
                                .setSwappable(oldEq.swappable())
                                .setDamageOnHurt(oldEq.damageOnHurt())
                                .setEquipOnInteract(oldEq.equipOnInteract())
                                .setCanBeSheared(oldEq.canBeSheared())// was in original item
                                .setShearingSound(oldEq.shearingSound())// was in original item
                                .build());
            }
        });
    }

}

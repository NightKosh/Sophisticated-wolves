package sophisticated_wolves.packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class PetNameMessageToServer {

    private final UUID animalId;
    private final String text;

    public PetNameMessageToServer(TamableAnimal animal, String text) {
        this.animalId = animal.getUUID();
        this.text = text;
    }

    public PetNameMessageToServer(FriendlyByteBuf buf) {
        this.animalId = UUID.fromString(buf.readUtf());
        this.text = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(animalId.toString());
        buf.writeUtf(text);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        var context = supplier.get();
        context.enqueueWork(() -> {
            var player = context.getSender();
            var level = player.getLevel();

            if (level != null) {
                var animal = level.getEntity(animalId);
                if (animal != null && animal instanceof TamableAnimal) {
                    animal.setCustomName(Component.literal(text));
                }
            }
        });
        return true;
    }

}

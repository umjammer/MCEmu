package deltatwoforce.mcemu;

import com.mojang.blaze3d.platform.InputConstants;
import deltatwoforce.mcemu.minecraft.TelevisionEntityRenderer;
import deltatwoforce.mcemu.model.EmuRenderer;
import deltatwoforce.mcemu.model.MCEmu;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.minecraft.client.KeyMapping;


@Environment(EnvType.CLIENT)
public class MCEmuClientMod implements ClientModInitializer {

    public static MCEmu model;

    private static final KeyMapping.Category NES = KeyMapping.Category.register(MCEmuMod.id("nes"));

    private static KeyMapping key(String name, int key) {
        return new KeyMapping(name, InputConstants.Type.KEYBOARD, key, NES);
    }

    public static final KeyMapping P1NES_LEFT = key("NES: P1 Left", InputConstants.KEY_LEFT);
    public static final KeyMapping P1NES_RIGHT = key("NES: P1 Right", InputConstants.KEY_RIGHT);
    public static final KeyMapping P1NES_UP = key("NES: P1 Up", InputConstants.KEY_UP);
    public static final KeyMapping P1NES_DOWN = key("NES: P1 Down", InputConstants.KEY_DOWN);
    public static final KeyMapping P1NES_A = key("NES: P1 A", InputConstants.KEY_RSHIFT);
    public static final KeyMapping P1NES_B = key("NES: P1 B", InputConstants.KEY_RCONTROL);
    public static final KeyMapping P1NES_START = key("NES: P1 Start", InputConstants.KEY_RETURN);
    public static final KeyMapping P1NES_SELECT = key("NES: P1 Select", InputConstants.KEY_BACKSPACE);

    public static final KeyMapping P2NES_LEFT = key("NES: P2 Left", InputConstants.KEY_1);
    public static final KeyMapping P2NES_RIGHT = key("NES: P2 Right", InputConstants.KEY_3);
    public static final KeyMapping P2NES_UP = key("NES: P2 Up", InputConstants.KEY_5);
    public static final KeyMapping P2NES_DOWN = key("NES: P2 Down", InputConstants.KEY_2);
    public static final KeyMapping P2NES_A = key("NES: P2 A", InputConstants.KEY_RETURN);
    public static final KeyMapping P2NES_B = key("NES: P2 B", InputConstants.KEY_0);
    public static final KeyMapping P2NES_START = key("NES: P2 Start", InputConstants.KEY_7);
    public static final KeyMapping P2NES_SELECT = key("NES: P2 Select", InputConstants.KEY_9);

    public static final KeyMapping[][] keyDef = {
            { P1NES_A, P1NES_B, P1NES_SELECT, P1NES_START, P1NES_UP, P1NES_DOWN, P1NES_LEFT, P1NES_RIGHT, },
            { P2NES_A, P2NES_B, P2NES_SELECT, P2NES_START, P2NES_UP, P2NES_DOWN, P2NES_LEFT, P2NES_RIGHT, } };

    @Override
    public void onInitializeClient() {
        for (KeyMapping[] kms : keyDef) {
            for (KeyMapping km : kms) {
                KeyMappingHelper.registerKeyMapping(km);
            }
        }

        BlockEntityRendererRegistry.register(MCEmuMod.televisionEntityType, TelevisionEntityRenderer::new);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            for (int i = 0; i < 2; i++)
                for (int j = 0; j < 8; j++)
                    EmuRenderer.inpi.buf[i * 8 + j] = keyDef[i][j].isDown() ? 1 : 0;
        });

        model = new MCEmu();
    }
}

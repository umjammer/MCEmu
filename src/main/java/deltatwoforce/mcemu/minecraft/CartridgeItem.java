package deltatwoforce.mcemu.minecraft;

import java.nio.file.Path;
import java.util.function.Consumer;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;


public class CartridgeItem extends Item {
	private String name;
	public Path rom;

	public CartridgeItem(Properties properties, Path rom) {
		super(properties);
		name = rom.getFileName().toString();
		int i = name.lastIndexOf('.');
		if (i > 0) {
			name = name.replace(name.substring(i), "");
		}

		this.rom = rom;
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> tooltip, TooltipFlag flag) {
		tooltip.accept(Component.literal("A ROM File for the NES. " + name));
		super.appendHoverText(stack, context, display, tooltip, flag);
	}
}

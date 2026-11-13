package deltatwoforce.mcemu;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import deltatwoforce.mcemu.minecraft.CartridgeItem;
import deltatwoforce.mcemu.minecraft.ConsoleBlock;
import deltatwoforce.mcemu.minecraft.TelevisionBlock;
import deltatwoforce.mcemu.minecraft.TelevisionEntity;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MCEmuMod implements ModInitializer {

    public static final String MODID = "mcemu";

	public static Logger logger = LogManager.getLogger(MODID);

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MODID, path);
	}

	private static ResourceKey<Block> blockKey(String path) {
		return ResourceKey.create(Registries.BLOCK, id(path));
	}

	private static ResourceKey<Item> itemKey(String path) {
		return ResourceKey.create(Registries.ITEM, id(path));
	}

	// LESSON: w/o #noOcclusion() makes block super dark
	public static final ConsoleBlock consoleBlock = new ConsoleBlock(BlockBehaviour.Properties.of()
			.setId(blockKey("console")).mapColor(MapColor.METAL).sound(SoundType.METAL).noOcclusion());
	public static final TelevisionBlock televisionBlock = new TelevisionBlock(BlockBehaviour.Properties.of()
			.setId(blockKey("television")).mapColor(MapColor.WOOD).sound(SoundType.WOOD).noOcclusion());

	public static final BlockItem consoleItem = new BlockItem(consoleBlock,
			new Item.Properties().setId(itemKey("console")).useBlockDescriptionPrefix());
	public static final BlockItem televisionItem = new BlockItem(televisionBlock,
			new Item.Properties().setId(itemKey("television")).useBlockDescriptionPrefix());

	public static final List<CartridgeItem> cartridges = new ArrayList<>();

	public static final ResourceKey<CreativeModeTab> tabNESKey = ResourceKey.create(Registries.CREATIVE_MODE_TAB, id("nes"));

	public static final CreativeModeTab tabNES = FabricCreativeModeTab.builder()
			.title(Component.translatable("itemGroup.mcemu.nes"))
			.icon(() -> new ItemStack(Items.STAINED_GLASS.white()))
			.displayItems((context, entries) -> {
				entries.accept(consoleItem);
				entries.accept(televisionItem);
				cartridges.forEach(entries::accept);
			})
			.build();

	public static final BlockEntityType<TelevisionEntity> televisionEntityType =
			FabricBlockEntityTypeBuilder.create(TelevisionEntity::new, televisionBlock).build();

	@Override
    public void onInitialize() {
		Registry.register(BuiltInRegistries.BLOCK, blockKey("console"), consoleBlock);
		Registry.register(BuiltInRegistries.ITEM, itemKey("console"), consoleItem);
		Registry.register(BuiltInRegistries.BLOCK, blockKey("television"), televisionBlock);
		Registry.register(BuiltInRegistries.ITEM, itemKey("television"), televisionItem);

		Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, id("television_entity"), televisionEntityType);

		Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, tabNESKey, tabNES);

		try {
			Path nesroms = FabricLoader.getInstance().getConfigDir().resolve(MODID).resolve("roms/nes");
			Files.createDirectories(nesroms);
logger.info("loading NES roms from " + nesroms + " ...");
			try (Stream<Path> files = Files.list(nesroms)) {
				files.forEach(f -> {
					CartridgeItem item = new CartridgeItem(new Item.Properties().setId(itemKey("cartridge")), f);
					Registry.register(BuiltInRegistries.ITEM, itemKey("cartridge"), item);
					cartridges.add(item);
logger.info("loaded " + item.rom);
				});
			}
		} catch (IOException e) {
			logger.error(e);
		}
	}
}

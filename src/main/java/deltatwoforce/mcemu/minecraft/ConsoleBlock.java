package deltatwoforce.mcemu.minecraft;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import static deltatwoforce.mcemu.MCEmuClientMod.model;
import static deltatwoforce.mcemu.MCEmuMod.logger;

// LESSON: assets/NS/blockstates/*.json should be simple for new version
public class ConsoleBlock extends Block {

	private static final VoxelShape SHAPE = box(1, 0, 3, 15, 5, 13);

	public ConsoleBlock(Properties properties) {
		super(properties);
	}

	@Override
	protected InteractionResult useItemOn(ItemStack is, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
logger.info("client: " + world.isClientSide());

		if (world.isClientSide()) {
			if (is.getItem() instanceof CartridgeItem item) {
logger.info("item: " + item.rom);
				if (!model.isRunning()) {
					model.start(item.rom);
					logger.info("nes: started");
				} else {
					model.reset(item.rom);
					logger.info("nes: restarted");
				}
			}
		}

		return super.useItemOn(is, state, world, pos, player, hand, hit);
	}

	@Override
	public void destroy(LevelAccessor world, BlockPos pos, BlockState state) {
		model.stop();
logger.info("nes stopped");
		super.destroy(world, pos, state);
	}

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter view, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}
}

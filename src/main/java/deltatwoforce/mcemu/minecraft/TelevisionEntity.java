package deltatwoforce.mcemu.minecraft;

import deltatwoforce.mcemu.MCEmuMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;


public class TelevisionEntity extends BlockEntity {

    public TelevisionEntity(BlockPos pos, BlockState state) {
        super(MCEmuMod.televisionEntityType, pos, state);
    }

    public Direction getHorizontalFacing() {
        return getBlockState().getValue(HORIZONTAL_FACING);
    }
}

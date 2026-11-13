package deltatwoforce.mcemu.minecraft;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import static deltatwoforce.mcemu.MCEmuClientMod.model;


public class TelevisionEntityRenderer implements BlockEntityRenderer<TelevisionEntity, TelevisionEntityRenderer.State> {

    private static final int LIGHT = 15728640;

    public static class State extends BlockEntityRenderState {
        Direction facing = Direction.NORTH;
    }

    private final DynamicNativeImage dni;

    public TelevisionEntityRenderer(BlockEntityRendererProvider.Context ctx) {
        dni = new DynamicNativeImage(model.getImage());
    }

    @Override
    public State createRenderState() {
        return new State();
    }

    @Override
    public void extractRenderState(TelevisionEntity entity, State state, float partialTick, Vec3 cameraPosition, @Nullable ModelFeatureRenderer.CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(entity, state, partialTick, cameraPosition, breakProgress);
        state.facing = entity.getHorizontalFacing();
    }

    @Override
    public void submit(State state, PoseStack matrixStack, SubmitNodeCollector collector, CameraRenderState camera) {

        matrixStack.pushPose();
        matrixStack.rotateDegrees(Axis.ZP, 180.0F);

        Direction direction = state.facing;
        if (direction == Direction.EAST) {
            matrixStack.rotateDegrees(Axis.YP, -90 * direction.get2DDataValue());
            matrixStack.translate(-1 + 0.0625, -1 + 0.0625, -1 + 0.0625);
        } else if (direction == Direction.WEST) {
            matrixStack.rotateDegrees(Axis.YP, -90 * direction.get2DDataValue());
            matrixStack.translate(0.0625, -1 + 0.0625, 0.0625);
        } else if (direction == Direction.SOUTH) {
            matrixStack.rotateDegrees(Axis.YP, -180);
            matrixStack.translate( 0.0625, -1 + 0.0625, -1 + 0.0625);
        } else if (direction == Direction.NORTH) {
            matrixStack.rotateDegrees(Axis.YP, 180 * direction.get2DDataValue());
            matrixStack.translate(-1 + 0.0625, -1 + 0.0625, 0.0625);
        }

        matrixStack.scale(0.007f, 0.007f, 0.007f);
        collector.submitCustomGeometry(matrixStack, RenderTypes.text(dni.update()), (pose, vertexConsumer) -> {
            vertexConsumer.addVertex(pose, 0.0F, 128.0F, -0.01F).setColor(255, 255, 255, 255).setUv(0.0F, 1.0F).setLight(LIGHT);
            vertexConsumer.addVertex(pose, 128.0F, 128.0F, -0.01F).setColor(255, 255, 255, 255).setUv(1.0F, 1.0F).setLight(LIGHT);
            vertexConsumer.addVertex(pose, 128.0F, 0.0F, -0.01F).setColor(255, 255, 255, 255).setUv(1.0F, 0.0F).setLight(LIGHT);
            vertexConsumer.addVertex(pose, 0.0F, 0.0F, -0.01F).setColor(255, 255, 255, 255).setUv(0.0F, 0.0F).setLight(LIGHT);
        });
        matrixStack.popPose();
    }
}

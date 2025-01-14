package com.fengluoqiuwu.forced_banner.block_entities;

import com.fengluoqiuwu.forced_banner.Config;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.MobSpawnerLogic;

@Environment(EnvType.CLIENT)
public class CustomMobSpawnerBlockEntityRenderer implements BlockEntityRenderer<MobSpawnerBlockEntity> {
    private final EntityRenderDispatcher entityRenderDispatcher;

    public CustomMobSpawnerBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        this.entityRenderDispatcher = ctx.getEntityRenderDispatcher();
    }

    public void render(MobSpawnerBlockEntity mobSpawnerBlockEntity, float f, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, int j) {
        matrixStack.push();
        matrixStack.translate(0.5F, 0.0F, 0.5F);
        MobSpawnerLogic mobSpawnerLogic = mobSpawnerBlockEntity.getLogic();
        Entity entity = mobSpawnerLogic.getRenderedEntity(mobSpawnerBlockEntity.getWorld(), mobSpawnerBlockEntity.getWorld().getRandom(), mobSpawnerBlockEntity.getPos());
        if (entity != null) {
            float g = 0.53125F;
            float h = Math.max(entity.getWidth(), entity.getHeight());
            if ((double)h > (double)1.0F) {
                g /= h;
            }

            matrixStack.translate(0.0F, 0.4F, 0.0F);
            matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float)MathHelper.lerp((double)f, mobSpawnerLogic.getLastRotation(), mobSpawnerLogic.getRotation()) * 10.0F));
            matrixStack.translate(0.0F, -0.2F, 0.0F);
            matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-30.0F));
            matrixStack.scale(g, g, g);
            this.entityRenderDispatcher.render(entity, (double)0.0F, (double)0.0F, (double)0.0F, 0.0F, f, matrixStack, vertexConsumerProvider, i);
        }

        matrixStack.pop();
    }

    @Override
    public int getRenderDistance() {
        return Config.isModEnabled() ? Config.getRenderDistance() : 64;
    }
}

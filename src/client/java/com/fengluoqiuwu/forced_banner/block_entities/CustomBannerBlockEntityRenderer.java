package com.fengluoqiuwu.forced_banner.block_entities;

import com.fengluoqiuwu.forced_banner.Config;
import com.mojang.datafixers.util.Pair;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BannerBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.WallBannerBlock;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.block.entity.BannerBlockEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.*;

import java.util.List;

@Environment(EnvType.CLIENT)
public class CustomBannerBlockEntityRenderer implements BlockEntityRenderer<BannerBlockEntity> {

    private static final int WIDTH = 20;
    private static final int HEIGHT = 40;
    private static final int ROTATIONS = 16;
    public static final String BANNER = "flag";
    private static final String PILLAR = "pole";
    private static final String CROSSBAR = "bar";
    private final ModelPart banner;
    private final ModelPart pillar;
    private final ModelPart crossbar;

    public CustomBannerBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        ModelPart modelPart = ctx.getLayerModelPart(EntityModelLayers.BANNER);
        this.banner = modelPart.getChild("flag");
        this.pillar = modelPart.getChild("pole");
        this.crossbar = modelPart.getChild("bar");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild("flag", ModelPartBuilder.create().uv(0, 0).cuboid(-10.0F, 0.0F, -2.0F, 20.0F, 40.0F, 1.0F), ModelTransform.NONE);
        modelPartData.addChild("pole", ModelPartBuilder.create().uv(44, 0).cuboid(-1.0F, -30.0F, -1.0F, 2.0F, 42.0F, 2.0F), ModelTransform.NONE);
        modelPartData.addChild("bar", ModelPartBuilder.create().uv(0, 42).cuboid(-10.0F, -32.0F, -1.0F, 20.0F, 2.0F, 2.0F), ModelTransform.NONE);
        return TexturedModelData.of(modelData, 64, 64);
    }

    public void render(BannerBlockEntity bannerBlockEntity, float f, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, int j) {
        List<Pair<RegistryEntry<BannerPattern>, DyeColor>> list = bannerBlockEntity.getPatterns();
        float g = 0.6666667F;
        boolean bl = bannerBlockEntity.getWorld() == null;
        matrixStack.push();
        long l;
        if (bl) {
            l = 0L;
            matrixStack.translate(0.5F, 0.5F, 0.5F);
            this.pillar.visible = true;
        } else {
            l = bannerBlockEntity.getWorld().getTime();
            BlockState blockState = bannerBlockEntity.getCachedState();
            if (blockState.getBlock() instanceof BannerBlock) {
                matrixStack.translate(0.5F, 0.5F, 0.5F);
                float h = -RotationPropertyHelper.toDegrees((Integer)blockState.get(BannerBlock.ROTATION));
                matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(h));
                this.pillar.visible = true;
            } else {
                matrixStack.translate(0.5F, -0.16666667F, 0.5F);
                float h = -((Direction)blockState.get(WallBannerBlock.FACING)).asRotation();
                matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(h));
                matrixStack.translate(0.0F, -0.3125F, -0.4375F);
                this.pillar.visible = false;
            }
        }

        matrixStack.push();
        matrixStack.scale(0.6666667F, -0.6666667F, -0.6666667F);
        VertexConsumer vertexConsumer = ModelLoader.BANNER_BASE.getVertexConsumer(vertexConsumerProvider, RenderLayer::getEntitySolid);
        this.pillar.render(matrixStack, vertexConsumer, i, j);
        this.crossbar.render(matrixStack, vertexConsumer, i, j);
        BlockPos blockPos = bannerBlockEntity.getPos();
        float k = ((float)Math.floorMod((long)(blockPos.getX() * 7 + blockPos.getY() * 9 + blockPos.getZ() * 13) + l, 100L) + f) / 100.0F;
        this.banner.pitch = (-0.0125F + 0.01F * MathHelper.cos(((float)Math.PI * 2F) * k)) * (float)Math.PI;
        this.banner.pivotY = -32.0F;
        renderCanvas(matrixStack, vertexConsumerProvider, i, j, this.banner, ModelLoader.BANNER_BASE, true, list);
        matrixStack.pop();
        matrixStack.pop();
    }

    public static void renderCanvas(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, ModelPart canvas, SpriteIdentifier baseSprite, boolean isBanner, List<Pair<RegistryEntry<BannerPattern>, DyeColor>> patterns) {
        renderCanvas(matrices, vertexConsumers, light, overlay, canvas, baseSprite, isBanner, patterns, false);
    }

    public static void renderCanvas(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, ModelPart canvas, SpriteIdentifier baseSprite, boolean isBanner, List<Pair<RegistryEntry<BannerPattern>, DyeColor>> patterns, boolean glint) {
        canvas.render(matrices, baseSprite.getVertexConsumer(vertexConsumers, RenderLayer::getEntitySolid, glint), light, overlay);

        for(int i = 0; i < 17 && i < patterns.size(); ++i) {
            Pair<RegistryEntry<BannerPattern>, DyeColor> pair = (Pair)patterns.get(i);
            float[] fs = ((DyeColor)pair.getSecond()).getColorComponents();
            ((RegistryEntry)pair.getFirst()).getKey().map((key) -> isBanner ? TexturedRenderLayers.getBannerPatternTextureId((RegistryKey<BannerPattern>) key) : TexturedRenderLayers.getShieldPatternTextureId((RegistryKey<BannerPattern>) key)).ifPresent((sprite) -> canvas.render(matrices, ((SpriteIdentifier)sprite).getVertexConsumer(vertexConsumers, RenderLayer::getEntityNoOutline), light, overlay, fs[0], fs[1], fs[2], 1.0F));
        }

    }

    @Override
    public int getRenderDistance() {
        return Config.isModEnabled() ? Config.getRenderDistance() : 64;
    }

    @Override
    public boolean isInRenderDistance(BannerBlockEntity bannerBlockEntity, Vec3d vec3d) {
        return Vec3d.ofCenter((Vec3i)bannerBlockEntity.getPos()).multiply(1.0, 0.0, 1.0).isInRange((Position)vec3d.multiply(1.0, 0.0, 1.0), (double)this.getRenderDistance());
    }
}


package com.fengluoqiuwu.forced_banner;

import com.fengluoqiuwu.forced_banner.block_entities.CustomBannerBlockEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.block.entity.BlockEntityType;

@Environment(value= EnvType.CLIENT)
public class ForcedBannerModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        // 注册命令
        ForcedBannerCommand.register();
        // 注册自定义的旗帜渲染器，使用 BlockEntityType.BANNER 作为类型
        BlockEntityRendererFactories.register(BlockEntityType.BANNER, CustomBannerBlockEntityRenderer::new);
    }
}

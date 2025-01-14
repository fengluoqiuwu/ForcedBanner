package com.fengluoqiuwu.forced_banner;

import com.fengluoqiuwu.forced_banner.block_entities.*;
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
        // block entity 渲染器注册
        block_entities_register();
    }

    private void block_entities_register() {
        BlockEntityRendererFactories.register(BlockEntityType.BANNER, CustomBannerBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(BlockEntityType.BEACON, CustomBeaconBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(BlockEntityType.BED, CustomBedBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(BlockEntityType.BELL, CustomBellBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(BlockEntityType.BRUSHABLE_BLOCK, CustomBrushableBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(BlockEntityType.CAMPFIRE, CustomCampfireBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(BlockEntityType.CHEST, CustomChestBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(BlockEntityType.CONDUIT, CustomConduitBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(BlockEntityType.DECORATED_POT, CustomDecoratedPotBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(BlockEntityType.ENCHANTING_TABLE, CustomEnchantingTableBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(BlockEntityType.END_GATEWAY, CustomEndGatewayBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(BlockEntityType.END_PORTAL, CustomEndPortalBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(BlockEntityType.HANGING_SIGN, CustomHangingSignBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(BlockEntityType.LECTERN, CustomLecternBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(BlockEntityType.MOB_SPAWNER, CustomMobSpawnerBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(BlockEntityType.PISTON, CustomPistonBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(BlockEntityType.SHULKER_BOX, CustomShulkerBoxBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(BlockEntityType.SIGN, CustomSignBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(BlockEntityType.SKULL, CustomSkullBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(BlockEntityType.STRUCTURE_BLOCK, CustomStructureBlockBlockEntityRenderer::new);
    }
}

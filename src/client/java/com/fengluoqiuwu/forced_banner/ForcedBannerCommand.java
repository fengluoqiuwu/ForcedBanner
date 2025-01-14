package com.fengluoqiuwu.forced_banner;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;


@Environment(value= EnvType.CLIENT)
public class ForcedBannerCommand {

    // 注册命令
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            // /fb enable 命令
            dispatcher.register(CommandManager.literal("fb")
                    .then(CommandManager.literal("enabled")
                            .then(CommandManager.argument("state", BoolArgumentType.bool())
                                    .executes(context -> {
                                        boolean enabled = BoolArgumentType.getBool(context, "state");

                                        // 切换模组状态
                                        Config.setModEnabled(enabled);
                                        return 1;
                                    })
                            )
                    )
            );

            // /fb distance set [64<int<=512] 命令
            dispatcher.register(CommandManager.literal("fb")
                    .then(CommandManager.literal("distance")
                            .then(CommandManager.literal("set")
                                    .then(CommandManager.argument("distance", IntegerArgumentType.integer(64, 512))
                                            .executes(context -> {
                                                int distance = IntegerArgumentType.getInteger(context, "distance");

                                                // 设置距离
                                                Config.setRenderDistance(distance);
                                                return 1;
                                            })
                                    )
                            )
                    )
            );
        });
    }
}

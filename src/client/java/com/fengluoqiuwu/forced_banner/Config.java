package com.fengluoqiuwu.forced_banner;


import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(value= EnvType.CLIENT)
public class Config {
    private static boolean isModEnabled = false;
    private static int renderDistance = 256;

    public static boolean isModEnabled() {
        return isModEnabled;
    }

    public static void setModEnabled(boolean enabled) {
        isModEnabled = enabled;
    }

    public static int getRenderDistance() {
        return renderDistance;
    }

    public static void setRenderDistance(int rd) {
        if (rd >= 64 && rd <= 512) {
            renderDistance = rd;
        }
    }
}

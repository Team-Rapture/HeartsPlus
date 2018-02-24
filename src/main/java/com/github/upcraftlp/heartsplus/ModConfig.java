package com.github.upcraftlp.heartsplus;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static com.github.upcraftlp.heartsplus.Reference.*;

/**
 * @author UpcraftLP
 */
@Config(modid = MODID, name = "craftdevmods/" + MODID) //--> /config/craftdevmods/heartsplus.cfg
@Config.LangKey("config." + MODID + ".title")
public class ModConfig {

    @Config.Comment("if enabled will show numbers instead of rendering black hearts")
    public static boolean reducedRender = false;
    //config values here
    //TODO world gen configurable

    @Mod.EventBusSubscriber
    public static class Handler {

        @SubscribeEvent
        public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
            if(event.getModID().equals(Reference.MODID)) {
                ConfigManager.sync(Reference.MODID, Config.Type.INSTANCE);
            }
        }
    }
    
}

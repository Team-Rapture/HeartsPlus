package mod.upcraftlp.heartsplus;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mod.upcraftlp.heartsplus.Reference.*;

/**
 * @author UpcraftLP
 */
@Config(modid = MODID, name = "craftdevmods/" + MODID) //--> /config/craftdevmods/heartsplus.cfg
@Config.LangKey("config." + MODID + ".title")
public class ModConfig {

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

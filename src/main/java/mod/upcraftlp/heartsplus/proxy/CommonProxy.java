package mod.upcraftlp.heartsplus.proxy;

import core.upcraftlp.craftdev.api.util.UpdateChecker;
import mod.upcraftlp.heartsplus.Reference;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

/**
 * @author UpcraftLP
 */
public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        UpdateChecker.registerMod(Reference.MODID);
    }

    public void init(FMLInitializationEvent event) {
    }

    public void postInit(FMLPostInitializationEvent event) {

    }

    public void serverStarting(FMLServerStartingEvent event) {

    }
}

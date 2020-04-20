package tfcngwaila;

import org.apache.logging.log4j.Logger;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;


@SuppressWarnings("WeakerAccess")
@Mod(modid = TFCNGWaila.MODID, name = TFCNGWaila.NAME, version = TFCNGWaila.VERSION)
public class TFCNGWaila
{
    public static final String MODID = "tfctech";
    public static final String NAME = "TFCNGWaila";
    public static final String VERSION = "@VERSION@";
    

    @Mod.Instance
    private static TFCNGWaila instance = null;
    private static Logger logger;
    private static boolean signedBuild = true;

    public static SimpleNetworkWrapper getNetwork()
    {
        return instance.network;
    }

    public static Logger getLog()
    {
        return logger;
    }

    public static TFCNGWaila getInstance()
    {
        return instance;
    }

    private SimpleNetworkWrapper network;

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        //Waila/Hwyla/TOP initialization (if present)
        FMLInterModComms.sendMessage("waila", "register", "tfcngwaila.compat.waila.WailaPlugin.callbackRegister");

    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        if (!signedBuild)
        {
            logger.error("INVALID FINGERPRINT DETECTED! This means this jar file has been compromised and are not supported.");
        }
    }
}

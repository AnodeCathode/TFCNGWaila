package tfcngwaila;

import org.apache.logging.log4j.Logger;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;



@SuppressWarnings("WeakerAccess")
@Mod(modid = TFCNGWaila.MODID, name = TFCNGWaila.NAME, version = TFCNGWaila.VERSION, dependencies = "required:tfc@[1.0.6.133,]")
public class TFCNGWaila
{
    public static final String MODID = "tfcngwaila";
    public static final String NAME = "TFCNGWaila";
    public static final String VERSION = "@VERSION@";
    

    @Mod.Instance
    private static TFCNGWaila instance = null;
    private static Logger logger;

    public static Logger getLog()
    {
        return logger;
    }

    public static TFCNGWaila getInstance()
    {
        return instance;
    }



    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        //Waila/initialization
        FMLInterModComms.sendMessage("waila", "register", "tfcngwaila.compat.waila.WailaBlocksTFC.callbackRegister");

    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
    }
}

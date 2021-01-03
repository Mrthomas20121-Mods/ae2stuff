package mrthomas20121.ae2stuff;

import mrthomas20121.ae2stuff.util.RegistryHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = Ae2Stuff.MODID, name = Ae2Stuff.NAME, version = Ae2Stuff.VERSION,
        dependencies = "required-after:forge@[14.23.5.2847,);"
        + "required-after:appliedenergistics2;")
public class Ae2Stuff
{
    @Mod.Instance
    public static Ae2Stuff instance;
    public static final String MODID = "ae2stuff";
    public static final String NAME = "Ae2 Stuff";
    public static final String VERSION = "0.0.1";

    private static Logger logger;

    public static Logger getLogger() {
        return logger;
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(RegistryHandler.class);
   }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
    }
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    }

}

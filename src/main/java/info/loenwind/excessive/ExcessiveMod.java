package info.loenwind.excessive;

import javax.annotation.Nonnull;

import org.apache.logging.log4j.Logger;

import info.loenwind.excessive.commands.ExcessiveCommand;
import info.loenwind.excessive.config.ConfigHandler;
import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = ExcessiveMod.MODID, version = ExcessiveMod.VERSION, name = ExcessiveMod.MODID, serverSideOnly = true, acceptableRemoteVersions = "*")
@EventBusSubscriber
public class ExcessiveMod {

  public static final String MODID = "excessive";
  public static final String VERSION = "1.0.0";

  public static Logger LOG;

  public static ConfigHandler CONFIGHANDLER;

  @EventHandler
  public void preinit(FMLPreInitializationEvent event) {
    LOG = event.getModLog();
    CONFIGHANDLER = new ConfigHandler(MODID, LOG);
    CONFIGHANDLER.init(event);
  }

  @SubscribeEvent
  public static void registerBlocks(@Nonnull RegistryEvent.Register<Block> event) {
  }

  @EventHandler
  public void init(FMLInitializationEvent event) {
  }

  @EventHandler
  public void onServerStart(FMLServerStartingEvent event) {
    event.registerServerCommand(new ExcessiveCommand());
  }

}

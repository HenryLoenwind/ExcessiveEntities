package info.loenwind.excessive.config;

import java.io.File;

import org.apache.logging.log4j.Logger;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ConfigHandler {

  private final String modid;
  private final Logger log;

  static {
    loadAll();
  }

  // ****************************************************************************************

  public static Configuration configuration;
  public static File configDirectory;

  public ConfigHandler(String modid, Logger log) {
    this.modid = modid;
    this.log = log;
  }

  public void init(FMLPreInitializationEvent event) {
    configDirectory = new File(event.getModConfigurationDirectory(), modid.toLowerCase());
    if (!configDirectory.exists()) {
      configDirectory.mkdir();
    }

    File configFile = new File(configDirectory, modid + ".cfg");
    configuration = new Configuration(configFile);
    syncConfig(false);
  }

  private void syncConfig(boolean load) {
    try {
      if (load) {
        configuration.load();
      }
      processConfig();
    } catch (Exception e) {
      log.error(modid + " has a problem loading its configuration");
      e.printStackTrace();
    } finally {
      if (configuration.hasChanged()) {
        configuration.save();
      }
    }
  }

  private static void processConfig() {
    loadAll(configuration);
    if (configuration.hasChanged()) {
      configuration.save();
    }
  }

  public static void loadAll(Configuration config) {
    for (Config value : Config.values()) {
      value.load(config);
    }
  }

  public static void loadAll() {
    for (Config value : Config.values()) {
      value.resetToDefault();
    }
  }

}

package cc.koffeecreations.main.Sickle;


import java.io.File;
import java.io.IOException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Config {
  private JavaPlugin main;
  
  private String name;
  
  private String folder;
  
  public File file;
  
  public Config(JavaPlugin plugin, String name) {
    this.main = plugin;
    this.name = name;
    this.file = new File(this.main.getDataFolder(), String.valueOf(name) + ".yml");
    YamlConfiguration config = YamlConfiguration.loadConfiguration(this.file);
    try {
      config.save(this.file);
    } catch (IOException iOException) {}
  }
  
  public Config(JavaPlugin plugin, String folder, String name) {
    this.main = plugin;
    this.name = name;
    this.folder = folder;
    this.file = new File(this.main.getDataFolder() + File.separator + folder, String.valueOf(name) + ".yml");
    YamlConfiguration config = YamlConfiguration.loadConfiguration(this.file);
    try {
      config.save(this.file);
    } catch (IOException iOException) {}
  }
  
  public YamlConfiguration yml() {
    YamlConfiguration config = YamlConfiguration.loadConfiguration(this.file);
    return config;
  }
  
  public Object get(String path) {
    YamlConfiguration config = YamlConfiguration.loadConfiguration(this.file);
    return config.get(path);
  }
  
  public boolean set(String path, Object object) {
    YamlConfiguration config = YamlConfiguration.loadConfiguration(this.file);
    config.set(path, object);
    try {
      config.save(this.file);
      return true;
    } catch (IOException e) {
      return false;
    } 
  }
  
  public boolean save() {
    YamlConfiguration config = YamlConfiguration.loadConfiguration(this.file);
    try {
      config.save(this.file);
      return true;
    } catch (IOException e) {
      return false;
    } 
  }
}


package cc.koffeecreations.main.unknow;

import org.bukkit.configuration.file.FileConfiguration;

public class AreaStyle {
    String strokecolor;
    String unownedstrokecolor;
    double strokeopacity;
    int strokeweight;
    String fillcolor;
    double fillopacity;
    String label;
    
    AreaStyle(FileConfiguration cfg, String path, AreaStyle def) {
      this.strokecolor = cfg.getString(path + ".strokeColor", def.strokecolor);
      this.unownedstrokecolor = cfg.getString(path + ".unownedStrokeColor", def.unownedstrokecolor);
      this.strokeopacity = cfg.getDouble(path + ".strokeOpacity", def.strokeopacity);
      this.strokeweight = cfg.getInt(path + ".strokeWeight", def.strokeweight);
      this.fillcolor = cfg.getString(path + ".fillColor", def.fillcolor);
      this.fillopacity = cfg.getDouble(path + ".fillOpacity", def.fillopacity);
      this.label = cfg.getString(path + ".label", null);
    }
    
    AreaStyle(FileConfiguration cfg, String path) {
      this.strokecolor = cfg.getString(path + ".strokeColor", "#FF0000");
      this.unownedstrokecolor = cfg.getString(path + ".unownedStrokeColor", "#00FF00");
      this.strokeopacity = cfg.getDouble(path + ".strokeOpacity", 0.8D);
      this.strokeweight = cfg.getInt(path + ".strokeWeight", 3);
      this.fillcolor = cfg.getString(path + ".fillColor", "#FF0000");
      this.fillopacity = cfg.getDouble(path + ".fillOpacity", 0.35D);
    }
  }

package com.toeikanta.acidworldgenerator;

import org.bukkit.potion.PotionEffectType;
import java.util.ArrayList;
import java.util.List;

public class Settings {
    // ---------------------------------------------
    private String worldName = "acid_sky_world";
    public static int islandNum = 5;
    /*      ACID        */
    private boolean acidDamageOp = true;
    private boolean acidDamageChickens = false;
    // Damage
    private int acidDamage = 5;
    private int acidDamageMonster = 5;
    private int acidDamageAnimal = 1;
    private long acidDestroyItemTime = 0;
    private int acidRainDamage = 1;
    private long acidDamageDelay = 1;
    private List<PotionEffectType> acidEffects = new ArrayList<>();
    private int acidEffectDuation = 10;
    private List<PotionEffectType> acidRainEffects = new ArrayList<>();
    private boolean acidDamageSnow = false;
    private int rainEffectDuation = 10;
    private boolean helmetProtection = false;
    private boolean fullArmorProtection = true;

    public Settings(){
//         Available effects are:
//    #    BLINDNESS
//    #    CONFUSION
//    #    HUNGER
//    #    POISON
//    #    SLOW
//    #    SLOW_DIGGING
//    #    WEAKNESS
        acidEffects.add(PotionEffectType.getByName("WEAKNESS"));
        acidEffects.add(PotionEffectType.getByName("BLINDNESS"));

        acidRainEffects.add(PotionEffectType.getByName("POISON"));
        acidRainEffects.add(PotionEffectType.getByName("WEAKNESS"));
        acidRainEffects.add(PotionEffectType.getByName("SLOW"));
    }

    public int getAcidRainDamage() {
        return acidRainDamage;
    }

    public void setAcidRainDamage(int acidRainDamage) {
        this.acidRainDamage = acidRainDamage;
    }

    public int getAcidDamage() {
        return acidDamage;
    }

    public void setAcidDamage(int acidDamage) {
        this.acidDamage = acidDamage;
    }

    public boolean isAcidDamageOp() {
        return acidDamageOp;
    }

    public void setAcidDamageOp(boolean acidDamageOp) {
        this.acidDamageOp = acidDamageOp;
    }

    public boolean isAcidDamageChickens() {
        return acidDamageChickens;
    }

    public int getAcidDamageMonster() {
        return acidDamageMonster;
    }

    public int getAcidDamageAnimal() {
        return acidDamageAnimal;
    }

    public long getAcidDestroyItemTime() {
        return acidDestroyItemTime;
    }

    public boolean isAcidDamageSnow() {
        return acidDamageSnow;
    }

    public long getAcidDamageDelay() {
        return acidDamageDelay;
    }

    public List<PotionEffectType> getAcidRainEffects() {
        return acidRainEffects;
    }

    public int getRainEffectDuation() {
        return rainEffectDuation;
    }

    public List<PotionEffectType> getAcidEffects() {
        return acidEffects;
    }

    public int getAcidEffectDuation() {
        return acidEffectDuation;
    }

    public boolean isHelmetProtection() {
        return helmetProtection;
    }

    public boolean isFullArmorProtection() {
        return fullArmorProtection;
    }

    public String getWorldName() {
        return worldName;
    }
}

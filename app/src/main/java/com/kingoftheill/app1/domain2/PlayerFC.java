package com.kingoftheill.app1.domain2;

import com.kingoftheill.app1.domain.model.utilities.PlayerLevels;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Andrade on 06/12/2017.
 */

public class PlayerFC {

    private String name;
    private String image;
    private String messId;

    //CORE_STATS
    private int life;
    private int range;
    private int damage;
    private int resistance;

    //BATTLE
    private int btAttack;
    private int btDefense;

    //COMMONS_STATS
    private int level;
    private int currXP;

    //DISEASE
    private int type;
    private String disImage;

    //DISEASE COMMONS_STATS
    private int disLevel;
    private int disCurrXP;

    //DISEASE STATS
    private int disRange;
    private int disDamage;
    private int disResistence;
    private int disBtAttack;
    private int disBtDefense;

    private int numUpgrades;

    //Infection
    private HashMap<String, Object> infection1;
    private HashMap<String, Object> infection2;

    public PlayerFC() {
    }

    public PlayerFC(String name, String image, String messId) {
        setName(name);
        setMessId(messId);
        setImage(image);
        setLevel(PlayerLevels.LEVEL_1.level());
        setCurrXP(0);
        setDamage(PlayerLevels.LEVEL_1.damage());
        setLife(PlayerLevels.LEVEL_1.life());
        setRange(PlayerLevels.LEVEL_1.range());
        setResistance(PlayerLevels.LEVEL_1.resistance());
    }

    public static Map<String, Object> setDis(int type) {
        Map<String,Object> map = new HashMap<>();
        map.put("type", type);
        map.put("disLevel", 1);
        map.put("disCurrXP", 0);
        map.put("disRange", 0);
        map.put("disDamage", 0);
        map.put("disResistence", 0);
        map.put("disBtAttack", 1);
        map.put("disBtDefense", 1);
        map.put("numUpgrades", 0);
        return map;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessId() {
        return messId;
    }

    public void setMessId(String messId) {
        this.messId = messId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life += life;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range += range;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage += damage;
    }

    public int getResistance() {
        return resistance;
    }

    public void setResistance(int resistance) {
        this.resistance += resistance;
    }

    public int getBtAttack() {
        return btAttack;
    }

    public void setBtAttack(int btAttack) {
        this.btAttack += btAttack;
    }

    public int getBtDefense() {
        return btDefense;
    }

    public void setBtDefense(int btDefense) {
        this.btDefense += btDefense;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getCurrXP() {
        return currXP;
    }

    public void setCurrXP (int xpGained) {
        currXP += xpGained;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDisImage() {
        return disImage;
    }

    public void setDisImage(String disImage) {
        this.disImage = disImage;
    }

    public int getDisLevel() {
        return disLevel;
    }

    public void setDisLevel(int disLevel) {
        this.disLevel = disLevel;
    }

    public int getDisCurrXP() {
        return disCurrXP;
    }

    public void setDisCurrXP(int disCurrXP) {
        this.disCurrXP += disCurrXP;
    }

    public int getDisRange() {
        return disRange;
    }

    public void setDisRange(int disRange) {
        this.disRange += disRange;
    }

    public int getDisDamage() {
        return disDamage;
    }

    public void setDisDamage(int disDamage) {
        this.disDamage += disDamage;
    }

    public int getDisResistence() {
        return disResistence;
    }

    public void setDisResistence(int disResistence) {
        this.disResistence += disResistence;
    }

    public int getDisBtAttack() {
        return disBtAttack;
    }

    public void setDisBtAttack(int disBtAttack) {
        this.disBtAttack += disBtAttack;
    }

    public int getDisBtDefense() {
        return disBtDefense;
    }

    public void setDisBtDefense(int disBtDefense) {
        this.disBtDefense += disBtDefense;
    }

    public int getNumUpgrades() {
        return numUpgrades;
    }

    public void setNumUpgrades(int numUpgrades) {
        this.numUpgrades += numUpgrades;
    }

    public void levelUpDis() { this.disLevel++; }

    public void levelUp() { this.level++; }

    public void reduceLife (int reduction) {
        life-=reduction;
    }

    public HashMap<String, Object> getInfection1() {
        return infection1;
    }

    public void setInfection1(int damage, String ref, int type) {
        HashMap<String, Object> infection1 = new HashMap<>();
        infection1.put("ref", ref);
        infection1.put("damage", damage);
        infection1.put("type", type);
        this.infection1 = infection1;
    }

    public HashMap<String, Object> getInfection2() {
        return infection2;
    }

    public void setInfection2(int damage, String ref, int type) {
        HashMap<String, Object> infection2 = new HashMap<>();
        infection2.put("ref", ref);
        infection2.put("damage", damage);
        infection2.put("type", type);
        this.infection2 = infection2;
    }

    public static HashMap<String, Object> newInfection1(int damage, String ref, int type) {
        HashMap<String, Object> infec = new HashMap<>();
        infec.put("infection1.ref", ref);
        infec.put("infection1.damage", damage);
        infec.put("infection1.type", type);
        return infec;
    }

    public static HashMap<String, Object> newInfection2(int damage, String ref, int type) {
        HashMap<String, Object> infec = new HashMap<>();
        infec.put("infection2.ref", ref);
        infec.put("infection2.damage", damage);
        infec.put("infection2.type", type);
        return infec;
    }

    public void upgrade (String stat) {
        switch (stat) {
            case "disRange":
                disRange++;
                break;

            case "disDamage":
                disDamage++;
                break;

            case "disResistence":
                disResistence++;
                break;

            case "disBtAttack":
                disBtAttack++;
                break;

            case "disBtDefense":
                disBtDefense++;
                break;
        }
    }

    public int getTotalRange() { return range+disRange;}

    public int getTotalResistance() { return resistance + disResistence;}

    public int getTotalDamage() { return damage + disDamage; }

    public int getTotalBtAttack() { return btAttack + disBtAttack; }

    public int getTotalBtDefense() { return btDefense + disBtDefense;}

}

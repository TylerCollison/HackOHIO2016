package com.ohio.hack.hackohio2016;

/**
 * Created by Tyler Collison on 11/19/2016.
 */
public class Skills {
    private String owningUserame = "";
    private String skillType = "";
    private String skillDescription = "";

    public void setUsername (String username) {
        owningUserame = username;
    }

    public void setSkill (String skill) {
        skillType = skill;
    }

    public void setDescription (String description) {
        skillDescription = description;
    }

    public String getUsername () {
        return owningUserame;
    }

    public String getSkill () {
        return skillType;
    }

    public String getDescription () {
        return skillDescription;
    }
}

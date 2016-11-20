package com.ohio.hack.hackohio2016;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tyler Collison on 11/19/2016.
 */
public class Skill {
    private String ownerUserame = "";
    private String skillType = "";
    private String skillDescription = "";
    private String ownerEmail = "";
    private List<String> ownerInterests = new ArrayList<>();

    public void setEmail (String email) {
        ownerEmail = email;
    }

    public void addInterest (String interest) {
        ownerInterests.add(interest);
    }

    public void setUsername (String username) {
        ownerUserame = username;
    }

    public void setSkill (String skill) {
        skillType = skill;
    }

    public void setDescription (String description) {
        skillDescription = description;
    }

    public String getEmail () {
        return ownerEmail;
    }

    public String getUsername () {
        return ownerUserame;
    }

    public List<String> getInterests () {
        return ownerInterests;
    }

    public String getSkill () {
        return skillType;
    }

    public String getDescription () {
        return skillDescription;
    }
}

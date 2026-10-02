package com.example.appmarvel;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class SuperheroSearchResponse {
    @SerializedName("response")
    private String response;
    
    @SerializedName("results")
    private List<SuperheroCharacter> results;

    public String getResponse() { return response; }
    public List<SuperheroCharacter> getResults() { return results; }

    public static class SuperheroCharacter {
        private String id;
        private String name;
        private Powerstats powerstats;
        private Biography biography;
        private Connections connections;

        public String getId() { return id; }
        public String getName() { return name; }
        public Powerstats getPowerstats() { return powerstats; }
        public Biography getBiography() { return biography; }
        public Connections getConnections() { return connections; }
    }

    public static class Powerstats {
        private String intelligence;
        private String strength;
        private String speed;
        private String durability;
        private String power;
        private String combat;

        public String getIntelligence() { return intelligence; }
        public String getStrength() { return strength; }
        public String getSpeed() { return speed; }
        public String getDurability() { return durability; }
        public String getPower() { return power; }
        public String getCombat() { return combat; }
    }

    public static class Biography {
        @SerializedName("full-name")
        private String fullName;
        private String publisher;
        private String alignment;

        public String getFullName() { return fullName; }
        public String getPublisher() { return publisher; }
        public String getAlignment() { return alignment; }
    }

    public static class Connections {
        @SerializedName("group-affiliation")
        private String groupAffiliation;

        public String getGroupAffiliation() { return groupAffiliation; }
    }
}

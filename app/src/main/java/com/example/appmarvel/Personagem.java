package com.example.appmarvel;

import java.util.List;

public class Personagem {
    // Informações Básicas (Identity)
    private String name;
    private String id;
    private String status;
    private String origin;
    private String rank;

    // Atributos Físicos (%)
    private int strength;
    private int agility;

    // Atributos Cognitivos (%)
    private int intelligence;
    private int leadership;

    // Arquivo do Indivíduo (Listas)
    private List<String> affiliations;
    private List<String> powers;
    private List<Mission> missions;

    // Construtor Completo
    public Personagem(String name, String id, String status, String origin, String rank,
                          int strength, int agility, int intelligence, int leadership,
                          List<String> affiliations, List<String> powers, List<Mission> missions) {
        this.name = name;
        this.id = id;
        this.status = status;
        this.origin = origin;
        this.rank = rank;
        this.strength = strength;
        this.agility = agility;
        this.intelligence = intelligence;
        this.leadership = leadership;
        this.affiliations = affiliations;
        this.powers = powers;
        this.missions = missions;
    }

    // Getters e Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getOrigin() { return origin; }
    public void setOrigin(String origin) { this.origin = origin; }

    public String getRank() { return rank; }
    public void setRank(String rank) { this.rank = rank; }

    public int getStrength() { return strength; }
    public void setStrength(int strength) { this.strength = strength; }

    public int getAgility() { return agility; }
    public void setAgility(int agility) { this.agility = agility; }

    public int getIntelligence() { return intelligence; }
    public void setIntelligence(int intelligence) { this.intelligence = intelligence; }

    public int getLeadership() { return leadership; }
    public void setLeadership(int leadership) { this.leadership = leadership; }

    public List<String> getAffiliations() { return affiliations; }
    public void setAffiliations(List<String> affiliations) { this.affiliations = affiliations; }

    public List<String> getPowers() { return powers; }
    public void setPowers(List<String> powers) { this.powers = powers; }

    public List<Mission> getMissions() { return missions; }
    public void setMissions(List<Mission> missions) { this.missions = missions; }

    // Subclasse Interna para representar as Missões
    public static class Mission {
        private String year;
        private String title;
        private String description;

        public Mission(String year, String title, String description) {
            this.year = year;
            this.title = title;
            this.description = description;
        }

        public String getYear() { return year; }
        public String getTitle() { return title; }
        public String getDescription() { return description; }
    }
}

package com.example.winecompliance.model;

import java.util.List;

public class WineBreakdown {
    public String breakDownType;

    public List<Percentage> breakdown;

    public WineBreakdown(String breakDownType, List<Percentage> breakdown){
        this.breakDownType = breakDownType;
        this.breakdown = breakdown;
    }

    public static class Percentage {
        public String key;
        public Double percentage;

        public Percentage(String key, Double percentage){
            this.key = key;
            this.percentage = percentage;
        }
    }

}

package com.example.winecompliance.model;

import java.util.Objects;

public class WineComponent {
    public float percentage;
    public int year;
    public String variety;
    public String region;

    public WineComponent(){}

    public static class YearVarietyKey {
        private int year;
        private String variety;

        public YearVarietyKey(WineComponent wineComponent) {
            this.year = wineComponent.year;
            this.variety = wineComponent.variety;
        }

        @Override
        public String toString() {
            return this.year + "-" + this.variety;
        }

        @Override
        public boolean equals(Object object) {
            if (object == this) {
                return true;
            }

            if (!(object instanceof YearVarietyKey)) {
                return false;
            }

            YearVarietyKey other = (YearVarietyKey) object;
            return this.year == other.year && Objects.equals(this.variety, other.variety);
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 53 * hash + this.year;
            hash = 53 * hash + (this.variety != null ? this.variety.hashCode() : 0);
            return hash;
        }
    }
}

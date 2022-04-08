package com.example.winecompliance.model;

import java.util.List;

public class Wine {
    public String lotCode;
    public float volume;
    public String description;
    public String tankCode;
    public String productState;
    public String ownerName;
    public List<WineComponent> components;

    public Wine() {
    }

}

package tn.esprit.foodjoy.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum TableStatus {
    @JsonProperty("free")
    FREE,

    @JsonProperty("occupied")
    OCCUPIED,

    @JsonProperty("reserved")
    RESERVED,

    @JsonProperty("cleaning")
    CLEANING
}
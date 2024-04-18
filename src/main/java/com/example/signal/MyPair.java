package com.example.signal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MyPair {

    @JsonProperty
    int toNode ;
    @JsonProperty
    double gain;

    public MyPair(int toNode, double gain) {
        this.toNode = toNode;
        this.gain = gain;
    }
}

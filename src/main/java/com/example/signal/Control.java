package com.example.signal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@CrossOrigin("api")
@Controller
@RestController


public class Control {

    SignalFLow signalFlow = new SignalFLow();


    @GetMapping("/allKthNonTouchingloops")
    public ArrayList<ArrayList<ArrayList<Integer>>> allKthNonTouchingloops(@RequestParam  int k) {
        return signalFlow.nonTouchingLoops.get(k-2);
    }

    @GetMapping("/paths")
    public ArrayList<ArrayList<Integer>> getPaths() {
        return signalFlow.paths;
    }

    @GetMapping("/allIndividualsLoops")
    public ArrayList<ArrayList<Integer>> getLoops() {
        return signalFlow.loops;
    }

    @GetMapping("/allDeltasOfAllPaths")
    public ArrayList<Double> allDeltasOfAllPaths() {
        return signalFlow.allDeltasOfAllPaths;
    }

    @GetMapping("/delta")
    public Double getDelta() {
        return signalFlow.getDelta();
    }

    @GetMapping("/transferFunction")
    public Double transferFunction() {
        return signalFlow.overAllTransferFunction();
    }


    @PostMapping("/run")
    void runAllGragh(@RequestBody  ArrayList<ArrayList<ArrayList<Integer>>> postedGraph) {
        signalFlow.init(postedGraph);
        signalFlow.overAllTransferFunction();
    }

}

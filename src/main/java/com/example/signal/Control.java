package com.example.signal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@CrossOrigin("api")
@Controller
@RestController


public class Control {

    SignalFLow signalFlow = new SignalFLow();


    @GetMapping("/allKthNonTouchingloops")
    public ArrayList<ArrayList<ArrayList<Integer>>> allKthNonTouchingloops(int k) {
        return signalFlow.nonTouchingLoops.get(k);
    }

    @GetMapping("/paths")
    public ArrayList<ArrayList<Integer>> getPaths() {
        return signalFlow.paths;
    }

    @GetMapping("/allLoops")
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


    @PutMapping()
    void runAllGragh() {
        signalFlow.overAllTransferFunction();
    }

}

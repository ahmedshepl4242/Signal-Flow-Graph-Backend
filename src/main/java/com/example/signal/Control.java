package com.example.signal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

//<<<<<<< HEAD
//@CrossOrigin()
//@Controller
//@RestController
//=======
@RestController
@CrossOrigin(value = "http://localhost:8081/")
@RequestMapping()
//>>>>>>> 0a0d3e7418122534528709d923c957bfd8913172

public class Control {
@Autowired
    SignalFLow signalFlow;


    @PostMapping("/receiveGraph")
    void runAllGragh(@RequestBody  ArrayList<ArrayList<ArrayList<Integer>>> postedGraph) {
        signalFlow.clear();
        signalFlow.init(postedGraph);
        signalFlow.overAllTransferFunction();
    }

    @GetMapping("/paths")
    public ArrayList<ArrayList<Integer>> getPaths() {
        return signalFlow.paths;
    }

    @GetMapping("/allIndividualsLoops")
    public ArrayList<ArrayList<Integer>> getLoops() {
        return signalFlow.loops;
    }

    @GetMapping("/allKthNonTouchingloops")
    public ArrayList<ArrayList<ArrayList<Integer>>> allKthNonTouchingloops(@RequestParam Integer k) {
        return signalFlow.nonTouchingLoops.get(k-2);
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


}

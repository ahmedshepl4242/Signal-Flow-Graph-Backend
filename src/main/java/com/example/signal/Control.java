package com.example.signal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@CrossOrigin("api")
@Controller
@RestController


public class Control {

    SignalFLow signalFlow = new SignalFLow();

    @PostMapping("/find-loops-paths")
    public void findLoopsPaths(@RequestBody ArrayList<ArrayList<ArrayList<Integer>>> graph) {
       signalFlow.init(graph);
        signalFlow.findPathsGains();

    }

//    @GetMapping("/loops")
//    public ArrayList<ArrayList<Integer>> getLoops() {
//        return signalFlow.findLoopsGains(0,1);
//    }

    //    @GetMapping("/paths")
//    public ArrayList<ArrayList<Integer>> getPaths() {
//
//        return signalFlow.dfs(0,1);
//    }
    @PutMapping()
    void put() {
        signalFlow.findPathsGains();
        signalFlow.combine();

        signalFlow.printLoops();
        signalFlow.computeDelta();

    }

}

package com.example.signal;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Stack;

@Service
public class SignalFLow {


    ArrayList<ArrayList<MyPair>> graph = new ArrayList<>();
    ArrayList<Boolean> in;
    //    ArrayList<Boolean> out;
    ArrayList<Boolean> vis;
    Stack<Integer> st = new Stack<Integer>();
    Stack<Integer> temp = new Stack<Integer>();
    ArrayList<ArrayList<ArrayList<ArrayList<Integer>>>> nonTouchingLoops = new ArrayList<>();
    ArrayList<ArrayList<Double>> allNonTouchingGains = new ArrayList<>();


    int[][][] numbersArray = {{{1, 3}}, // Numbers for index 0
            {{2, 4}, {3, 5}, {6, 3}},    // Numbers for index 1
            {{1, 4}, {3, 2}},    // Numbers for index 1
            {{2, 4}, {4, 2}},    // Numbers for index 1
            // Add more arrays for additional indices as needed
            {{3, 45}, {5, 2}}, // Numbers for index 2
            {{4, 45}, {6, 2}}, // Numbers for index 2
            {{6, 45}, {7, 2}, {5, 2}, {4, 2}}, // Numbers for index 2
            {},
            // Numbers for index 2
    };

    ArrayList<ArrayList<Integer>> paths = new ArrayList<>();
    ArrayList<Double> pathsGains = new ArrayList<>();
    ArrayList<Double> allDeltasOfAllPaths = new ArrayList<>();
    ArrayList<Double> loopsGains = new ArrayList<>();
    ArrayList<ArrayList<Integer>> loops = new ArrayList<>();

    static double delta = 0;
    static double transferFunction = 0;

    double computeDelta() {

//        compute indivisual loops

        double delta = 1;

        for (int i = 0; i < loopsGains.size(); i++) {
            delta -= loopsGains.get(i);
        }
        boolean alter = true;
        double sign = 1;

        for (ArrayList<Double> k : allNonTouchingGains) {
            if (alter) {
                sign = 1;
                alter = false;
            } else {
                sign = -1;
                alter = true;
            }
            for (Double ele : k) {
                delta += sign * ele;
            }
        }

        System.out.println(delta);

        return delta;
    }


    boolean checkTouch(ArrayList<Integer> lp) {

        for (Integer node : lp) {
            if (vis.get(node)) {
                return true;
            }
        }
        return false;

    }

    void makeAllNodesVisted(ArrayList<Integer> lp) {

        for (Integer node : lp) {
            vis.set(node, true);
        }

    }

    void makeAllNodesNotVisted(ArrayList<Integer> lp) {

        for (Integer node : lp) {
            vis.set(node, false);
        }

    }

    public void combine() {
        int n = loops.size();
//        ArrayList<ArrayList<Integer>> comb;
        ArrayList<ArrayList<Integer>> vc = new ArrayList<>();


        int k = 2;

        while (k < 100) {
            int gain = 1;
            nonTouchingLoops.add(new ArrayList<>());
            allNonTouchingGains.add(new ArrayList<>());


            rec(0, n, k, vc, gain);
            if (nonTouchingLoops.get(nonTouchingLoops.size() - 1).size() == 0) {
                System.out.println("there is no more notTiuching loops  of" + k);
                break;
            }
            k++;
        }
    }

    void rec(int number, int n, int k, ArrayList<ArrayList<Integer>> sol, double gain) {
        if (sol.size() == k) {
            nonTouchingLoops.get(k - 2).add(new ArrayList<>(sol));
            allNonTouchingGains.get(k - 2).add(gain);
            return;
        }
        for (int i = number; i < n; i++) {
            if (checkTouch(loops.get(i))) {
                continue;
            }
//            System.out.println(i);
            sol.add(loops.get(i));
            gain *= loopsGains.get(i);
            makeAllNodesVisted(loops.get(i));
            rec(i + 1, n, k, sol, gain);
            makeAllNodesNotVisted(loops.get(i));
            gain /= loopsGains.get(i);
            sol.remove(loops.get(i));
        }
    }


    void init(ArrayList<ArrayList<ArrayList<Integer>>> postedGraph) {
        in = new ArrayList<>();
        vis = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            vis.add(false);
            in.add(false);
        }

        // clear the existing graph each time we get a graph
        graph.clear();

        // add the posted graph to the graph ArrayList
        for (ArrayList<ArrayList<Integer>> innerList : postedGraph) {
            ArrayList<MyPair> myPairList = new ArrayList<>();
            for (ArrayList<Integer> pair : innerList) {
                myPairList.add(new MyPair(pair.get(0), pair.get(1)));
            }
            graph.add(myPairList);
        }
    }


    void findAllPaths(int i, double gain) {

        in.set(i, true);
        st.push(i);
        for (MyPair p : graph.get(i)) {
            if (!in.get(p.toNode)) {
                gain *= p.gain;
                findAllPaths(p.toNode, gain);
                gain /= p.gain;
            }
        }

        if (i == (numbersArray.length - 1)) {

            paths.add(new ArrayList<>());
            int last = paths.size() - 1;
            for (Integer node : st) {
                paths.get(last).add(node);
            }
            pathsGains.add(gain);
        }
        st.pop();
        in.set(i, false);
//        vis.set(i, true);
    }

    void print() {

        int i = 0;

        for (ArrayList<Integer> p : paths) {


            System.out.println("path :  ");

            for (Integer node : p) {
                System.out.print(node + " ,");
            }
            System.out.print("  path gain is " + pathsGains.get(i));
            System.out.println();
            i++;
        }
    }

    void printLoops() {

        System.out.println(loops.size());
        int i = 0;

        for (ArrayList<Integer> p : loops) {


            System.out.println("loop :  ");

            for (Integer node : p) {
                System.out.print(node + " ,");
            }
            System.out.print("  loop gain is " + loopsGains.get(i));
            System.out.println();
            i++;
        }
        System.out.println("every two loops");
        int j = 0;

//        all non touching loops
        for (ArrayList<ArrayList<ArrayList<Integer>>> vc : nonTouchingLoops) {
            System.out.println(vc.size());
            i = 0;

            for (ArrayList<ArrayList<Integer>> v : vc) {


                for (ArrayList<Integer> vcm : v) {
                    System.out.println(vcm.toString());

                }
                System.out.println("gain is " + allNonTouchingGains.get(j).get(i) + "-------------------------------------");
                i++;

            }
            j++;
        }

    }


    void findLoopsGains(int i) {
        in.set(i, true);
        st.push(i);

        for (MyPair p : graph.get(i)) {
            if (!in.get(p.toNode)) {
                findLoopsGains(p.toNode);
            } else if (st.contains(p.toNode)) {
//                ArrayList<Integer> curLoop = new ArrayList<>(st.subList(st.indexOf(p.toNode), st.size()));
//                curLoop.add(p.toNode);

                ArrayList<Integer> curLoop = new ArrayList<>();
                int nodeIndex = st.indexOf(p.toNode);
                for (int j = nodeIndex; j <= st.size() - 1; j++) {
                    curLoop.add(st.get(j));
                }
//                curLoop.add(p.toNode); // add the node causing the curLoop
                // add the loop gain to the stack
                double loopGain = 1.0;
                for (Integer node : curLoop) {
                    // find the pair corresponding to the transition from 'node' to the next node in the loop
                    for (MyPair pair : graph.get(node)) {
                        if (pair.toNode == curLoop.get((curLoop.indexOf(node) + 1) % curLoop.size())) {
                            loopGain *= pair.gain;
                            break;
                        }
                    }
                }
                // check if the curLoop already exists
                boolean loopExists = false;
                for (ArrayList<Integer> existingLoop : loops) {
                    if (existingLoop.size() == curLoop.size()) {
                        boolean flag = false;
                        for (Integer node : existingLoop) {
                            if (!curLoop.contains(node)) {
                                flag = true;
                                break;
                            }
                        }
                        if (!flag) {
                            loopExists = true;
                            break;
                        }
                    }
                }
                // add the curLoop only if it doesn't already exist
                if (!loopExists) {
                    loops.add(curLoop);
                    loopsGains.add(loopGain);
                }
            }
        }
        st.pop();
        in.set(i, false);
    }

    double computeDeltaForPathK(int i) {
        for (Integer n : paths.get(i)) {
            vis.set(n, true);
        }
        double gainTotall = getDelta();
        double sign = -1;
        int j = 0;

        int index = 0;
        for (ArrayList<Integer> lp : loops) {
            boolean touch = false;
            for (Integer ele : lp) {
                if (vis.get(ele)) {
                    touch = true;
                    gainTotall += loopsGains.get(index);
                    break;
                }
            }
            index++;
        }
        for (ArrayList<ArrayList<ArrayList<Integer>>> vc : nonTouchingLoops) {
            int k = 0;
            for (ArrayList<ArrayList<Integer>> v : vc) {
                for (ArrayList<Integer> vcm : v) {
                    boolean flag = false;
                    for (Integer e : vcm) {
                        if (vis.get(e)) {
                            flag = true;
                            break;
                        }
                    }
                    if (flag) {
                        gainTotall -= Math.pow(sign, j) * allNonTouchingGains.get(j).get(k);
                        break;
                    }
                }
                k++;
//                System.out.println("-------------------------------------");
            }
            j++;

        }
        allDeltasOfAllPaths.add(gainTotall);
        return gainTotall * pathsGains.get(i);

    }

    void intiAllArrayList() {

        findAllPaths(0, 1);
        findLoopsGains(0);
        combine();
        printLoops();
        print();
    }

    double overAllTransferFunction() {
        intiAllArrayList();
        delta = getDelta();
        double numerator = 1;
        for (int i = 0; i < paths.size(); i++) {
            numerator += computeDeltaForPathK(i);
        }
        System.out.println("  numerator is " + numerator + " deltat is " + getDelta());
        transferFunction = numerator / getDelta();
        return numerator / getDelta();

    }

    double getDelta() {
        if (delta == 0) {
            delta = computeDelta();
        }
        return delta;
    }

}

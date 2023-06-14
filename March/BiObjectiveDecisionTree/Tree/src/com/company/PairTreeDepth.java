package com.company;

import com.sun.jdi.event.StepEvent;

import java.util.TreeSet;

public class PairTreeDepth{
    TreeSet<Integer> indices;
    int depth;

    public PairTreeDepth(TreeSet indices, int depth) {
        this.indices = indices;
        this.depth = depth;
    }
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(Integer i : indices){
            sb.append(i);
        }
        sb.append("depth");
        sb.append(depth);
        System.out.println(sb);
        return sb.toString();
    }

}

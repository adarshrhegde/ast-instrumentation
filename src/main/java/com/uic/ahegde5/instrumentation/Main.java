package com.uic.ahegde5.instrumentation;

import com.uic.ahegde5.instrumentation.parser.InstrumentationParser;

public class Main {

    public static void main(String[] args){

        InstrumentationParser instrumentationParser = new InstrumentationParser("D:\\IntellijWorkspace\\OOLE\\adarsh_hegde_hw1\\src\\main\\java\\org\\apache\\commons\\lang3\\test");
        instrumentationParser.execute();
    }
}

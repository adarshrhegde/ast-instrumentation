package com.uic.ahegde5.instrumentation.utility;

import java.util.Map;

public class TemplateClass {

    public static void instrum(int lineNo, String operation, Map<String,String> vars){

        System.out.print("Line" + String.valueOf(lineNo) + " " + operation + " ");
        if(null != vars && vars.isEmpty())
        for (Map.Entry<String,String> entry : vars.entrySet()) {
            System.out.print(", ");
            System.out.print(entry.getKey() + " = " + entry.getValue());
        }
        System.out.println();
    }


}

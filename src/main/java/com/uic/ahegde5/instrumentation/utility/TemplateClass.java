package com.uic.ahegde5.instrumentation.utility;

import java.util.Map;

public class TemplateClass {

    public static void instrum(int lineNo, String operation, Pair... pairs){

        try {
            System.out.print("Line" + String.valueOf(lineNo) + " " + operation + " ");
            if (null != pairs && pairs.length > 0)
                for (Pair pair : pairs) {
                    System.out.print(", " + pair.toString());
                }
            System.out.println();
        } catch (Exception e){}
    }


}

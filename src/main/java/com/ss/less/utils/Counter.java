package com.ss.less.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class Counter {
  public static HashMap<String, Integer> counts = new HashMap<String, Integer>();
  //Counter.bump(Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + " remove");
  public static void bump(String method){
  	Integer val = 0;
  	if(counts.get(method) != null){
  		val = counts.get(method);
  	}
  	counts.put(method, val + 1);
  }
  public static void tally(){
  	Iterator it = counts.entrySet().iterator();
    while (it.hasNext()) {
        Map.Entry pairs = (Map.Entry)it.next();
        System.out.println(pairs.getKey() + " = " + pairs.getValue());
        it.remove(); // avoids a ConcurrentModificationException
    }
  }
}

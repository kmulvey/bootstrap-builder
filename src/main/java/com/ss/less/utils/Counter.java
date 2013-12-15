package com.ss.less.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;


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
  	Map<Integer, String> reversedMap = new TreeMap<Integer, String>();
    while (it.hasNext()) {
        Map.Entry pairs = (Map.Entry)it.next();
        reversedMap.put((Integer) pairs.getValue(), pairs.getKey().toString());
        it.remove(); // avoids a ConcurrentModificationException
    }
    
    
    for (Map.Entry entry : reversedMap.entrySet()) {
        System.out.println(entry.getKey() + " - "  + entry.getValue());
    }
  }
}

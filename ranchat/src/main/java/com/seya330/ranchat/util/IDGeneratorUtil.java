package com.seya330.ranchat.util;

import java.util.Random;

public class IDGeneratorUtil {
	public static String generateId(String prefix, int tailLen) {
		String str = "";
	    int d = 0;
	    for (int i = 1; i <= tailLen; i++) {
	      Random r = new Random();
	      if (i == 1)
	        d = r.nextInt(8) + 1;
	      else {
	        d = r.nextInt(9);
	      }
	      str = str + Integer.toString(d);
	    }
	    return prefix + str;
	}
}

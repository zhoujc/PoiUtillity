package com.telecom.poi.util;
import java.util.HashMap;
import java.util.List;


public class NameUtil {
	public static void main(String[] args) {
		String s1 = "鼎盛锦新疆特产专卖店";
		String s2 = "鼎盛锦良新疆特产专卖店";
		double ret = diffScore(Segment.segmentList(s1),
								Segment.segmentList(s2));
		int ret2 = diffCount(Segment.segmentList(s1),
				Segment.segmentList(s2))[0];
		System.out.println(ret2+" "+ret);
	}
	
	public static double diffScore(String name1, String name2) {
		int[] ret = diffCount(Segment.segmentWithMerge(name1),
				Segment.segmentWithMerge(name2));
		return ((double)ret[0])/(ret[0]+ret[1]+ret[2]);
	}
	
	public static double diffScore(List<String> a, List<String> b) {
		int[] ret = diffCount(a,b);
		return ((double)ret[0])/(ret[0]+ret[1]+ret[2]);
	}
	
	public static int[] diffCount(List<String> a, List<String> b) {
		HashMap<String, Boolean> map = new HashMap<String, Boolean>();
		int same = 0;
		for (String str : a) {
			if (!map.containsKey(str)) {
				map.put(str, Boolean.FALSE);
			}
		}
		for (String str : b) {
			if (map.containsKey(str)) {
				same++;
			}
		}
		int[] ret = new int[3];
		ret[0]= same;
		ret[1]= a.size()-same;
		ret[2]= b.size()-same;
		return ret;
	}
	
	public static boolean isHeadMatch(String name1, String name2){
		List<String> list1=Segment.segmentList(name1);
		List<String> list2=Segment.segmentList(name2);
		return list1.get(0).equals(list2.get(0));
	}
	
}

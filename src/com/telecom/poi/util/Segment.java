package com.telecom.poi.util;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

public class Segment {
	private static int LENGTH = 2;
	
	public static boolean isSameOnSegment(String str1, String str2) {
		long time1 = System.currentTimeMillis();
		HashMap<String, String> ret1 = Segment.segmentMap(str1);
		HashMap<String, String> ret2 = Segment.segmentMap(str2);

		for (String name : ret1.keySet()) {
			if (!ret2.containsKey(name))
				return false;
		}
		for (String name : ret2.keySet()) {
			if (!ret1.containsKey(name))
				return false;
		}
		long time2 = System.currentTimeMillis();
		System.out.println(time2 - time1 + ":" + str1 + ":" + str2 + ":");
		return true;
	}
	
	public static List<String> segmentWithMerge(String name) {
		// too short to segment
		if (name.length() < 4) {
			List<String> ret = new ArrayList<String>();
			ret.add(name);
			return ret;
		}
		List<String> words = segmentList(name);
		// 4 seperate at first or 3 seperate charactor at last means step1
		// failure,
		// so skip this step2 merge.
		if ((words.size() > 3 && words.get(0).length() == 1
				&& words.get(1).length() == 1 && words.get(2).length() == 1 && words.get(3)
				.length() == 1)
				|| (words.get(words.size() - 1).length() == 1
						&& words.get(words.size() - 2).length() == 1 
						&& words.get(words.size() - 3).length() == 1))
			return words;
		LinkedList<String> sList = new LinkedList<String>();
		StringBuffer newWord = new StringBuffer();
		for (int i = 0; i < words.size(); i++) {
			if (words.get(i).length() > 1) {
				if (newWord.length() > 0) {
					sList.add(newWord.toString());
					newWord.delete(0, newWord.length());
				}
				sList.add(words.get(i));
			} else if (words.get(i).length() == 1) {
				if (newWord.length() == 0)
					newWord.append(words.get(i));
				else if (newWord.length() == 1) {
					if (i != words.size() - 1) {
						newWord.append(words.get(i));
						sList.add(newWord.toString());
						newWord.delete(0, newWord.length());
					} else {
						sList.add(newWord.toString());
						sList.add(words.get(i));
						newWord.delete(0, newWord.length());
					}
				}
			}
		}
		if (newWord.length() > 0)
			sList.add(newWord.toString());
		if (sList.size() >= 3) {
			int len0 = sList.get(0).length();
			int len1 = sList.get(1).length();
			int len2 = sList.get(2).length();
			if ((len0 == 1 && len1 == 2)
					|| (len0 == 2 && len1 == 1 && len2 > 1)) {
				String first = sList.removeFirst() + sList.removeFirst();
				sList.addFirst(first);
			}
		}
		return sList;
	}

	public static String segmentString(String input) {
		String[] strs = segmentArray(input);
		StringBuffer stringBuffer = new StringBuffer();
		for (String str : strs) {
			stringBuffer.append(str + " ");
		}
		return stringBuffer.toString();
	}

	public static String[] segmentArray(String input) {
		List<String> slist = segmentList(input);
		String[] ret = new String[slist.size()];
		return slist.toArray(ret);
	}

	public static List<String> segmentList(String input) {
		try {
			ArrayList<String> ret = new ArrayList<String>();
			Reader reader = new StringReader(input.trim());
			IKSegmenter segmenter = new IKSegmenter(reader, true);
			Lexeme lexeme = null;
			do {
				lexeme = segmenter.next();
				if (lexeme != null)
					ret.add(lexeme.getLexemeText());
			} while (lexeme != null);
			return ret;
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public static HashMap<String, String> segmentMap(String input) {
		try {
			HashMap<String, String> ret = new HashMap<String, String>();
			Reader reader = new StringReader(input.trim());
			IKSegmenter segmenter = new IKSegmenter(reader, true);

			Lexeme lexeme = null;
			do {
				lexeme = segmenter.next();
				if (lexeme != null)
					ret.put(lexeme.getLexemeText(), "");
			} while (lexeme != null);
			return ret;
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	public static void main(String[] args) {
//		String ret = segmentString("海淀区上地开发区树村后营91号和平医院附近");
//		String ret = segmentString("慈云寺桥东住邦20005号楼");
		String str = "南京西路1376号上海商城2楼（近铜仁路)";
		System.out.println(str.split("（")[0]);
	}
	
	public static boolean isComplexExisted(String in){
		if(in == null || in.length()<1)
			return false;
		String[] strs = in.split(" ");
		for(String str: strs){
			if(str.length() > LENGTH)
				return true;
		}
		return false;
	}
	
	public static String cycleSeg(String in,String base){
		StringBuffer sBuffer = new StringBuffer(); 
		String[] ins = in.split(" ");
		String[] bases = base.split(" ");
		for(String str:ins){
			if(str.length() > LENGTH){
				str = getContainSeg(str,bases);
			}
			sBuffer.append(" "+str);
		}
		return sBuffer.toString();
	}
	
	public static String getContainSeg(String big,String[] bases){
		for(String word:bases){
			if(big.contains(word) && big.length() > word.length()){
				int index = big.indexOf(word);
				if(index==0)
					return word+" "+big.substring(word.length())+" ";
				else 
					return big.substring(0,index)+" "+word+" "
							+big.substring(index+ word.length())+" ";
			}
		}
		return big;
	}

}

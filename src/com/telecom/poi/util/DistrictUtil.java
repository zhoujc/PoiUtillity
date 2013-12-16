package com.telecom.poi.util;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class DistrictUtil {

	private static String district = "district_names.txt";
	private static HashMap<String, String> map = new HashMap<String, String>();

	private static DistrictUtil destrictUtil = new DistrictUtil();

	private DistrictUtil() {
		loadDestrict();
	}

	private void loadDestrict() {
		try {
			InputStreamReader isr = new InputStreamReader(new FileInputStream(
					district));
			BufferedReader br = new BufferedReader(isr);
			String str = "";
			while ((str = br.readLine()) != null) {
				if (str.length() > 0)
					map.put(str.trim(), "");
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static DistrictUtil getInstance() {
		return destrictUtil;
	}

	// public static void main(String[] args) {
	// DistrictUtil.getInstantance();
	// appendToFile("test", test);
	// }

	public boolean isCityOrDistrict(String word) {
		return map.containsKey(word);
	}
	
	public static boolean isCitySame(POI poi1,POI poi2){
		try{
			if(poi1.city.contains(poi2.city) 
					||poi2.city.contains(poi1.city))
				return true;
		}catch(Exception e){
//			e.printStackTrace();
		}
		return false;
	}
//	private static void appendToFile(String line, String path) {
//		// write string to file
//		try {
//			FileWriter writer = new FileWriter(path, true);
//			BufferedWriter bw = new BufferedWriter(writer);
//			bw.write(line + "\r\n");
//			bw.close();
//			writer.close();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

}

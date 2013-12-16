package com.telecom.poi.util;
import java.util.ArrayList;
import java.util.List;


public class AddressUtil {
	
//	private static String[] districtTails = {"省","市","县","区","州","旗","盟"};
//	private static String[] roadTails ={"路","道","街","巷","里","弄","胡同"};
	private static int LENGTH = 4;
	public static double LENGTH_SCORE = 0.850;
	
	public static double diffScore(String address1,String address2) {
		if(address1== null || address2== null 
				|| address1.equals("null")|| address2.equals("null"))
			return -1.0;
		
		String addr1 = getCleanedAddress(address1);
		String addr2 = getCleanedAddress(address2);
		List<String> addrList1= Segment.segmentList(addr1);
		List<String> addrList2= Segment.segmentList(addr2);
		addrList1= getAddressAfterDistrict(addrList1);
		addrList2= getAddressAfterDistrict(addrList2);
		String addrIn1 = makeString(addrList1);
		String addrIn2 = makeString(addrList2);
		if(addrIn1.length()==0 || addrIn2.length()==0)
			return -1.0;
		if(addrIn1.contains(addrIn2) || addrIn2.contains(addrIn1))
			return 1.0;
		
		double score= NameUtil.diffScore(addrList1, addrList2);
		String lsStr= getLCString(addrIn1.toCharArray(),addrIn2.toCharArray());
//		System.out.println(lsStr);
		if(lsStr.length()> LENGTH && isValidAddressPre(lsStr))
			return LENGTH_SCORE;
		else
			return score;
	}
	
	private static boolean isValidAddressPre(String input) {
		if(input==null)
			return false;
		for(int i=0;i<input.length();i++){
			boolean isNum= isNum(input.substring(i, i+1));
			if(isNum && i<3)
				return false;
		}
		return true;
	}
	
	private static boolean isNum(String str){
		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}
	
	public static void main(String[] args) {
//		String s1= "朝阳区建国路88号SOHO现代城A区C座0601号";
//		String s2= "朝阳区王建国路88号SOHO现代城内号码";
//		String s1= "朝阳区建国门外大街1号国贸大厦西楼SB128号";
//		String s2= "朝阳区建国门外大街1号国贸西楼1层";
		String s1= "朝阳区劲松南路16号武汉大厦对面";
//		String s2= "朝阳区劲松南路16号武汉大厦对面";
		String s2= "朝阳区东三环中路39号建外SOHO东区1号楼0104室";
		double score= diffScore(s1,s2);
		String lsStr= getLCString(s1.toCharArray(),s2.toCharArray());

		System.out.println(score+" "+lsStr);
	}
	
	private static List<String> getAddressAfterDistrict(List<String> words){
		List<String> rets= new ArrayList<String>();
		for(String addr:words){
			boolean isDistrict= DistrictUtil.getInstance()
					.isCityOrDistrict(addr);
			if(!isDistrict)
				rets.add(addr);
		}
		return rets;
	}
	
	private static String makeString(List<String> list){
		if(list==null)
			return null;
		StringBuffer sBuffer = new StringBuffer();
		for(String word:list)
			sBuffer.append(word);
		return sBuffer.toString();
	}
	
	public static String getCleanedAddress(String address){
		if(address==null)
			return null;
		if(address.contains("("))
			return address.split("\\(")[0];
		else if(address.contains("（")) 
			return address.split("（")[0];
		else return address;
	}

	public static String getLCString(char[] str1, char[] str2) {
		//矩阵动态规划方法
			int len1, len2;
			len1 = str1.length;
			len2 = str2.length;
			int maxLen = len1 > len2 ? len1 : len2;

			int[] max = new int[maxLen];
			int[] maxIndex = new int[maxLen];// 保存最长索引的数组
			int[] c = new int[maxLen];

			int i, j;
			for (i = 0; i < len2; i++) {
				for (j = len1 - 1; j >= 0; j--) {
					if (str2[i] == str1[j]) {
						if ((i == 0) || (j == 0))
							c[j] = 1;
						else
							c[j] = c[j - 1] + 1;//此时C[j-1]还是上次循环中的值，因为还没被重新赋值
					} else {
						c[j] = 0;
					}

					if (c[j] > max[0]) {
						max[0] = c[j];
						maxIndex[0] = j;

						for (int k = 1; k < maxLen; k++) {
							max[k] = 0;
							maxIndex[k] = 0;
						}
					}
					// 有多个是相同长度的子串
					else if (c[j] == max[0]) {
						for (int k = 1; k < maxLen; k++) {
							if (max[k] == 0) {
								max[k] = c[j];
								maxIndex[k] = j;
								break; 
							}
						}
					}
				}
//				for (int temp : c) {
//					System.out.print(temp);
//				}
//				System.out.println();
			}

			StringBuffer sBuffer= new StringBuffer();
			for (j = 0; j < maxLen; j++) {
				if (max[j] > 0) {
//					System.out.println("公共子串:");
					for (i = maxIndex[j] - max[j] + 1; i <= maxIndex[j]; i++){
						sBuffer.append(str1[i]);
					}
				}
			}
			return sBuffer.toString();
		}
}

package com.telecom.poi.util;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneUtil {

	public static boolean isPhoneMatched(String phone1, String phone2) {
		String[] ret1 = getPhones(phone1);
		String[] ret2 = getPhones(phone2);
		if(ret1==null || ret2==null)
			return false;
		
		for(String str1:ret1){
			for(String str2:ret2){
				if(str1.contains(str2) || str2.contains(str1))
					return true;
			}
		}
		return false;
	}

	// 提取电话号码
	public static String[] getPhones(String phone) {
		if(phone==null ||phone.length()<5)
			return null;

		// 匹配汉字及括号及括号含内容的正则表达式
		Pattern p0 = Pattern.compile("\\(.*\\)|[\u4e00-\u9fa5]*");
		Matcher m0 = null;

		// 以- 或 A 或* 加数字结尾的及多次的正则表达式
		Pattern p3 = Pattern
				.compile("([-A]\\d{0,6})$|([-A]\\d{0,6})[/\\s]\\d{0,5}$|([-A]\\d{0,6})[/\\s]\\d{0,5}[/\\s]\\d{0,5}$|\\*.*$");
		Matcher m3 = null;

		// 第一个电话号码含分机号的正则表达式
		Pattern p4 = Pattern.compile("^(-|A)\\d{0,5}(/|、|\\s)$");
		Matcher m4 = null;

		// 以\ / \\ 结尾的正则表达式
		Pattern p5 = Pattern.compile("[/]$");
		Matcher m5 = null;

		// 再一次去除 分机号
		Pattern p6 = Pattern.compile("A.*");
		Matcher m6 = null;
		// 去除时间
		Pattern p7 = Pattern
				.compile("([0-9][0-9]B[0-9][0-9])|([0-9]B)|([0-9][0-9]B)|B-|B");
		Matcher m7 = null;

		// 去除日期
		Pattern p8 = Pattern
				.compile("([0-9]\\.[0-9][0-9]-[0-9]\\.[0-9])|(\\.\\s[0-9])");
		Matcher m8 = null;
		Pattern p9 = Pattern.compile("(-[0-9]{1,5}$)|(-\\d-\\d)");
		Matcher m9 = null;

		// 整合数据源

		// “转” 变为 A，方便后续处理汉字
		phone = phone.replaceAll("\u8f6c", "A");// \u8f6c---- 是"转"的unicode码，将
		// 或字的转变 \u6216
		phone = phone.replaceAll("\u6216", " ");

		// 时间的处理
		phone = phone.replaceAll("\\u70b9", "B");// 点 字的转码
		phone = phone.replaceAll("：", "B");
		phone = phone.replaceAll(":", "B");

		// 避免同级符号连在一起
		phone = phone.replaceAll("\u603b", " ");// 总字的转码
		phone = phone.replaceAll("\u673a", " ");// 机字的转码
		phone = phone.replaceAll("\u524d", " ");// 前字的转码
		phone = phone.replaceAll("；", "  ");

		phone = phone.replaceAll("（", "(");
		phone = phone.replaceAll("）", ")");
		phone = phone.replaceAll(";", "");
		phone = phone.replaceAll(",", " ");
		phone = phone.replaceAll("【", "");
		phone = phone.replaceAll("】", "");
		phone = phone.replaceAll("[a-z]*", "");
		phone = phone.replaceAll("E", "");

		phone = phone.replaceAll("／", "/");
		phone = phone.replaceAll("\\\\", "/");
		phone = phone.replaceAll("∕", "/");

		phone = phone.replaceAll("，", "/");
		phone = phone.replaceAll("。", "/");
		phone = phone.replaceAll("、", "/");

		phone = phone.replaceAll("－", "-");
		phone = phone.replaceAll("—", "-");
		phone = phone.replaceAll("－－", "-");
		String[] arr = null;
		try {
			// 将 汉字--与括号及括号中的内容全部去除
			m0 = p0.matcher(phone);
			if (m0.find()) {
				phone = m0.replaceAll("");
			}

			// 第一个电话号码含分机号的处理
			m4 = p4.matcher(phone);
			if (m4.find()) {
				phone = m4.replaceAll("");
			}

			// 其它情况的分机号的处理
			m3 = p3.matcher(phone);
			if (m3.find()) {
				phone = m3.replaceAll("");
			}

			// 其它的一些整合
			phone = phone.replaceAll("! !", "");
			phone = phone.replaceAll("\\(", "");
			phone = phone.replaceAll("！！！", "");
			phone = phone.replaceAll("!!", "");
			phone = phone.replaceAll("`", " ");

			// 去除以/ \ \\结束的\ / \\
			m5 = p5.matcher(phone);
			if (m5.find()) {
				phone = m5.replaceAll("");
			}
			//
			m6 = p6.matcher(phone);
			if (m6.find()) {
				phone = m6.replaceAll("");
			}

			// 去除时间，如5：30等
			m7 = p7.matcher(phone);
			if (m7.find()) {
				phone = m7.replaceAll(" ");
			}
			// 去除日期
			m8 = p8.matcher(phone);
			if (m8.find()) {
				phone = m8.replaceAll(" ");
			}
			// 最后替换
			phone = phone.replaceAll("\\)", "");
			phone = phone.replaceAll("/", " ");
			phone = phone.replaceAll("--", " ");
			phone = phone.replaceAll("！！  ", " ");

			arr = phone.split(" ");
			int len = arr.length;

			// 补全不完整的电话号码---下面的输出语句是补过的号码
			if (len > 1) {
				int k = arr[0].length();
				for (int i = 1; i < len; i++) {
					int m = arr[i].length();
					if (k > m) {
						if (m > 0 && m < 5) {
							arr[i] = arr[0].substring(0, k - m) + arr[i];
						}
					}
				}
			}

			// 数组中元素的处理
			for (int j = 0; j < len; j++) {
				m9 = p9.matcher(arr[j]);
				if (m9.find()) {
					arr[j] = m9.replaceAll("");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return arr;
	}

	public static void main(String[] args){

		// String t = "010-3638156 59";
		String t = "010-3638156-8159！！   3638156-8257-00";

		String[] a = getPhones(t);
		for (String k : a) {
			System.out.print(k + " ");
		}

	}
}

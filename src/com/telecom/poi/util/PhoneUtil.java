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

	// ��ȡ�绰����
	public static String[] getPhones(String phone) {
		if(phone==null ||phone.length()<5)
			return null;

		// ƥ�人�ּ����ż����ź����ݵ�������ʽ
		Pattern p0 = Pattern.compile("\\(.*\\)|[\u4e00-\u9fa5]*");
		Matcher m0 = null;

		// ��- �� A ��* �����ֽ�β�ļ���ε�������ʽ
		Pattern p3 = Pattern
				.compile("([-A]\\d{0,6})$|([-A]\\d{0,6})[/\\s]\\d{0,5}$|([-A]\\d{0,6})[/\\s]\\d{0,5}[/\\s]\\d{0,5}$|\\*.*$");
		Matcher m3 = null;

		// ��һ���绰���뺬�ֻ��ŵ�������ʽ
		Pattern p4 = Pattern.compile("^(-|A)\\d{0,5}(/|��|\\s)$");
		Matcher m4 = null;

		// ��\ / \\ ��β��������ʽ
		Pattern p5 = Pattern.compile("[/]$");
		Matcher m5 = null;

		// ��һ��ȥ�� �ֻ���
		Pattern p6 = Pattern.compile("A.*");
		Matcher m6 = null;
		// ȥ��ʱ��
		Pattern p7 = Pattern
				.compile("([0-9][0-9]B[0-9][0-9])|([0-9]B)|([0-9][0-9]B)|B-|B");
		Matcher m7 = null;

		// ȥ������
		Pattern p8 = Pattern
				.compile("([0-9]\\.[0-9][0-9]-[0-9]\\.[0-9])|(\\.\\s[0-9])");
		Matcher m8 = null;
		Pattern p9 = Pattern.compile("(-[0-9]{1,5}$)|(-\\d-\\d)");
		Matcher m9 = null;

		// ��������Դ

		// ��ת�� ��Ϊ A���������������
		phone = phone.replaceAll("\u8f6c", "A");// \u8f6c---- ��"ת"��unicode�룬��
		// ���ֵ�ת�� \u6216
		phone = phone.replaceAll("\u6216", " ");

		// ʱ��Ĵ���
		phone = phone.replaceAll("\\u70b9", "B");// �� �ֵ�ת��
		phone = phone.replaceAll("��", "B");
		phone = phone.replaceAll(":", "B");

		// ����ͬ����������һ��
		phone = phone.replaceAll("\u603b", " ");// ���ֵ�ת��
		phone = phone.replaceAll("\u673a", " ");// ���ֵ�ת��
		phone = phone.replaceAll("\u524d", " ");// ǰ�ֵ�ת��
		phone = phone.replaceAll("��", "  ");

		phone = phone.replaceAll("��", "(");
		phone = phone.replaceAll("��", ")");
		phone = phone.replaceAll(";", "");
		phone = phone.replaceAll(",", " ");
		phone = phone.replaceAll("��", "");
		phone = phone.replaceAll("��", "");
		phone = phone.replaceAll("[a-z]*", "");
		phone = phone.replaceAll("E", "");

		phone = phone.replaceAll("��", "/");
		phone = phone.replaceAll("\\\\", "/");
		phone = phone.replaceAll("�M", "/");

		phone = phone.replaceAll("��", "/");
		phone = phone.replaceAll("��", "/");
		phone = phone.replaceAll("��", "/");

		phone = phone.replaceAll("��", "-");
		phone = phone.replaceAll("��", "-");
		phone = phone.replaceAll("����", "-");
		String[] arr = null;
		try {
			// �� ����--�����ż������е�����ȫ��ȥ��
			m0 = p0.matcher(phone);
			if (m0.find()) {
				phone = m0.replaceAll("");
			}

			// ��һ���绰���뺬�ֻ��ŵĴ���
			m4 = p4.matcher(phone);
			if (m4.find()) {
				phone = m4.replaceAll("");
			}

			// ��������ķֻ��ŵĴ���
			m3 = p3.matcher(phone);
			if (m3.find()) {
				phone = m3.replaceAll("");
			}

			// ������һЩ����
			phone = phone.replaceAll("! !", "");
			phone = phone.replaceAll("\\(", "");
			phone = phone.replaceAll("������", "");
			phone = phone.replaceAll("!!", "");
			phone = phone.replaceAll("`", " ");

			// ȥ����/ \ \\������\ / \\
			m5 = p5.matcher(phone);
			if (m5.find()) {
				phone = m5.replaceAll("");
			}
			//
			m6 = p6.matcher(phone);
			if (m6.find()) {
				phone = m6.replaceAll("");
			}

			// ȥ��ʱ�䣬��5��30��
			m7 = p7.matcher(phone);
			if (m7.find()) {
				phone = m7.replaceAll(" ");
			}
			// ȥ������
			m8 = p8.matcher(phone);
			if (m8.find()) {
				phone = m8.replaceAll(" ");
			}
			// ����滻
			phone = phone.replaceAll("\\)", "");
			phone = phone.replaceAll("/", " ");
			phone = phone.replaceAll("--", " ");
			phone = phone.replaceAll("����  ", " ");

			arr = phone.split(" ");
			int len = arr.length;

			// ��ȫ�������ĵ绰����---������������ǲ����ĺ���
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

			// ������Ԫ�صĴ���
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
		String t = "010-3638156-8159����   3638156-8257-00";

		String[] a = getPhones(t);
		for (String k : a) {
			System.out.print(k + " ");
		}

	}
}

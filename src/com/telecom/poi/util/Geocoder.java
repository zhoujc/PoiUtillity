package com.telecom.poi.util;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;

public class Geocoder {
	// urlPre+ KEYWORD +urlCityPre+ CITY +urllast
	static String urlPre = "http://116.228.55.102:8080/gss/simple?sid=1000&resType=json&encode=UTF-8&batch=1&number=20&rid=123&keyword=";
	static String urlCityPre = "&city=";
	static String urllast = "&key=5e2a2acb5e33ea069bc53e6b38277aa878804f7a584130e068c3f0f36bf0a7ef9308031af8fb6462%22";

	public static void main(String[] args) {
		// 西二旗地铁站
		System.out.println(getAddressParsed("乌鲁木齐北路505号", "上海"));
	}

	public static POI getPOIWithGeocode(String addressInput, String city) {
		AddressParsed ret = getAddressParsed(addressInput, city);
		POI poi = new POI();
		if (ret != null) {
			poi.name = ret.getName();
			poi.address = ret.getAddress();
			poi.lat = String.valueOf(ret.getX());
			poi.lon = String.valueOf(ret.getY());
			poi.type = String.valueOf(ret.getType());
			return poi;
		}else {
			return null;
		}
	}
	
	public static POI fillPOILatLon(POI input) {
		POI ret= getPOIWithGeocode(input.address,input.city);
		if(ret!=null){
			input.lat= ret.lat;
			input.lon= ret.lon; 		
		}
		return input;
	}

	private static AddressParsed getAddressParsed(String addressInput,
			String city) {
		String url = "";
		String address = AddressUtil.getCleanedAddress(addressInput);
		if (city != null)
			url = urlPre + address + urlCityPre + city + urllast;
		else {
			url = urlPre + address + urllast;
		}
		// String url=;
		System.out.println(url);
		// 创建一个HttpClient对象
		HttpClient httpClient = new DefaultHttpClient();
		String responseData = "";
		try {
			// 向指定的URL发送Http请求
			HttpResponse response = httpClient.execute(new HttpGet(url));
			// 取得服务器返回的响应
			HttpEntity entity = response.getEntity();
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(entity.getContent(), "UTF-8"));
			String line = "";
			while ((line = bufferedReader.readLine()) != null) {
				responseData = responseData + line;
			}
			String jsonRet = responseData.split("=")[1];
			// System.out.println(jsonRet);
			Gson gson = new Gson();
			RetResult retResult = gson.fromJson(jsonRet, RetResult.class);
			System.out.println(retResult);
			if (retResult.status.equals("E0"))
				return retResult.getList().get(0);
			else
				return null;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(responseData);
		}
		return null;

	}

	class RetResult {
		private String status;
		private List<AddressParsed> list;

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public List<AddressParsed> getList() {
			return list;
		}

		public void setList(List<AddressParsed> list) {
			this.list = list;
		}

		@Override
		public String toString() {
			return "TestResult [results=" + list + ", status=" + status + "]";
		}
	}

}

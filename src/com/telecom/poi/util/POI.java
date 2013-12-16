package com.telecom.poi.util;

public class POI{
		String city;
		String name;
		String address;
		String phone;
		String lat;
		String lon;
		String type;
		
		@Override
		public String toString(){
			return name+","+address+","+phone+","+lat+","+lon+","+type;
		}
	}

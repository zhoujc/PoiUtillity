package com.telecom.poi.util;

public class LatLonUtil {
	public static double THRESHOLD = 1000;//below 1000m
	private static double EARTH_RADIUS = 6378.137;

	public static void main(String[] args) {
//		double tt = distance(39.00001, 120.00000, 39.00002, 120.00000);
//		double tt = distance(39.9183320, 116.4788740, 39.915764, 116.481493);
		double tt = getDistanceScore(39.9183320, 116.4788740, 39.915764, 116.481493);
		System.out.println(tt);
	}
	
	public static double getDistanceScore(double lat1, double lng1, double lat2, double lng2){
		double distance = distance(lat1,lng1,lat2,lng2);
		return (1-distance/THRESHOLD);
	}
	
	public static double getDistanceScore(String lat1, String lng1, String lat2, String lng2){
		try{
			return getDistanceScore(Double.parseDouble(lat1),Double.parseDouble(lng1),
			            Double.parseDouble(lat2),Double.parseDouble(lng2));
		}catch(Exception e){
			return -1;
		}
	}
	
	public static double distance(String lat1, String lng1, String lat2, String lng2){
		try{
			return distance(Double.parseDouble(lat1),Double.parseDouble(lng1),
			            Double.parseDouble(lat2),Double.parseDouble(lng2));
		}catch(Exception e){
			return -1;
		}
	}
	public static double distance(double lat1, double lng1, double lat2, double lng2)
	{
	   double radLat1 = rad(lat1);
	   double radLat2 = rad(lat2);
	   double a = radLat1 - radLat2;
	   double b = rad(lng1) - rad(lng2);
	   double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) +
	    Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
	   s = s * EARTH_RADIUS;
	   s = Math.round(s * 1000) ;
	   return s;
	}
	
	private static double rad(double d)
	{
	   return d * Math.PI / 180.0;
	}
}

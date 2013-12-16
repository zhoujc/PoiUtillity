package com.telecom.poi.util;

public class PoiUtil {

		public static boolean isSame(POI poi1,POI poi2){
			if(!DistrictUtil.isCitySame(poi1,poi2))
				return false;
			if(poi1.lat==null)
				poi1= Geocoder.fillPOILatLon(poi1);
			if(poi2.lat==null)
				poi2= Geocoder.fillPOILatLon(poi2);
			
			double nameScore = NameUtil.diffScore(poi1.name,poi2.name);
			double addrScore = AddressUtil.diffScore(poi1.address,poi2.address);
			double disScore = LatLonUtil.getDistanceScore(poi1.lat, poi1.lon, poi2.lat, poi2.lon);
			boolean isPhoneMatched= PhoneUtil.isPhoneMatched(poi1.phone,poi2.phone);
			addrScore= Math.max(addrScore, disScore);

			if((nameScore>0.8 && addrScore>0.8)
				||(nameScore==1.0 && addrScore>0.6) 
				|| (addrScore==1.0 && nameScore>0.7 
				&& NameUtil.isHeadMatch(poi1.name, poi2.name)))
				return true;
			else {
				return false;
			}
		}
		
}

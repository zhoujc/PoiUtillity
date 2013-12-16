package com.telecom.poi.util;

public class AddressParsed {
		private String type;
		private String address;
		private String name;
		private double y;
		private double x;

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}
		
		public String getName() {
			return name;
		}
		
		public void setName(String name) {
			this.name = name;
		}
		
		public double getY() {
			return y;
		}
		
		public void setY(double y) {
			this.y = y;
		}
		
		public double getX() {
			return x;
		}
		
		public void setX(double x) {
			this.x = x;
		}

		@Override
		public String toString() {
			return "[Address=" + address + ", name="
					+ name +","+y+","+x+"]";
		}
	}
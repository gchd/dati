package com.sdust.entity;

import org.junit.Test;

public class RGBValue {
	private int rValue;
	private int gValue;
	private int bValue;
	// 允许的误差
	private static int rgbVerificationValue = 5;

	public RGBValue() {
		this.rValue = 0;
		this.gValue = 0;
		this.bValue = 0;
	}

	// 例子#FFFFFF
	public RGBValue(String hexColor) {
		if (!(hexColor.length() == 7) || !hexColor.startsWith("#")) {
			this.rValue = 0;
			this.gValue = 0;
			this.bValue = 0;
		} else {
			try {
				this.rValue = Integer.parseInt(hexColor.substring(1, 3), 16);
				this.gValue = Integer.parseInt(hexColor.substring(3, 5), 16);
				this.bValue = Integer.parseInt(hexColor.substring(5, 7), 16);
			} catch (Exception e) {
				this.rValue = 0;
				this.gValue = 0;
				this.bValue = 0;
			}
		}
	}

	public RGBValue(int rValue, int gValue, int bValue) {
		this.rValue = rValue;
		this.gValue = gValue;
		this.bValue = bValue;
	}

	public void setRgbVerificationValue(int rgbVerificationValue) {
		this.rgbVerificationValue = rgbVerificationValue;
	}

	public int getrValue() {
		return rValue;
	}

	public void setrValue(int rValue) {
		this.rValue = rValue;
	}

	public int getgValue() {
		return gValue;
	}

	public void setgValue(int gValue) {
		this.gValue = gValue;
	}

	public int getbValue() {
		return bValue;
	}

	public void setbValue(int bValue) {
		this.bValue = bValue;
	}

	public String getHexColor() {
		String hexColor = "#" + Integer.toHexString(rValue)
				+ Integer.toHexString(gValue) + Integer.toHexString(bValue);
		return hexColor;
	}

	@Override
	public String toString() {
		return "RGBValue [rValue=" + rValue + ", gValue=" + gValue
				+ ", bValue=" + bValue + "]";
	}

	/**
	 * 
	 * @param A
	 *            当前点的颜色
	 * @param B
	 *            要比较的颜色中心值（加减误差值）
	 * @return
	 */
	public static boolean equalRGB(RGBValue A, RGBValue B) {
		if (A.getrValue() > B.getrValue() + rgbVerificationValue
				|| A.getrValue() < B.getrValue() - rgbVerificationValue) {
			return false;
		}

		if (A.getgValue() > B.getgValue() + rgbVerificationValue
				&& A.getgValue() < B.getgValue() - rgbVerificationValue) {
			return false;
		}

		if (A.getbValue() > B.getbValue() + rgbVerificationValue
				&& A.getbValue() < B.getbValue() - rgbVerificationValue) {
			return false;
		}
		return true;
	}

}

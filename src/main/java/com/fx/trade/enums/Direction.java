package com.fx.trade.enums;

public enum Direction {
	BUY, SELL;

	static public boolean isMember(String value) {
		Direction[] list = Direction.values();
		for (Direction val : list)
			if (val.toString().equals(value))
				return true;
		return false;
	}
}

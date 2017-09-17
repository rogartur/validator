package com.fx.trade.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Type {

	SPOT("Spot"), FORWARD("Forward"), OPTION("VanillaOption");

	private String name;

	private Type(String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return name;
	}

	static public boolean isMember(String value) {
		Type[] list = Type.values();
		for (Type val : list)
			if (val.name.equals(value))
				return true;
		return false;
	}
}

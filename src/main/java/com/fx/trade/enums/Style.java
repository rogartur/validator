package com.fx.trade.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Style {

	EUROPEAN("AMERICAN"), AMERICAN("AMERICAN");

	private String name;

	private Style(String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return name;
	}
	
	static public boolean isMember(String value) {
		Style[] list = Style.values();
		for (Style val : list)
			if (val.name.equals(value))
				return true;
		return false;
	}
	
}

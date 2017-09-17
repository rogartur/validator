package com.fx.trade.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Customer {

	PLUTO1("PLUTO1"), PLUTO2("PLUTO2");

	private String name;

	private Customer(String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return name;
	}
	
	static public boolean isMember(String value) {
		Customer[] list = Customer.values();
		for (Customer val : list)
			if (val.name.equals(value))
				return true;
		return false;
	}
}

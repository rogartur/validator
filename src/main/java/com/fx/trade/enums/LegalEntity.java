package com.fx.trade.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum LegalEntity {

	CS_ZURICH("CS Zurich");

	private String name;

	private LegalEntity(String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return name;
	}

	static public boolean isMember(String value) {
		LegalEntity[] list = LegalEntity.values();
		for (LegalEntity val : list)
			if (val.name.equals(value))
				return true;
		return false;
	}
}

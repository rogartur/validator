package com.fx.trade.wrapper;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fx.trade.model.TradeData;

/**
 * Wrapper for the group of FX transaction objects. In case of wrapping object
 * name change, update only the @JsonProperty annotation
 *
 */
public class TradeDataWrapper {

	private List<TradeData> dataList;

	@JsonProperty("dataList")
	public List<TradeData> getDataList() {
		return dataList;
	}

	public void setDataList(List<TradeData> dataList) {
		this.dataList = dataList;
	}

	@Override
	public String toString() {
		return "TradeDataWrapper [dataList=" + dataList + "]";
	}

}

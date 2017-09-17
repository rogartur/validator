package com.fx.trade.model;

/**
 * This is main object representing FX transaction
 *
 */
public class TradeData {

	private String customer;

	private String ccyPair;

	private String type;

	private String style;

	private String direction;

	private String strategy;

	private String tradeDate;

	private Double amount1;

	private Double amount2;

	private Double rate;

	private String valueDate;

	private String deliveryDate;

	private String expiryDate;

	private String exerciseStartDate;

	private String payCcy;

	private Double premium;

	private String premiumCcy;

	private String premiumType;

	private String premiumDate;

	private String legalEntity;

	private String trader;

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getCcyPair() {
		return ccyPair;
	}

	public void setCcyPair(String ccyPair) {
		this.ccyPair = ccyPair;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getStrategy() {
		return strategy;
	}

	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}

	public String getTradeDate() {
		return tradeDate;
	}

	public void setTradeDate(String tradeDate) {
		this.tradeDate = tradeDate;
	}

	public Double getAmount1() {
		return amount1;
	}

	public void setAmount1(Double amount1) {
		this.amount1 = amount1;
	}

	public Double getAmount2() {
		return amount2;
	}

	public void setAmount2(Double amount2) {
		this.amount2 = amount2;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public String getValueDate() {
		return valueDate;
	}

	public void setValueDate(String valueDate) {
		this.valueDate = valueDate;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getExerciseStartDate() {
		return exerciseStartDate;
	}

	public void setExerciseStartDate(String excerciseStartDate) {
		this.exerciseStartDate = excerciseStartDate;
	}

	public String getPayCcy() {
		return payCcy;
	}

	public void setPayCcy(String payCcy) {
		this.payCcy = payCcy;
	}

	public Double getPremium() {
		return premium;
	}

	public void setPremium(Double premium) {
		this.premium = premium;
	}

	public String getPremiumCcy() {
		return premiumCcy;
	}

	public void setPremiumCcy(String premiumCcy) {
		this.premiumCcy = premiumCcy;
	}

	public String getPremiumType() {
		return premiumType;
	}

	public void setPremiumType(String premiumType) {
		this.premiumType = premiumType;
	}

	public String getPremiumDate() {
		return premiumDate;
	}

	public void setPremiumDate(String premiumDate) {
		this.premiumDate = premiumDate;
	}

	public String getLegalEntity() {
		return legalEntity;
	}

	public void setLegalEntity(String legalEntity) {
		this.legalEntity = legalEntity;
	}

	public String getTrader() {
		return trader;
	}

	public void setTrader(String trader) {
		this.trader = trader;
	}

	@Override
	public String toString() {
		return "TradeData [customer=" + customer + ", ccyPair=" + ccyPair + ", type=" + type + ", style=" + style
				+ ", direction=" + direction + ", strategy=" + strategy + ", tradeDate=" + tradeDate + ", amount1="
				+ amount1 + ", amount2=" + amount2 + ", rate=" + rate + ", valueDate=" + valueDate + ", deliveryDate="
				+ deliveryDate + ", expiryDate=" + expiryDate + ", excerciseStartDate=" + exerciseStartDate
				+ ", payCcy=" + payCcy + ", premium=" + premium + ", premiumCcy=" + premiumCcy + ", premiumType="
				+ premiumType + ", premiumDate=" + premiumDate + ", legalEntity=" + legalEntity + ", trader=" + trader
				+ "]";
	}
}
package com.fx.trade.validator;

import static org.springframework.validation.ValidationUtils.rejectIfEmpty;
import static org.springframework.validation.ValidationUtils.rejectIfEmptyOrWhitespace;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;

import com.fx.trade.enums.Customer;
import com.fx.trade.enums.Direction;
import com.fx.trade.enums.LegalEntity;
import com.fx.trade.enums.Style;
import com.fx.trade.enums.Type;
import com.fx.trade.model.TradeData;
import com.fx.trade.service.CurrencyISOCheckService;
import com.fx.trade.service.CurrencyWorkingDayService;

public class ValidationHelper {

	protected final Logger log = LoggerFactory.getLogger(this.getClass());

	private final String accessKey;

	private static CurrencyWorkingDayService currencyWDService;

	private static CurrencyISOCheckService currencyISOService;

	public ValidationHelper(String accessKey) {
		this.accessKey = accessKey;
	}

	/**
	 * @param e
	 * @param p
	 */
	public void validateOptionType(Errors e, TradeData p, int i) {
		if (Type.OPTION.getName() == p.getType()) {

			rejectIfEmptyOrWhitespace(e, "dataList[" + i + "].premium", "required.for.type");
			rejectIfEmptyOrWhitespace(e, "dataList[" + i + "].premiumCcy", "required.for.type");
			rejectIfEmptyOrWhitespace(e, "dataList[" + i + "].premiumType", "required.for.type");
			rejectIfEmptyOrWhitespace(e, "dataList[" + i + "].premiumDate", "required.for.type");
			rejectIfEmptyOrWhitespace(e, "dataList[" + i + "].strategy", "required.for.type");

			boolean legalExpiryDate = isLegalDate(p.getExpiryDate());
			boolean legalDeliveryDate = isLegalDate(p.getDeliveryDate());
			boolean legalPremiumDate = isLegalDate(p.getPremiumDate());
			boolean legalExerciseDate = isLegalDate(p.getExerciseStartDate());

			if (p.getStyle() == null)
				e.rejectValue("dataList[" + i + "].style", "required.for.type");
			if (p.getDeliveryDate() == null || !legalDeliveryDate)
				e.rejectValue("dataList[" + i + "].deliveryDate", "required.for.type");
			if (p.getExpiryDate() == null || !legalExpiryDate)
				e.rejectValue("dataList[" + i + "].expiryDate", "required.for.type");
			if (p.getPremiumDate() == null || !legalPremiumDate)
				e.rejectValue("dataList[" + i + "].premiumDate", "required.for.type");

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date expd;
			Date dd;
			Date pd;
			Date excd;
			Date td;

			try {
				expd = sdf.parse(p.getExpiryDate());
				excd = sdf.parse(p.getExerciseStartDate());
				dd = sdf.parse(p.getDeliveryDate());
				pd = sdf.parse(p.getPremiumDate());
				td = sdf.parse(p.getTradeDate());
			} catch (ParseException ex) {
				log.error("TradeDataValidator: Exception while parsing dates!", ex);
				return;
			}

			if (Style.AMERICAN.getName() == p.getStyle()) {
				if (p.getExerciseStartDate() == null || !legalExerciseDate)
					e.rejectValue("dataList[" + i + "].exerciseStartDate", "required.for.type");

				if (excd.before(td) || excd.after(expd))
					e.rejectValue("dataList[" + i + "].exerciseStartDate", "exerciseStartDate.incorrect.date");

				if (expd.after(dd))
					e.rejectValue("dataList[" + i + "].expiryDate", "expiryDate.after.deliveryDate");

				if (pd.after(dd))
					e.rejectValue("dataList[" + i + "].premiumDate", "premiumDate.after.deliveryDate");

			}

		}
	}

	/**
	 * @param e
	 * @param ccyPair
	 * @return
	 */
	public boolean validateCurrencies(Errors e, String ccyPair, int i) {
		if (!StringUtils.isEmpty(ccyPair) && ccyPair.length() == 6) {
			currencyISOService = new CurrencyISOCheckService();
			boolean isISO1 = currencyISOService.isISOCurrency(ccyPair.substring(0, 3));
			boolean isISO2 = currencyISOService.isISOCurrency(ccyPair.substring(3, 6));

			if (isISO1 && isISO2)
				return true;
		}
		e.rejectValue("dataList[" + i + "].ccyPair", "ccyPair.not.iso");
		return false;
	}

	/**
	 * @param e
	 * @param p
	 */
	public void validateSpotForwardTypes(Errors e, TradeData p, int i) {
		if (Type.FORWARD.getName() == p.getType() || Type.SPOT.getName() == p.getType()) {
			if (p.getValueDate() == null || !isLegalDate(p.getValueDate()))
				e.rejectValue("dataList[" + i + "].valueDate", "required.for.type");
		}
	}

	/**
	 * @param e
	 * @param valueDate
	 * @param tradeDate
	 * @param ccyPair
	 * @param validCurrencies
	 * @throws ParseException
	 */
	public void validateValueDate(Errors e, String valueDate, String tradeDate, String ccyPair, boolean validCurrencies,
			int i) {

		if (valueDate == null || tradeDate == null || !isLegalDate(valueDate) || !isLegalDate(tradeDate))
			return;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date vd;
		Date td;

		try {
			vd = sdf.parse(valueDate);
			td = sdf.parse(tradeDate);
		} catch (ParseException ex) {
			log.error("TradeDataValidator: Exception while parsing dates: {} {} ", valueDate, tradeDate, ex);
			return;
		}

		/* VALID VALUE DATE, BEFORE TRADE DATE */
		if (vd.before(td))
			e.rejectValue("dataList[" + i + "].valueDate", "valueDate.before.tradeDate.message");

		LocalDate localDate = vd.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		DayOfWeek day = localDate.getDayOfWeek();

		/* VALID ON NON-WEEKEND */
		if (DayOfWeek.SATURDAY == day || DayOfWeek.SUNDAY == day)
			e.rejectValue("dataList[" + i + "].valueDate", "valueDate.on.weekend");

		if (validCurrencies) {
			String currencies = ccyPair.substring(0, 3) + "," + ccyPair.substring(3, 6);
			/* VALID WORKING DAY FOR CURRENCY */
			currencyWDService = new CurrencyWorkingDayService(accessKey);
			if (!currencyWDService.isWorkingDay(currencies, valueDate)) {
				e.reject("dataList[" + i + "].valueDate.currency.holiday",
						"Value date fall on currency non working day!");
			}
		}
	}

	/**
	 * @param e
	 * @param p
	 */
	public void validateDates(Errors e, TradeData p, int i) {

		if (p.getValueDate() != null)
			if (!isLegalDate(p.getValueDate()))
				e.rejectValue("dataList[" + i + "].valueDate", "not.a.date.message");

		if (p.getTradeDate() != null)
			if (!isLegalDate(p.getTradeDate()))
				e.rejectValue("dataList[" + i + "].tradeDate", "not.a.date.message");

		if (p.getDeliveryDate() != null)
			if (!isLegalDate(p.getDeliveryDate()))
				e.rejectValue("dataList[" + i + "].deliveryDate", "not.a.date.message");

		if (p.getExerciseStartDate() != null)
			if (!isLegalDate(p.getExerciseStartDate()))
				e.rejectValue("dataList[" + i + "].exerciseStartDate", "not.a.date.message");

		if (p.getExpiryDate() != null)
			if (!isLegalDate(p.getExpiryDate()))
				e.rejectValue("dataList[" + i + "].expiryDate", "not.a.date.message");

		if (p.getPremiumDate() != null)
			if (!isLegalDate(p.getPremiumDate()))
				e.rejectValue("dataList[" + i + "].premiumDate", "not.a.date.message");
	}

	/**
	 * @param e
	 * @param p
	 */
	public void validateEnumValues(Errors e, TradeData p, int i) {
		if (p.getCustomer() != null)
			if (!Customer.isMember(p.getCustomer()))
				e.rejectValue("dataList[" + i + "].customer", "not.enum.message", new String[] { p.getCustomer() },
						null);

		if (p.getDirection() != null)
			if (!Direction.isMember(p.getDirection()))
				e.rejectValue("dataList[" + i + "].direction", "not.enum.message", new String[] { p.getDirection() },
						null);

		if (p.getLegalEntity() != null)
			if (!LegalEntity.isMember(p.getLegalEntity()))
				e.rejectValue("dataList[" + i + "].legalEntity", "not.enum.message",
						new String[] { p.getLegalEntity() }, null);

		if (p.getStyle() != null)
			if (!Style.isMember(p.getStyle()))
				e.rejectValue("dataList[" + i + "].style", "not.enum.message", new String[] { p.getStyle() }, null);

		if (p.getType() != null)
			if (!Type.isMember(p.getType()))
				e.rejectValue("dataList[" + i + "].type", "not.enum.message", new String[] { p.getType() }, null);
	}
	
	/**
	 * @param e
	 * @param amount1
	 * @param amount2
	 * @param rate
	 * @param i
	 */
	public void validateAmounts(Errors e, Double amount1, Double amount2, Double rate, int i) {
		if (amount1 < 0)
			e.rejectValue("dataList[" + i + "].amount1", "negativeValue");
		if (amount2 < 0)
			e.rejectValue("dataList[" + i + "].amount2", "negativeValue");
		if (rate < 0)
			e.rejectValue("dataList[" + i + "].rate", "negativeValue");

	}

	/**
	 * @param e
	 */
	public void validateEmptyFields(Errors e, int i) {
		rejectIfEmpty(e, "dataList[" + i + "].customer", "empty.customer");
		rejectIfEmptyOrWhitespace(e, "dataList[" + i + "].ccyPair", "empty.ccyPair");
		rejectIfEmpty(e, "dataList[" + i + "].type", "empty.type");
		rejectIfEmpty(e, "dataList[" + i + "].direction", "empty.direction");
		rejectIfEmpty(e, "dataList[" + i + "].tradeDate", "empty.tradeDate");
		rejectIfEmpty(e, "dataList[" + i + "].amount1", "empty.amount1");
		rejectIfEmpty(e, "dataList[" + i + "].amount2", "empty.amount2");
		rejectIfEmpty(e, "dataList[" + i + "].rate", "empty.rate");
		rejectIfEmpty(e, "dataList[" + i + "].legalEntity", "empty.legalEntity");
		rejectIfEmptyOrWhitespace(e, "dataList[" + i + "].trader", "empty.trader");
	}

	/**
	 * @param s
	 * @return
	 */
	public static boolean isLegalDate(String s) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(false);
		return sdf.parse(s, new ParsePosition(0)) != null;
	}

}

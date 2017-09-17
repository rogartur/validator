package com.fx.trade.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.fx.trade.model.TradeData;
import com.fx.trade.wrapper.TradeDataWrapper;

public class TradeDataListValidator implements Validator {

	protected final Logger log = LoggerFactory.getLogger(this.getClass());

	private final ValidationHelper helper;

	public TradeDataListValidator(String accessKey) {
		this.helper = new ValidationHelper(accessKey);
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return TradeDataWrapper.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors e) {

		TradeDataWrapper p = (TradeDataWrapper) target;

		if (p.getDataList() == null) {
			e.rejectValue("dataList", "dataList.not.found");
			return;
		}

		for (int i = 0; i < p.getDataList().size(); i++) {
			TradeData tradeData = p.getDataList().get(i);

			/* GENERAL VALIDATION */
			helper.validateEmptyFields(e, i);

			helper.validateEnumValues(e, tradeData, i);

			helper.validateDates(e, tradeData, i);
			
			helper.validateAmounts(e, tradeData.getAmount1(), tradeData.getAmount2(), tradeData.getRate(), i);

			/* VALID ISO 4217 */
			boolean validCurrencies = helper.validateCurrencies(e, tradeData.getCcyPair(), i);

			/* VALIDATION AROUND VALUE DATE */
			helper.validateValueDate(e, tradeData.getValueDate(), tradeData.getTradeDate(), tradeData.getCcyPair(),
					validCurrencies, i);

			/* SPOT, FORWARD TYPES */
			helper.validateSpotForwardTypes(e, tradeData, i);

			/* OPTION TYPE */
			helper.validateOptionType(e, tradeData, i);

		}

	}

}

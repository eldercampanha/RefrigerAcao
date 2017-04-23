package br.com.refrigeracao.app.presentation.helper;

/**
 * Created by elder on 2017-04-23.
 */

public class Regex {
    public static final String CREDIT_CARD_NUMBER_FORMAT = "^(4[0-9]{3}|5[1-5][0-9]{2}) [0-9]{4} [0-9]{4} [0-9]{4}";
    public static final String CREDIT_CARD_NUMBER_SIMPLE_FORMAT = "^(4[0-9]{3}) [0-9]{4} [0-9]{4} [0-9]{4}";
    public static final String PHONE_NUMBER_SIMPLE_FORMAT = "\\([0-9]{2}\\) [0-9]{5} ([0-9]{4})?([0-9]{3})?";
}

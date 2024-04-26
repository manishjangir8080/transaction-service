package com.marlow.v1.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class KafkaConstants {

    public static final String TRANSACTION_TOPIC = "transactions";
    public static final String ACCOUNT_BALANCES_TOPIC = "account-balances";

    public static final String ACCOUNT_BALANCE_STORE = "account-balance-store";
}

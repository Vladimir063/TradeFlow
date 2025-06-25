package com.tradeflow.event.kafka;

public final class Topics {

    private Topics() {}

    public static final String ORDER_CREATE_EVENTS = "order-create-events";
    public static final String ORDER_COMPLETED_EVENTS = "order-completed-events";
    public static final String ORDER_COMMANDS = "order-commands";

    public static final String COMPANY_CREATED_EVENTS = "company-created-events";


    public static final String FUNDS_COMMANDS    = "funds-commands";
    public static final String PAYMENT_RESERVED_EVENTS = "payment-reserved-events";
    public static final String PAYMENT_RESERVED_FAILED_EVENTS = "payment-reserved-failed-events";


    public static final String TRADE_COMMANDS = "trade-commands";
    public static final String TRADE_EXECUTED = "trade-executed";


    public static final String PORTFOLIO_COMMANDS_RESERVE = "portfolio-commands-reserve";
    public static final String PORTFOLIO_COMMANDS_UPDATE = "portfolio-commands-update";
    public static final String PORTFOLIO_RESERVED_EVENTS = "portfolio-reserved-events";
    public static final String PORTFOLIO_RESERVED_FAILED_EVENTS = "portfolio-reserved-failed-events";
    public static final String PORTFOLIO_EXECUTED = "portfolio-executed";


    public static final String SAGA_EXECUTED = "saga-executed";

    public static final String USER_CREATE_EVENTS = "user-create-events";

}

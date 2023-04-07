package com.transdev.mobiservices.entity.enums;

public enum PaymentType {
    PAYPAL("Paypal"),
    CREDIT_CARD("Credit card");

    private final String name;

    PaymentType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

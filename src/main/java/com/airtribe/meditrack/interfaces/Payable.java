package com.airtribe.meditrack.interfaces;

import com.airtribe.meditrack.constants.Constants;

public interface Payable {
    double baseAmount();
    default double withTax() { return baseAmount() * (1.0 + Constants.TAX_RATE); }
}

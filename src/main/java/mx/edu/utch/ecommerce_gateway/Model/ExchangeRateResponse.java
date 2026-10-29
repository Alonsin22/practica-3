package mx.edu.utch.ecommerce_gateway.Model;

import java.util.Map;

public record ExchangeRateResponse(
        double amount,
        String base,
        String date,
        Map<String, Double> rates
) {}
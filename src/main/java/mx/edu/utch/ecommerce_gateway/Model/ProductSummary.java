package mx.edu.utch.ecommerce_gateway.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ProductSummary(
        @JsonProperty("product_id") String productId,
        String title,
        @JsonProperty("price_usd") double priceUsd,
        @JsonProperty("price_mxn") Double priceMxn,
        @JsonProperty("stock_status") String stockStatus,
        @JsonProperty("available_units") Integer availableUnits,
        @JsonProperty("currency_conversion_source") String currencyConversionSource,
        String status
) {}
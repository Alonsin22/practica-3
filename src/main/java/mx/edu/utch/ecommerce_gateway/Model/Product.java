package mx.edu.utch.ecommerce_gateway.Model;

public record Product(
        String id,
        String name,
        String description,
        String category,
        double priceUsd
) {}
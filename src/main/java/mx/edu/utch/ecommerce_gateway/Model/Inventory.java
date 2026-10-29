package mx.edu.utch.ecommerce_gateway.Model;

public record Inventory(
        String productId,
        int availableUnits,
        String warehouseLocation,
        int estimatedDeliveryDays
) {}
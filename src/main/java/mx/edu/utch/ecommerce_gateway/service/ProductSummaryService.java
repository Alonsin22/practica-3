package mx.edu.utch.ecommerce_gateway.service;

import mx.edu.utch.ecommerce_gateway.client.ServiceClients;
import mx.edu.utch.ecommerce_gateway.Model.ExchangeRateResponse;
import mx.edu.utch.ecommerce_gateway.Model.Inventory;
import mx.edu.utch.ecommerce_gateway.Model.Product;
import mx.edu.utch.ecommerce_gateway.Model.ProductSummary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class ProductSummaryService {

    private static final Logger log = LoggerFactory.getLogger(ProductSummaryService.class);

    private final ServiceClients clients;

    public ProductSummaryService(ServiceClients clients) {
        this.clients = clients;
    }

    public ProductSummary getSummary(String id) {

        // 1. Lanzar las 3 peticiones EN PARALELO
        CompletableFuture<Product> productFuture = CompletableFuture.supplyAsync(() -> {
            try {
                return clients.getProduct(id);
            } catch (Exception e) {
                log.error("Fallo en Catálogo: {}", e.getMessage());
                return null;
            }
        });

        CompletableFuture<Inventory> inventoryFuture = CompletableFuture.supplyAsync(() -> {
            try {
                return clients.getInventory(id);
            } catch (Exception e) {
                log.error("Fallo en Inventario: {}", e.getMessage());
                return null;
            }
        });

        CompletableFuture<ExchangeRateResponse> exchangeFuture = CompletableFuture.supplyAsync(() -> {
            try {
                return clients.getExchangeRate();
            } catch (Exception e) {
                log.error("Fallo en API de divisas: {}", e.getMessage());
                return null;
            }
        });

        // 2. Esperar a que terminen las 3
        CompletableFuture.allOf(productFuture, inventoryFuture, exchangeFuture).join();

        Product product = productFuture.join();
        Inventory inventory = inventoryFuture.join();
        ExchangeRateResponse exchange = exchangeFuture.join();

        // 3. Si no hay producto, no se puede construir la respuesta
        if (product == null) {
            return new ProductSummary(id, null, 0, null, null, null, null, "ERROR");
        }

        // 4. Mapeo y transformación a MXN
        Double priceMxn = null;
        String source = null;
        if (exchange != null && exchange.rates() != null && exchange.rates().get("MXN") != null) {
            double rate = exchange.rates().get("MXN");
            priceMxn = Math.round(product.priceUsd() * rate * 100.0) / 100.0;
            source = "Frankfurter External API";
        }

        // 5. Datos de inventario
        String stockStatus = null;
        Integer units = null;
        if (inventory != null) {
            units = inventory.availableUnits();
            stockStatus = units > 0 ? "IN_STOCK" : "OUT_OF_STOCK";
        }

        // 6. Estado final: SUCCESS si todo respondió, PARTIAL si algo falló
        String status = (inventory != null && priceMxn != null) ? "SUCCESS" : "PARTIAL";

        return new ProductSummary(
                product.id(),
                product.name(),
                product.priceUsd(),
                priceMxn,
                stockStatus,
                units,
                source,
                status
        );
    }
}
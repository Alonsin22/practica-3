package mx.edu.utch.ecommerce_gateway.client;

import mx.edu.utch.ecommerce_gateway.Model.ExchangeRateResponse;
import mx.edu.utch.ecommerce_gateway.Model.Inventory;
import mx.edu.utch.ecommerce_gateway.Model.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class ServiceClients {

    private final RestClient restClient;

    @Value("${services.catalog.url}")
    private String catalogUrl;

    @Value("${services.inventory.url}")
    private String inventoryUrl;

    @Value("${services.exchange.url}")
    private String exchangeUrl;

    public ServiceClients(RestClient restClient) {
        this.restClient = restClient;
    }

    // Microservicio 1: Catálogo
    public Product getProduct(String id) {
        return restClient.get()
                .uri(catalogUrl + "/{id}", id)
                .retrieve()
                .body(Product.class);
    }

    // Microservicio 2: Inventario
    public Inventory getInventory(String id) {
        return restClient.get()
                .uri(inventoryUrl + "/{id}", id)
                .retrieve()
                .body(Inventory.class);
    }

    // Microservicio 3: API externa (Frankfurter)
    public ExchangeRateResponse getExchangeRate() {
        return restClient.get()
                .uri(exchangeUrl)
                .retrieve()
                .body(ExchangeRateResponse.class);
    }
}
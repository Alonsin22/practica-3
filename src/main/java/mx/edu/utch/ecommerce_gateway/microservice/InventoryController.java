package mx.edu.utch.ecommerce_gateway.microservice;

import mx.edu.utch.ecommerce_gateway.Model.Inventory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/microservices/inventory")
public class InventoryController {

    private static final Map<String, Inventory> INVENTORY = Map.of(
            "P-101", new Inventory("P-101", 15, "Almacén Chihuahua Norte", 3),
            "P-102", new Inventory("P-102", 0, "Almacén Monterrey", 7),
            "P-103", new Inventory("P-103", 8, "Almacén Guadalajara", 5)
    );

    @GetMapping("/{productId}")
    public ResponseEntity<Inventory> getInventory(@PathVariable String productId) {
        Inventory inventory = INVENTORY.get(productId);
        if (inventory == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(inventory);
    }
}
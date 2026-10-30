package mx.edu.utch.ecommerce_gateway.microservice;

import mx.edu.utch.ecommerce_gateway.Model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/microservices/products")
public class ProductController {

    private static final Map<String, Product> PRODUCTS = Map.of(
            "P-101", new Product("P-101", "Laptop Gamer XYZ",
                    "Laptop con procesador de casi la ultima generación y 16GB de RAM",
                    "Computadoras", 1200.00),
            "P-102", new Product("P-102", "Mouse Inalámbrico Pro",
                    "Mouse ergonómico con sensor óptico de alta precisión",
                    "Accesorios", 35.50),
            "P-103", new Product("P-103", "Monitor 27 pulgadas 4K",
                    "Monitor IPS con resolución 4K y 144Hz",
                    "Monitores", 450.00)
    );

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable String id) {
        Product product = PRODUCTS.get(id);
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(product);
    }
}
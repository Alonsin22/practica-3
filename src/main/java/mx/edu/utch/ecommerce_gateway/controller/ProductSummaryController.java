package mx.edu.utch.ecommerce_gateway.controller;

import mx.edu.utch.ecommerce_gateway.Model.ProductSummary;
import mx.edu.utch.ecommerce_gateway.service.ProductSummaryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/product-summary")
public class ProductSummaryController {

    private final ProductSummaryService service;

    public ProductSummaryController(ProductSummaryService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductSummary> getSummary(@PathVariable String id) {
        ProductSummary summary = service.getSummary(id);

        if ("ERROR".equals(summary.status())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(summary);
        }
        return ResponseEntity.ok(summary);
    }
}
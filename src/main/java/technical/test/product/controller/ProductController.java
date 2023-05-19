package technical.test.product.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import technical.test.product.persistence.entity.Product;
import technical.test.product.service.ProductService;

import java.util.Comparator;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<String> getAvailableProducts() {
        return ResponseEntity
                .ok(productService.getAvailableProducts().stream()
                        .sorted(Comparator.comparingInt(Product::getSequence))
                        .map(product -> product.getId().toString())
                        .collect(Collectors.joining(",")));
    }
}

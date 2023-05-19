package technical.test.product.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import technical.test.product.persistence.entity.Product;
import technical.test.product.persistence.repository.ProductRepository;
import technical.test.product.service.ProductService;

import java.io.IOException;
import java.util.Comparator;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<String> getAvailableProducts() throws IOException {
        var list = productService.getAvailableProducts();
        return ResponseEntity
                .ok()
                .body(list.stream()
                        .sorted(Comparator.comparingInt(Product::getSequence))
                        .map(product -> product.getId().toString())
                        .collect(Collectors.joining(",")));
    }
}

package technical.test.product.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import technical.test.product.persistence.entity.Product;
import technical.test.product.persistence.repository.ProductRepository;
import technical.test.product.persistence.repository.SizeRepository;
import technical.test.product.persistence.repository.StockRepository;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final SizeRepository sizeRepository;
    private final StockRepository stockRepository;

    public List<Product> getAvailableProducts() {
        var productList = establishRelationships();

        return productList.stream()
                .map(this::backSoonSizeFilter)
                .map(this::specialSizeFilter)
                .filter(product -> !product.getSizes().isEmpty())
                .toList();
    }

    private Product backSoonSizeFilter(Product product) {
        return Product.builder()
                .id(product.getId())
                .sequence(product.getSequence())
                .sizes(product.getSizes().stream()
                        .filter(size ->
                                size.getBackSoon() || (null != size.getStock() && size.getStock().getQuantity() > 0))
                        .toList())
                .build();
    }

    private Product specialSizeFilter(Product product) {
        boolean hasSpecialStock = product.getSizes().stream()
                .anyMatch(size ->
                        size.getBackSoon() && size.getSpecial() ||
                                (null != size.getStock() && size.getStock().getQuantity() > 0 && size.getSpecial()));

        boolean hasRegularStock = product.getSizes().stream()
                .anyMatch(size ->
                        size.getBackSoon() && !size.getSpecial()
                                || (null != size.getStock() && size.getStock().getQuantity() > 0 && !size.getSpecial()));

        if (!hasSpecialStock || !hasRegularStock) {
            return Product.builder()
                    .id(product.getId())
                    .sequence(product.getSequence())
                    .sizes(product.getSizes().stream()
                            .filter(size -> !size.getSpecial())
                            .toList())
                    .build();
        }
        return product;
    }

    private List<Product> establishRelationships() {
        try {
            var productList = productRepository.getAll();
            var sizeList = sizeRepository.getAll();
            var stockList = stockRepository.getAll();

            sizeList.forEach(size -> size.setStock(stockList.stream()
                    .filter(stock -> Objects.equals(stock.getSizeId(), size.getId()))
                    .findFirst()
                    .orElse(null)
            ));

            productList.forEach(product ->
                    product.setSizes(sizeList.stream()
                            .filter(size -> Objects.equals(product.getId(), size.getProductId()))
                            .toList()
                    ));

            return productList;
        } catch (Exception e) {
            throw new RuntimeException("Something went wrong retrieving the data");
        }
    }

}

package technical.test.product.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import technical.test.product.persistence.entity.Product;
import technical.test.product.persistence.entity.Size;
import technical.test.product.persistence.entity.Stock;
import technical.test.product.persistence.repository.ProductRepository;
import technical.test.product.persistence.repository.SizeRepository;
import technical.test.product.persistence.repository.StockRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final SizeRepository sizeRepository;
    private final StockRepository stockRepository;

    public List<Product> getAvailableProducts() {
        return getData().stream()
                .filter(product -> {
                    Set<Size> backSoonSizes = filterProductByBackSoon(product.getSizes());
                    Set<Size> specialSizes = filterProductBySpecial(
                            product.getSizes().stream()
                                    .filter(backSoonSizes::contains)
                                    .collect(Collectors.toList())
                    );
                    return product.getSizes().stream().anyMatch(specialSizes::contains);
                })
                .toList();
    }

    private List<Product> getData() {
        try {
            var productList = productRepository.getAll();
            var sizeList = sizeRepository.getAll();
            var stockList = stockRepository.getAll();

            establishRelationships(productList, sizeList, stockList);

            return productList;
        } catch (Exception e) {
            throw new RuntimeException("Something went wrong retrieving the data");
        }
    }

    private void establishRelationships(List<Product> productList, List<Size> sizeList, List<Stock> stockList) {
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
    }

    private Set<Size> filterProductByBackSoon(List<Size> sizes) {
        return sizes.stream()
                .filter(size ->
                        size.getBackSoon() || (null != size.getStock() && size.getStock().getQuantity() > 0))
                .collect(Collectors.toSet());
    }

    private Set<Size> filterProductBySpecial(List<Size> sizes) {
        boolean hasSpecialStock = sizes.stream()
                .anyMatch(size ->
                        size.getBackSoon() && size.getSpecial() ||
                                (null != size.getStock() && size.getStock().getQuantity() > 0 && size.getSpecial()));

        boolean hasRegularStock = sizes.stream()
                .anyMatch(size ->
                        size.getBackSoon() && !size.getSpecial()
                                || (null != size.getStock() && size.getStock().getQuantity() > 0 && !size.getSpecial()));

        if (!hasSpecialStock || !hasRegularStock) {
            return sizes.stream()
                    .filter(size -> !size.getSpecial())
                    .collect(Collectors.toSet());
        }
        return new HashSet<>(sizes);
    }
}

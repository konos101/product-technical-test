package technical.test.product.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import technical.test.product.persistence.repository.ProductRepository;
import technical.test.product.persistence.repository.SizeRepository;
import technical.test.product.persistence.repository.StockRepository;

import javax.net.ssl.SSLPeerUnverifiedException;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static technical.test.product.TestConstants.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private SizeRepository sizeRepository;
    @Mock
    private StockRepository stockRepository;

    private ProductService productService;


    @BeforeEach
    void setUp() {
        productService = new ProductService(productRepository, sizeRepository, stockRepository);
    }

    @Test
    void getAvailableProductsTest() throws IOException {
        // Arrange
        when(productRepository.getAll()).thenReturn(
                List.of(
                        PRODUCT_ENTITY_1,
                        PRODUCT_ENTITY_2,
                        PRODUCT_ENTITY_3
                ));
        when(sizeRepository.getAll()).thenReturn(
                List.of(
                        SIZE_ENTITY_1,
                        SIZE_ENTITY_2,
                        SIZE_ENTITY_3,
                        SIZE_ENTITY_4,
                        SIZE_ENTITY_5,
                        SIZE_ENTITY_6
                ));
        when(stockRepository.getAll()).thenReturn(
                List.of(
                        STOCK_ENTITY_1,
                        STOCK_ENTITY_2,
                        STOCK_ENTITY_3
                ));

        // Act
        var response = productService.getAvailableProducts();

        // Assert
        assertEquals(2, response.size());
        assertEquals(1, response.get(0).getId());
        assertEquals(2, response.get(1).getId());

        verify(productRepository, times(1)).getAll();
        verify(sizeRepository, times(1)).getAll();
        verify(stockRepository, times(1)).getAll();
    }

    @Test
    void getAvailableProductsTest_specialSizes() throws IOException {
        // Arrange
        when(productRepository.getAll()).thenReturn(
                List.of(
                        PRODUCT_ENTITY_4
                ));
        when(sizeRepository.getAll()).thenReturn(
                List.of(
                        SIZE_ENTITY_7,
                        SIZE_ENTITY_8
                ));
        when(stockRepository.getAll()).thenReturn(
                List.of(
                        STOCK_ENTITY_4,
                        STOCK_ENTITY_5
                ));

        // Act
        var response = productService.getAvailableProducts();

        // Assert
        assertEquals(1, response.size());
        assertEquals(4, response.get(0).getId());

        verify(productRepository, times(1)).getAll();
        verify(sizeRepository, times(1)).getAll();
        verify(stockRepository, times(1)).getAll();
    }

    @Test
    void getAvailableProductsTest_BackSoonSizes() throws IOException {
        // Arrange
        when(productRepository.getAll()).thenReturn(
                List.of(
                        PRODUCT_ENTITY_5
                ));
        when(sizeRepository.getAll()).thenReturn(
                List.of(
                        SIZE_ENTITY_9
                ));
        when(stockRepository.getAll()).thenReturn(
                List.of());

        // Act
        var response = productService.getAvailableProducts();

        // Assert
        assertEquals(1, response.size());
        assertEquals(5, response.get(0).getId());

        verify(productRepository, times(1)).getAll();
        verify(sizeRepository, times(1)).getAll();
        verify(stockRepository, times(1)).getAll();
    }

    @Test
    void getAvailableProductsTest_whenNoSizesFound() throws IOException {
        // Arrange
        when(productRepository.getAll()).thenReturn(
                List.of(PRODUCT_ENTITY_5));
        when(sizeRepository.getAll()).thenReturn(
                List.of());
        when(stockRepository.getAll()).thenReturn(
                List.of(STOCK_ENTITY_5));

        // Act
        var response = productService.getAvailableProducts();

        // Assert
        assertEquals(0, response.size());

        verify(productRepository, times(1)).getAll();
        verify(sizeRepository, times(1)).getAll();
        verify(stockRepository, times(1)).getAll();
    }

    @Test
    void getAvailableProductsTest_returnNoResults() throws IOException {
        // Arrange
        when(productRepository.getAll()).thenReturn(
                List.of(
                        PRODUCT_ENTITY_3
                ));
        when(sizeRepository.getAll()).thenReturn(
                List.of(
                        SIZE_ENTITY_5,
                        SIZE_ENTITY_6
                ));
        when(stockRepository.getAll()).thenReturn(
                List.of(
                        STOCK_ENTITY_3
                ));

        // Act
        var response = productService.getAvailableProducts();

        // Assert
        assertEquals(0, response.size());

        verify(productRepository, times(1)).getAll();
        verify(sizeRepository, times(1)).getAll();
        verify(stockRepository, times(1)).getAll();
    }

    @Test
    void getAvailableProductsTest_returnException() throws IOException {
        // Arrange
        when(productRepository.getAll()).thenThrow(new IOException());

        // Act
        var response = assertThrows(RuntimeException.class, () -> productService.getAvailableProducts());

        //Assert
        assertEquals("Something went wrong retrieving the data", response.getMessage());
    }

}
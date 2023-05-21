package technical.test.product.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import technical.test.product.persistence.entity.Product;
import technical.test.product.service.ProductService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductService productService;

    private ProductController productController;

    @BeforeEach
    void setUp() {
        productController = new ProductController(productService);
    }

    @Test
    void getAvailableProductsTest() {
        // Arrange
        var productList = List.of(
                Product.builder()
                        .id(1)
                        .sequence(2)
                        .build(),
                Product.builder()
                        .id(5)
                        .sequence(1)
                        .build()
        );
        when(productService.getAvailableProducts()).thenReturn(productList);

        // Act
        var response = productController.getAvailableProducts();

        // Assert
        assertTrue(HttpStatus.OK.isSameCodeAs(response.getStatusCode()));
        assertEquals("5,1", response.getBody());
    }

    @Test
    void getAvailableProductsTest_whenNothingIsReturned() {
        // Arrange

        when(productService.getAvailableProducts()).thenReturn(List.of());

        // Act
        var response = productController.getAvailableProducts();

        // Assert
        assertTrue(HttpStatus.OK.isSameCodeAs(response.getStatusCode()));
        assertEquals("", response.getBody());
    }
}
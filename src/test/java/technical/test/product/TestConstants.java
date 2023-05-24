package technical.test.product;

import technical.test.product.persistence.entity.Product;
import technical.test.product.persistence.entity.Size;
import technical.test.product.persistence.entity.Stock;

public class TestConstants {

    public static final Stock STOCK_ENTITY_1 = Stock.builder()
            .sizeId(1)
            .quantity(2)
            .build();
    public static final Stock STOCK_ENTITY_2 = Stock.builder()
            .sizeId(2)
            .quantity(0)
            .build();

    public static final Stock STOCK_ENTITY_3 = Stock.builder()
            .sizeId(6)
            .quantity(0)
            .build();

    public static final Stock STOCK_ENTITY_4 = Stock.builder()
            .sizeId(7)
            .quantity(0)
            .build();

    public static final Stock STOCK_ENTITY_5 = Stock.builder()
            .sizeId(8)
            .quantity(10)
            .build();

    public static final Size SIZE_ENTITY_1 = Size.builder()
            .id(1)
            .backSoon(false)
            .productId(1)
            .special(false)
            .build();

    public static final Size SIZE_ENTITY_2 = Size.builder()
            .id(2)
            .backSoon(true)
            .productId(1)
            .special(false)
            .build();

    public static final Size SIZE_ENTITY_3 = Size.builder()
            .id(3)
            .backSoon(false)
            .productId(1)
            .special(true)
            .build();

    public static final Size SIZE_ENTITY_4 = Size.builder()
            .id(4)
            .backSoon(true)
            .productId(2)
            .special(false)
            .build();

    public static final Size SIZE_ENTITY_5 = Size.builder()
            .id(5)
            .backSoon(true)
            .productId(3)
            .special(true)
            .build();

    public static final Size SIZE_ENTITY_6 = Size.builder()
            .id(6)
            .backSoon(false)
            .productId(3)
            .special(false)
            .build();

    public static final Size SIZE_ENTITY_7 = Size.builder()
            .id(7)
            .backSoon(true)
            .productId(4)
            .special(true)
            .build();

    public static final Size SIZE_ENTITY_8 = Size.builder()
            .id(8)
            .backSoon(false)
            .productId(4)
            .special(false)
            .build();

    public static final Size SIZE_ENTITY_9 = Size.builder()
            .id(9)
            .backSoon(true)
            .productId(5)
            .special(false)
            .build();

    public static final Product PRODUCT_ENTITY_1 = Product.builder()
            .id(1)
            .sequence(3)
            .build();

    public static final Product PRODUCT_ENTITY_2 = Product.builder()
            .id(2)
            .sequence(1)
            .build();

    public static final Product PRODUCT_ENTITY_3 = Product.builder()
            .id(3)
            .sequence(2)
            .build();

    public static final Product PRODUCT_ENTITY_4 = Product.builder()
            .id(4)
            .sequence(10)
            .build();

    public static final Product PRODUCT_ENTITY_5 = Product.builder()
            .id(5)
            .sequence(4)
            .build();
}

package technical.test.product.persistence.entity;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Stock {
    private Integer sizeId;
    private Integer quantity;
}

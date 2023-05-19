package technical.test.product.persistence.entity;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private Integer id;
    private Integer sequence;
    private List<Size> sizes;
}

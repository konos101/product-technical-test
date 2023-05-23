package technical.test.product.persistence.entity;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Size {
    private Integer id;
    private Integer productId;
    private Boolean backSoon;
    private Boolean special;
    private Stock stock;
}

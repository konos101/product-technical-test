package technical.test.product.persistence.entity;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Size {
    private Integer id;
    private Integer productId;
    private Boolean backSoon;
    private Boolean special;
}

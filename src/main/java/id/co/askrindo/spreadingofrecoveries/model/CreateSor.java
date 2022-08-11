package id.co.askrindo.spreadingofrecoveries.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Setter
@Getter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CreateSor {
    @NotBlank
    public Integer productId;

    @Override
    public String toString() {
        return "CreateSoc{product_id=" + productId + "}";
    }
}
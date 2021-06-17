package ua.com.shyrkov.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionInfoDto {

    private Long id;
    private Instant timestamp;
    private Double amount;
    private String description;
    private String transactionType;
    private List<String> categories = new ArrayList<>();

}

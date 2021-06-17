package ua.com.shyrkov.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.com.shyrkov.entity.AccountEntity;
import ua.com.shyrkov.entity.CategoryEntity;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {

    private Long id;
    private Instant timestamp;
    private Double amount;
    private String description;
    private AccountEntity accountEntity;
    private List<CategoryEntity> categoryEntities;


}

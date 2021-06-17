package ua.com.shyrkov.entity;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "transactions")
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "date")
    private Instant timestamp;
    @Column(name = "amount")
    private Double amount;
    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private AccountEntity accountEntity;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "transaction_categories",
            joinColumns = @JoinColumn(name = "transaction_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id")
    )
    private List<CategoryEntity> categoryEntities = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AccountEntity getAccountEntity() {
        return accountEntity;
    }

    public void setAccountEntity(AccountEntity accountEntity) {
        this.accountEntity = accountEntity;
    }

    public List<CategoryEntity> getCategoryEntities() {
        return categoryEntities;
    }

    public void setCategoryEntities(List<CategoryEntity> categoryEntities) {
        this.categoryEntities = categoryEntities;
    }
}

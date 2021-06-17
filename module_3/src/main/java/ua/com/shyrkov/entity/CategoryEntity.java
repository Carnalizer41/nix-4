package ua.com.shyrkov.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id")
    private CategoryTypeEntity categoryTypeEntity;

    @ManyToMany(mappedBy = "categoryEntities", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<TransactionEntity> transactionEntities = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CategoryTypeEntity getCategoryTypeEntity() {
        return categoryTypeEntity;
    }

    public void setCategoryTypeEntity(CategoryTypeEntity categoryTypeEntity) {
        this.categoryTypeEntity = categoryTypeEntity;
    }

    public List<TransactionEntity> getTransactionEntities() {
        return transactionEntities;
    }

    public void setTransactionEntities(List<TransactionEntity> transactionEntities) {
        this.transactionEntities = transactionEntities;
    }
}

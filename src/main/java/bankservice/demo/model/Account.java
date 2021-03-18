package bankservice.demo.model;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import lombok.Data;
import lombok.ToString;

@Data
@Entity(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "account_number", unique = true, nullable = false)
    private String accountNumber;
    @Enumerated(EnumType.STRING)
    private Currency currency;
    @Min(0)
    private BigDecimal balance;
    @Column(name = "is_active", columnDefinition = "boolean default true", nullable = false)
    private boolean isActive;
    @ManyToOne
    @ToString.Exclude
    private User user;
}

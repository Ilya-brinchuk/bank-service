package bankservice.demo.model;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Data;

@Entity(name = "transactions")
@Data
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "account_from")
    private Account accountFrom;
    @ManyToOne
    @JoinColumn(name = "account_to")
    private Account accountTo;
    private double amount;
    private LocalDateTime dateTime;
    @Enumerated(EnumType.STRING)
    private Type type;

    public enum Type {
        INCOMING, OUTGOING
    }
}


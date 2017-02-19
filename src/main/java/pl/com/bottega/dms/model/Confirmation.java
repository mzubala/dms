package pl.com.bottega.dms.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Confirmation {

    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    @AttributeOverride(name="id", column = @Column(name = "ownerId"))
    private EmployeeId owner;

    private LocalDateTime confirmationDate;

    @Embedded
    @AttributeOverride(name="id", column = @Column(name = "proxyId"))
    private EmployeeId proxy;

    public Confirmation(EmployeeId owner) {
        this.owner = owner;
    }

    public boolean isOwnedBy(EmployeeId employeeId) {
        return employeeId.equals(owner);
    }

    public boolean isConfirmed() {
        return confirmationDate != null;
    }

    public void confirm() {
        confirmationDate = LocalDateTime.now();
    }

    public void confirmFor(EmployeeId proxy) {
        confirm();
        this.proxy = proxy;
    }
}

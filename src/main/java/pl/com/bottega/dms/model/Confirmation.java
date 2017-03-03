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

    Confirmation() {}

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
        if(isConfirmed())
            throw new DocumentStatusException(String.format("Employee %s has already confirmed", owner));
        confirmationDate = LocalDateTime.now();
    }

    public void confirmFor(EmployeeId proxy) {
        if(proxy.equals(owner))
            throw new DocumentStatusException("Employee is the same as proxy employee");
        confirm();
        this.proxy = proxy;
    }

    public LocalDateTime getConfirmationDate() {
        return confirmationDate;
    }

    public EmployeeId getOwner() {
        return owner;
    }

    public EmployeeId getProxy() {
        return proxy;
    }

    public boolean hasProxy() {
        return proxy != null;
    }
}

package pl.com.bottega.dms.application;

import java.time.LocalDateTime;

public class ConfirmationDto {

    private boolean confirmed;
    private Long ownerEmployeeId;
    private Long proxyEmployeeId;
    private LocalDateTime confirmedAt;

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public Long getOwnerEmployeeId() {
        return ownerEmployeeId;
    }

    public void setOwnerEmployeeId(Long ownerEmployeeId) {
        this.ownerEmployeeId = ownerEmployeeId;
    }

    public LocalDateTime getConfirmedAt() {
        return confirmedAt;
    }

    public void setConfirmedAt(LocalDateTime confirmedAt) {
        this.confirmedAt = confirmedAt;
    }

    public Long getProxyEmployeeId() {
        return proxyEmployeeId;
    }

    public void setProxyEmployeeId(Long proxyEmployeeId) {
        this.proxyEmployeeId = proxyEmployeeId;
    }
}

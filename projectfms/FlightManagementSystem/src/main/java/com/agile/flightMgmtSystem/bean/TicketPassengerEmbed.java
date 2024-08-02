package com.agile.flightMgmtSystem.bean;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class TicketPassengerEmbed implements Serializable {
    @NotNull
    private Long ticketNumber;
    @NotNull
    private Integer serialNumber;

    public TicketPassengerEmbed() {
        super();
    }

    public TicketPassengerEmbed(Long ticketNumber, Integer serialNumber) {
        super();
        this.ticketNumber = ticketNumber;
        this.serialNumber = serialNumber;
    }

    // Getter and setter methods
    public Long getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(Long ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public Integer getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(Integer serialNumber) {
        this.serialNumber = serialNumber;
    }

    // Override equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TicketPassengerEmbed that = (TicketPassengerEmbed) o;
        return Objects.equals(ticketNumber, that.ticketNumber) &&
                Objects.equals(serialNumber, that.serialNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticketNumber, serialNumber);
    }
}

package com.mastery.java.task.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Entity
public class Passport {
    @Id
    @Column(name = "passport_id")
    private String passportId;
    @Column(name = "serial_number")
    @NotBlank
    private String serialNumber;
    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "end_date")
    private LocalDate endDate;

    public Passport(Builder builder) {
        this.passportId = builder.passportId;
        this.serialNumber = builder.serialNumber;
        this.startDate = builder.startDate;
        this.endDate = builder.endDate;
    }

    public Passport() {

    }

    public static class Builder {
        private String passportId;
        private String serialNumber;
        private LocalDate startDate;
        private LocalDate endDate;

        public Builder passportID(String passportId) {
            this.passportId = passportId;
            return this;
        }

        public Builder serialNumber(String serialNumber) {
            this.serialNumber = serialNumber;
            return this;
        }

        public Builder startDate(int year, int month, int day) {
            this.startDate = LocalDate.of(year, month, day);
            return this;
        }

        public Builder endDate(int year, int month, int day) {
            this.endDate = LocalDate.of(year, month, day);
            return this;
        }

        public Passport build() {
            return new Passport(this);
        }
    }
}

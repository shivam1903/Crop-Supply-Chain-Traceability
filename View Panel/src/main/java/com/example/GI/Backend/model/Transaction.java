package com.example.GI.Backend.model;


import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "initial_transaction")
public class Transaction {

    @Id
    @SequenceGenerator(name="initial_transaction_id_seq_a",
            sequenceName="initial_transaction_id_seq_a",
            allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator="initial_transaction_id_seq_a")
    @Column(name = "transaction_id", nullable = false)
    private Integer transaction_id;

    @Column(name = "actor_id")
    private Integer actor_id;

    @Column(name = "comment")
    private String comment;

    @Column(name = "crop")
    private String crop;

    @Column(name = "entry_date")
    private LocalDate entry_date;

    @Column(name = "geographical_coordinates")
    private String geographical_coordinates;

    @Column(name = "level")
    private Integer level;

    @Column(name = "parent_node")
    private String parent_node;

    @Column(name = "payment")
    private String payment;

    @Column(name = "price_per_unit")
    private BigDecimal price_per_unit;

    @Column(name = "process_type")
    private String process_type;

    @Column(name = "project_id")
    private String project_id;

    @Column(name = "quantity")
    private BigDecimal quantity;

    @Column(name = "status")
    private String status;

    @Column(name = "total_amount")
    private BigDecimal total_amount;

    @Column(name = "transaction_date")
    private LocalDate transaction_date;

    @Column(name = "transaction_document")
    private String transaction_document;

    @Column(name = "measuring_unit")
    private String measuring_unit;

    @Column(name = "no_of_units")
    private Integer no_of_units;

    @Column(name = "updated_date")
    private LocalDate updated_date;

    public Integer getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(Integer transaction_id) {
        this.transaction_id = transaction_id;
    }

    public Integer getActor_id() {
        return actor_id;
    }

    public void setActor_id(Integer actor_id) {
        this.actor_id = actor_id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCrop() {
        return crop;
    }

    public void setCrop(String crop) {
        this.crop = crop;
    }

    public LocalDate getEntry_date() {
        return entry_date;
    }

    public void setEntry_date(LocalDate entry_date) {
        this.entry_date = entry_date;
    }

    public String getGeographical_coordinates() {
        return geographical_coordinates;
    }

    public void setGeographical_coordinates(String geographical_coordinates) {
        this.geographical_coordinates = geographical_coordinates;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getParent_node() {
        return parent_node;
    }

    public void setParent_node(String parent_node) {
        this.parent_node = parent_node;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public BigDecimal getPrice_per_unit() {
        return price_per_unit;
    }

    public void setPrice_per_unit(BigDecimal price_per_unit) {
        this.price_per_unit = price_per_unit;
    }

    public String getProcess_type() {
        return process_type;
    }

    public void setProcess_type(String process_type) {
        this.process_type = process_type;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(BigDecimal total_amount) {
        this.total_amount = total_amount;
    }

    public LocalDate getTransaction_date() {
        return transaction_date;
    }

    public void setTransaction_date(LocalDate transaction_date) {
        this.transaction_date = transaction_date;
    }

    public String getTransaction_document() {
        return transaction_document;
    }

    public void setTransaction_document(String transaction_document) {
        this.transaction_document = transaction_document;
    }

    public String getMeasuring_unit() {
        return measuring_unit;
    }

    public void setMeasuring_unit(String measuring_unit) {
        this.measuring_unit = measuring_unit;
    }

    public Integer getNo_of_units() {
        return no_of_units;
    }

    public void setNo_of_units(Integer no_of_units) {
        this.no_of_units = no_of_units;
    }

    public LocalDate getUpdated_date() {
        return updated_date;
    }

    public void setUpdated_date(LocalDate updated_date) {
        this.updated_date = updated_date;
    }
}
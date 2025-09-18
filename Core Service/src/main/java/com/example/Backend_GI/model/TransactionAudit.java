package com.example.Backend_GI.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "transaction_audit_trail")
public class TransactionAudit {

    @Id
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

    @Column(name = "parentNode")
    private String parentNode;

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

    @Column(name = "updatedDate")
    private LocalDate updatedDate;

    @Column(name = "problem_reported")
    private boolean problem_reported;

    @Column(name = "problem_comment")
    private String problem_comment;

    @Column(name = "team_a_status")
    private String team_a_status;
    // Skipped - means no comment added by the trader
    // Pending_Assignment - means the Admin needs to assign someone
    // Pending_Check - means member needs to check
    // Approved - means member has checked and sent forward
    // Waiting - Waiting for previous team

    @Column(name = "team_a_assigned_id")
    private String team_a_assigned_id;

    @Column(name = "team_a_timestamp")
    private LocalDate team_a_timestamp;

    @Column(name = "team_a_remark")
    private String team_a_remark;

    @Column(name = "team_b_status")
    private String team_b_status;

    @Column(name = "team_b_assigned_id")
    private String team_b_assigned_id;

    @Column(name = "team_b_timestamp")
    private LocalDate team_b_timestamp;

    @Column(name = "team_b_remark")
    private String team_b_remark;

    @Column(name = "team_c_status")
    private String team_c_status;

    @Column(name = "team_c_assigned_id")
    private String team_c_assigned_id;

    @Column(name = "team_c_timestamp")
    private LocalDate team_c_timestamp;

    @Column(name = "team_c_remark")
    private String team_c_remark;

    @Column(name = "team_d_status")
    private String team_d_status;

    @Column(name = "team_d_assigned_id")
    private String team_d_assigned_id;

    @Column(name = "team_d_timestamp")
    private LocalDate team_d_timestamp;

    @Column(name = "team_d_remark")
    private String team_d_remark;

    @Column(name = "transaction_hash")
    private String transaction_hash;

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

    public String getParentNode() {
        return parentNode;
    }

    public void setParentNode(String parentNode) {
        this.parentNode = parentNode;
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

    public LocalDate getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDate updatedDate) {
        this.updatedDate = updatedDate;
    }

    public boolean isProblem_reported() {
        return problem_reported;
    }

    public void setProblem_reported(boolean problem_reported) {
        this.problem_reported = problem_reported;
    }

    public String getProblem_comment() {
        return problem_comment;
    }

    public void setProblem_comment(String problem_comment) {
        this.problem_comment = problem_comment;
    }

    public String getTeam_a_status() {
        return team_a_status;
    }

    public void setTeam_a_status(String team_a_status) {
        this.team_a_status = team_a_status;
    }

    public String getTeam_a_assigned_id() {
        return team_a_assigned_id;
    }

    public void setTeam_a_assigned_id(String team_a_assigned_id) {
        this.team_a_assigned_id = team_a_assigned_id;
    }

    public LocalDate getTeam_a_timestamp() {
        return team_a_timestamp;
    }

    public void setTeam_a_timestamp(LocalDate team_a_timestamp) {
        this.team_a_timestamp = team_a_timestamp;
    }

    public String getTeam_a_remark() {
        return team_a_remark;
    }

    public void setTeam_a_remark(String team_a_remark) {
        this.team_a_remark = team_a_remark;
    }

    public String getTeam_b_status() {
        return team_b_status;
    }

    public void setTeam_b_status(String team_b_status) {
        this.team_b_status = team_b_status;
    }

    public String getTeam_b_assigned_id() {
        return team_b_assigned_id;
    }

    public void setTeam_b_assigned_id(String team_b_assigned_id) {
        this.team_b_assigned_id = team_b_assigned_id;
    }

    public LocalDate getTeam_b_timestamp() {
        return team_b_timestamp;
    }

    public void setTeam_b_timestamp(LocalDate team_b_timestamp) {
        this.team_b_timestamp = team_b_timestamp;
    }

    public String getTeam_b_remark() {
        return team_b_remark;
    }

    public void setTeam_b_remark(String team_b_remark) {
        this.team_b_remark = team_b_remark;
    }

    public String getTeam_c_status() {
        return team_c_status;
    }

    public void setTeam_c_status(String team_c_status) {
        this.team_c_status = team_c_status;
    }

    public String getTeam_c_assigned_id() {
        return team_c_assigned_id;
    }

    public void setTeam_c_assigned_id(String team_c_assigned_id) {
        this.team_c_assigned_id = team_c_assigned_id;
    }

    public LocalDate getTeam_c_timestamp() {
        return team_c_timestamp;
    }

    public void setTeam_c_timestamp(LocalDate team_c_timestamp) {
        this.team_c_timestamp = team_c_timestamp;
    }

    public String getTeam_c_remark() {
        return team_c_remark;
    }

    public void setTeam_c_remark(String team_c_remark) {
        this.team_c_remark = team_c_remark;
    }

    public String getTeam_d_status() {
        return team_d_status;
    }

    public void setTeam_d_status(String team_d_status) {
        this.team_d_status = team_d_status;
    }

    public String getTeam_d_assigned_id() {
        return team_d_assigned_id;
    }

    public void setTeam_d_assigned_id(String team_d_assigned_id) {
        this.team_d_assigned_id = team_d_assigned_id;
    }

    public LocalDate getTeam_d_timestamp() {
        return team_d_timestamp;
    }

    public void setTeam_d_timestamp(LocalDate team_d_timestamp) {
        this.team_d_timestamp = team_d_timestamp;
    }

    public String getTeam_d_remark() {
        return team_d_remark;
    }

    public void setTeam_d_remark(String team_d_remark) {
        this.team_d_remark = team_d_remark;
    }

    public String getTransaction_hash() {
        return transaction_hash;
    }

    public void setTransaction_hash(String transaction_hash) {
        this.transaction_hash = transaction_hash;
    }
}

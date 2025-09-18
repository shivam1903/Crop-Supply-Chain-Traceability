package com.example.GI.Backend.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "transaction_audit_trail")
public class TransactionAuditTrail {

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

    @Column(name = "parent_node")
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

    @Override
    public String toString() {
        return "TransactionAudit{" +
                "transaction_id=" + transaction_id +
                ", actor_id='" + actor_id + '\'' +
                ", comment='" + comment + '\'' +
                ", crop='" + crop + '\'' +
                ", entry_date=" + entry_date +
                ", geographical_coordinates='" + geographical_coordinates + '\'' +
                ", level=" + level +
                ", parentNode='" + parentNode + '\'' +
                ", payment='" + payment + '\'' +
                ", price_per_unit=" + price_per_unit +
                ", process_type='" + process_type + '\'' +
                ", project_id='" + project_id + '\'' +
                ", quantity=" + quantity +
                ", status='" + status + '\'' +
                ", total_amount=" + total_amount +
                ", transaction_date=" + transaction_date +
                ", transaction_document='" + transaction_document + '\'' +
                ", measuring_unit='" + measuring_unit + '\'' +
                ", no_of_units='" + no_of_units + '\'' +
                ", updatedDate=" + updatedDate +
                ", problem_reported=" + problem_reported +
                ", problem_comment='" + problem_comment + '\'' +
                '}';
    }
}

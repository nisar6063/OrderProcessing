package com.order.processor.bean;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProcuralItemBean {
    private int id;
    private String itemName;
    private int quantity;
    private int supplierId;
    private String status;
    private int userId;
    public ProcuralItemBean() {
        super();
    }
    public ProcuralItemBean(int id, String itemName, int quantity, int supplierId, String status, int userId) {
        super();
        this.id = id;
        this.itemName = itemName;
        this.quantity = quantity;
        this.supplierId = supplierId;
        this.status = status;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "ProcuralItemBean [id=" + id + ", itemName=" + itemName + ", quantity=" + quantity + ", supplierId="
                + supplierId + ", status=" + status + ", userId=" + userId + "]";
    }

}

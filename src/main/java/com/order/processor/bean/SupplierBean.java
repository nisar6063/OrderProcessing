package com.order.processor.bean;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SupplierBean {
    private int id;
    private String supplierName;
    private String contact;
    private int userId;
    public SupplierBean() {
        super();
    }
    public SupplierBean(int id, String supplierName, String contact, int userId) {
        super();
        this.id = id;
        this.supplierName = supplierName;
        this.contact = contact;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "SupplierBean [id=" + id + ", supplierName=" + supplierName + ", contact=" + contact + ", userId="
                + userId + "]";
    }

}

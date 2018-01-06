package com.order.processor.bean;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BuyLeadItemBean {
    private int id;
    private int buyLeadId;
    private String itemName;
    private int quantity;
    private String category;
    private String rate;
    private String gst;
    private String total;
    private String remarks;
    private int userId;
    public BuyLeadItemBean() {
        super();
    }
    public BuyLeadItemBean(int id, int buyLeadId, String itemName, int quantity, String category, String rate,
            String gst, String total, String remarks) {
        super();
        this.id = id;
        this.buyLeadId = buyLeadId;
        this.itemName = itemName;
        this.quantity = quantity;
        this.category = category;
        this.rate = rate;
        this.gst = gst;
        this.total = total;
        this.remarks = remarks;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBuyLeadId() {
        return buyLeadId;
    }

    public void setBuyLeadId(int buyLeadId) {
        this.buyLeadId = buyLeadId;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    @Override
    public String toString() {
        return "BuyLeadItemBean [id=" + id + ", buyLeadId=" + buyLeadId + ", itemName=" + itemName + ", quantity="
                + quantity + ", category=" + category + ", rate=" + rate + ", gst=" + gst + ", total=" + total
                + ", remarks=" + remarks + "]";
    }

}

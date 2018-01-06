package com.order.processor.bean;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BuyLeadBean {
    private int id;
    private CustomerBean custBean;
    private String status;
    private int followUpCount;
    private int userId;
    private List<BuyLeadItemBean> buyLeadItems;
    private String uniqueCode;
    public BuyLeadBean() {
        super();
    }
    public BuyLeadBean(int id, CustomerBean custBean, String status, int followUpCount, int userId,
            List<BuyLeadItemBean> buyLeadItems, String uniqueCode) {
        super();
        this.id = id;
        this.custBean = custBean;
        this.status = status;
        this.followUpCount = followUpCount;
        this.userId = userId;
        this.buyLeadItems = buyLeadItems;
        this.uniqueCode = uniqueCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getFollowUpCount() {
        return followUpCount;
    }

    public void setFollowUpCount(int followUpCount) {
        this.followUpCount = followUpCount;
    }

    public List<BuyLeadItemBean> getBuyLeadItems() {
        return buyLeadItems;
    }

    public void setBuyLeadItems(List<BuyLeadItemBean> buyLeadItems) {
        this.buyLeadItems = buyLeadItems;
    }

    public CustomerBean getCustBean() {
        return custBean;
    }

    public void setCustBean(CustomerBean custBean) {
        this.custBean = custBean;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUniqueCode() {
        return uniqueCode;
    }

    public void setUniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode;
    }

    @Override
    public String toString() {
        return "BuyLeadBean [id=" + id + ", custBean=" + custBean + ", status=" + status + ", followUpCount="
                + followUpCount + ", userId=" + userId + ", buyLeadItems=" + buyLeadItems + ", uniqueCode=" + uniqueCode
                + "]";
    }

}

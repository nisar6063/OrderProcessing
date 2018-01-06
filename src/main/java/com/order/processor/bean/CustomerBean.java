package com.order.processor.bean;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerBean {
    private int id;
    private String custName;
    private String custLocation;
    private String custPhone;
    private String custEmail;
    private int userId;
    public CustomerBean() {
        super();
    }
    public CustomerBean(int id, String custName, String custLocation, String custPhone, String custEmail, int userId) {
        super();
        this.id = id;
        this.custName = custName;
        this.custLocation = custLocation;
        this.custPhone = custPhone;
        this.custEmail = custEmail;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustLocation() {
        return custLocation;
    }

    public void setCustLocation(String custLocation) {
        this.custLocation = custLocation;
    }

    public String getCustPhone() {
        return custPhone;
    }

    public void setCustPhone(String custPhone) {
        this.custPhone = custPhone;
    }

    public String getCustEmail() {
        return custEmail;
    }

    public void setCustEmail(String custEmail) {
        this.custEmail = custEmail;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "CustomerBean [id=" + id + ", custName=" + custName + ", custLocation=" + custLocation + ", custPhone="
                + custPhone + ", custEmail=" + custEmail + ", userId=" + userId + "]";
    }
}

package com.order.processor.service;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import com.order.processor.bean.BuyLeadBean;
import com.order.processor.bean.BuyLeadItemBean;
import com.order.processor.bean.BuyLeadStatus;
import com.order.processor.bean.CustomerBean;
import com.order.processor.bean.ProcuralItemBean;
import com.order.processor.bean.SupplierBean;
import com.order.processor.bean.UserBean;
import com.order.processor.util.PropertyUtil;

public class Orm {
    private OrmUtils ormUtils = new OrmUtils();
    private String dbName;

    public Orm() {
        this.dbName = new PropertyUtil().getProperty("DB_NAME");
    }

    public String getEncodedString(String plainString) {
        try {
            if ((plainString != null) || (plainString != ""))
                return Base64.getEncoder().encodeToString(plainString.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public int addUser(UserBean user) {
        String query = " INSERT INTO " + dbName + ".USER_LOGIN (NAME, PASSWORD, ROLE, CREATED_BY) VALUES (?,?,?,?)";
        int userId = 0;
        String password = getEncodedString(user.getPassword());
        try (Connection conn = ormUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getUsername().toUpperCase());
            stmt.setString(2, password);
            String role = user.getUserRole();
            if ((role == null) || (role.equals("")) || (role.trim().length() == 0))
                role = "USER";
            stmt.setString(3, role.toUpperCase());
            if (user.getUserId() == 0)
                stmt.setInt(4, 0);
            else
                stmt.setInt(4, user.getUserId());
            stmt.execute();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                userId = rs.getInt(1);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userId;
    }

    public int addCustomer(CustomerBean customerBean) throws Exception{
        String query = " INSERT INTO " + dbName
                + ".CUSTOMER (CUST_NAME, CUST_LOCATION, CUST_PHONE, CUST_EMAIL, CREATED_BY) VALUES (?,?,?,?,?)";
        int custId = 0;
        try (Connection conn = ormUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, customerBean.getCustName());
            stmt.setString(2, customerBean.getCustLocation());
            stmt.setString(3, customerBean.getCustPhone());
            stmt.setString(4, customerBean.getCustEmail());
            stmt.setInt(5, customerBean.getUserId());
            stmt.execute();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                custId = rs.getInt(1);
            }
        } catch (Exception e) {
            throw e;
        }
        return custId;
    }

    public int addBuyLead(BuyLeadBean buyLeadBean) throws Exception{
        Orm orm = new Orm();
        String query = " INSERT INTO " + dbName + ".BUYLEADS (CUST_ID, STATUS, CREATED_BY) VALUES (?,?,?)";
        int buyLeadId = 0;
        try (Connection conn = ormUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, orm.addCustomer(buyLeadBean.getCustBean()));
            stmt.setString(2, BuyLeadStatus.CREATED.toString());
            stmt.setInt(3, buyLeadBean.getUserId());
            stmt.execute();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                buyLeadId = rs.getInt(1);
            }
            for (BuyLeadItemBean itemBean : buyLeadBean.getBuyLeadItems()) {
                itemBean.setBuyLeadId(buyLeadId);
                itemBean.setUserId(buyLeadBean.getUserId());
                int buyLeadItemId = orm.addBuyLeadItem(itemBean);
                if (buyLeadItemId == 0)
                    return 0;
            }
        } catch (Exception e) {
            throw e;
        }
        return buyLeadId;
    }

    public int addBuyLeadItem(BuyLeadItemBean itemBean) throws Exception {
        String query = " INSERT INTO " + dbName
                + ".BUYLEADSITEMS (BUYLEAD_ID, ITEM_NAME, QUANTITY, CATEGORY, REMARKS, CREATED_BY) VALUES (?,?,?,?,?,?)";
        int buyLeadItemId = 0;
        try (Connection conn = ormUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, itemBean.getBuyLeadId());
            stmt.setString(2, itemBean.getItemName());
            stmt.setInt(3, itemBean.getQuantity());
            stmt.setString(4, itemBean.getCategory());
            stmt.setString(5, itemBean.getRemarks());
            stmt.setInt(6, itemBean.getUserId());
            stmt.execute();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                buyLeadItemId = rs.getInt(1);
            }
        } catch (Exception e) {
            throw e;
        }
        return buyLeadItemId;
    }

    public int updateBuyLead(BuyLeadBean buyLeadBean) throws Exception {
        int rows = 0;
        boolean error = false;
        String sql = "UPDATE " + dbName + ".BUYLEADSITEMS SET RATE=?,GST=?,TOTAL=? WHERE ID=? AND BUYLEAD_ID=?";
        for (BuyLeadItemBean itemBean : buyLeadBean.getBuyLeadItems()) {
            try (Connection conn = ormUtils.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, itemBean.getRate());
                stmt.setString(2, itemBean.getGst());
                stmt.setString(3, itemBean.getTotal());
                stmt.setInt(4, itemBean.getId());
                stmt.setInt(5, itemBean.getBuyLeadId());
                rows = stmt.executeUpdate();
            } catch (Exception e) {
                error = true;
                throw e;
            }
        }
        if (!error) {
            String query = "UPDATE " + dbName + ".BUYLEADS SET STATUS=? WHERE ID=?";
            try (Connection conn = ormUtils.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, BuyLeadStatus.RATEUPDATED.toString());
                stmt.setInt(2, buyLeadBean.getId());
                stmt.executeUpdate();
            } catch (Exception e) {
                throw e;
            }
        }
        return rows;
    }

    public int updateBuyLeadStatus(BuyLeadBean buyLeadBean, String status, String uniqueCode) throws Exception {
        int rows = 0;
        String query = "UPDATE " + dbName + ".BUYLEADS SET STATUS=?";
        if (uniqueCode != null) {
            query = query + ",UNIQUECODE=?";
        }
        query = query + " WHERE ID=?";
        try (Connection conn = ormUtils.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, status.toUpperCase());
            if (uniqueCode != null) {
                stmt.setString(2, uniqueCode);
            }
            stmt.setInt(3, buyLeadBean.getId());
            rows = stmt.executeUpdate();
        } catch (Exception e) {
            throw e;
        }
        return rows;
    }

    // TODO addProcuralItem
    public int addProcuralItems(List<ProcuralItemBean> procuralItemBeans) throws Exception {
        String query = " INSERT INTO " + dbName
                + ".PROCURALITEMS (ITEM_NAME, QUANTITY, CUST_PHONE, CUST_EMAIL, CREATED_BY) VALUES (?,?,?,?,?)";
        int userId = 0;
        try (Connection conn = ormUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

        } catch (Exception e) {
            throw e;
        }
        return userId;
    }

    public int updateProcuralItemSupplier(List<ProcuralItemBean> procuralItemBeans, int supplierId) throws Exception {
        int rows = 0;
        String query = "UPDATE " + dbName + ".PROCURALITEMS SET SUPPLIER_ID=? WHERE ID=?";
        for (ProcuralItemBean procuralItemBean : procuralItemBeans) {
            try (Connection conn = ormUtils.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, supplierId);
                stmt.setInt(2, procuralItemBean.getId());
                rows = rows + stmt.executeUpdate();
            } catch (Exception e) {
                throw e;
            }
        }
        return rows;
    }

    public int updateProcuralItemStatus(List<ProcuralItemBean> procuralItemBeans, String status) throws Exception {
        int rows = 0;
        String query = "UPDATE " + dbName + ".PROCURALITEMS SET STATUS=? WHERE ID=?";
        for (ProcuralItemBean procuralItemBean : procuralItemBeans) {
            try (Connection conn = ormUtils.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, status.toUpperCase());
                stmt.setInt(2, procuralItemBean.getId());
                rows = rows + stmt.executeUpdate();
            } catch (Exception e) {
                throw e;
            }
        }
        return rows;
    }

    public int addSupplier(SupplierBean supplierBean) throws Exception {
        String query = " INSERT INTO " + dbName + ".SUPPLIER (SUPPLIER_NAME, CONTACT, CREATED_BY) VALUES (?,?,?)";
        int supplierId = 0;
        try (Connection conn = ormUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, supplierBean.getSupplierName());
            stmt.setString(2, supplierBean.getContact());
            stmt.setInt(3, supplierBean.getUserId());
            stmt.execute();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                supplierId = rs.getInt(1);
            }
        } catch (Exception e) {
            throw e;
        }
        return supplierId;
    }

    public List<BuyLeadBean> getBuyLeads(String status) throws Exception {
        List<BuyLeadBean> buyLeadList = new ArrayList<BuyLeadBean>();
        StringBuilder sql = new StringBuilder(
                "SELECT B.ID,C.ID,C.CUST_NAME,C.CUST_LOCATION,C.CUST_PHONE,C.CUST_EMAIL,B.STATUS,B.FOLLOWUPCOUNT,B.UNIQUECODE from "
                        + dbName + ".BUYLEADS B, CUSTOMER C WHERE B.IS_DELETED='N' AND B.STATUS=?");
        sql.append(" AND B.CUST_ID = C.ID ORDER BY CREATED_ON DESC LIMIT 20");
        try (Connection conn = ormUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            stmt.setString(1, status);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                CustomerBean customer = new CustomerBean(resultSet.getInt(2), resultSet.getString(3),
                        resultSet.getString(4), resultSet.getString(5), resultSet.getString(6), 0);
                BuyLeadBean buylead = new BuyLeadBean(resultSet.getInt(1), customer, resultSet.getString(7),
                        resultSet.getInt(8), 0, null, resultSet.getString(9));
                // TODO Buylead Item
                buyLeadList.add(buylead);
            }
        } catch (Exception e) {
            throw e;
        }
        return buyLeadList;
    }

    public List<SupplierBean> getSuppliers(String supplierName) throws Exception {
        List<SupplierBean> supplierList = new ArrayList<SupplierBean>();
        StringBuilder sql = new StringBuilder(
                "SELECT ID,SUPPLIER_NAME,CONTACT from " + dbName + ".SUPPLIER WHERE IS_DELETED='N'");
        if (supplierName != null && supplierName.trim().length() > 0) {
            sql.append(" AND SUPPLIER_NAME LIKE '%").append(supplierName).append("%'");
        }
        sql.append(" ORDER BY CREATED_ON DESC LIMIT 20");
        try (Connection conn = ormUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                SupplierBean supplier = new SupplierBean(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), 0);
                supplierList.add(supplier);
            }
        } catch (Exception e) {
            throw e;
        }
        return supplierList;
    }

    public List<SupplierBean> getCustomer(String supplierName) throws Exception {
        List<SupplierBean> supplierList = new ArrayList<SupplierBean>();
        StringBuilder sql = new StringBuilder(
                "SELECT ID,SUPPLIER_NAME,CONTACT from " + dbName + ".SUPPLIER WHERE IS_DELETED='N'");
        if (supplierName != null && supplierName.trim().length() > 0) {
            sql.append(" AND SUPPLIER_NAME LIKE '%").append(supplierName).append("%'");
        }
        sql.append(" ORDER BY CREATED_ON DESC LIMIT 20");
        try (Connection conn = ormUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                SupplierBean supplier = new SupplierBean(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), 0);
                supplierList.add(supplier);
            }
        } catch (Exception e) {
            throw e;
        }
        return supplierList;
    }
    
    public UserBean getUser(UserBean user) throws Exception {
        String sql = "SELECT ID,NAME,ROLE from " + dbName
            + ".USER_LOGIN WHERE NAME=? AND PASSWORD=? AND IS_DELETED='N'";
        String password = getEncodedString(user.getPassword());
        try (Connection conn = ormUtils.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
          stmt.setString(1, user.getUsername().toUpperCase());
          stmt.setString(2, password);
          System.out.println("getUser Query ----" + stmt);
          ResultSet resultSet = stmt.executeQuery();
          if (resultSet.first()) {
            user.setId(resultSet.getInt(1));
            user.setUserRole(resultSet.getString(3));
          } else {
              user = null;
          }
        } catch (Exception e) {
            throw e;
        }
        return user;
      }

    public static void main(String[] args) throws Exception {
        testAdd();
    }

    public static void testAdd() throws Exception {
        UserBean userBean = new UserBean(0, "Admin1", "admin@123", null, "ADMIN", 0);
        int userId = new Orm().addUser(userBean);
        System.out.println("User created with id " + userId);
        CustomerBean bean = new CustomerBean(0, "Akash", "Bangalore", "8877887788", "aksadsadfk", userId);
        BuyLeadItemBean itemBean1 = new BuyLeadItemBean(0, 0, "FOOTBALL", 10, "SPORTS", null, null, null, "VAKIL");
        BuyLeadItemBean itemBean2 = new BuyLeadItemBean(0, 0, "TT BAT", 10, "SPORTS", null, null, null, "VAKIL");
        BuyLeadItemBean itemBean3 = new BuyLeadItemBean(0, 0, "BADMINTON RACKET", 10, "SPORTS", null, null, null,
                "VAKIL");
        List<BuyLeadItemBean> buyLeadItems = new ArrayList<>();
        buyLeadItems.add(itemBean1);
        buyLeadItems.add(itemBean2);
        buyLeadItems.add(itemBean3);
        BuyLeadBean buyLeadBean = new BuyLeadBean(0, bean, null, 0, userId, buyLeadItems, null);
        int buyLeadId = new Orm().addBuyLead(buyLeadBean);
        System.out.println("BuyLead created with id " + buyLeadId);
    }

}

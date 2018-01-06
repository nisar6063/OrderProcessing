package com.order.processor.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.order.processor.bean.BuyLeadBean;
import com.order.processor.bean.BuyLeadItemBean;
import com.order.processor.bean.CustomerBean;
import com.order.processor.bean.SupplierBean;
import com.order.processor.bean.UserBean;

@Path("/service")
@Produces("application/json")
@Consumes("application/json")
public class OPService {

    private Orm orm = new Orm();

    @Path("/addBuyLead")
    @POST
    public Response addBuyLead(BuyLeadBean lead) throws Exception {
        int noOfRow = orm.addBuyLead(lead);
        if (noOfRow>0) {
            return Response.status(201).build();
        }
        return Response.status(500).build();
    }
    
    @Path("/addUser")
    @POST
    public Response addUser(UserBean user) throws Exception {
        int noOfRow = orm.addUser(user);
        if (noOfRow>0) {
            return Response.status(201).build();
        }
        return Response.status(500).build();
    }
    
    @Path("/addSupplier")
    @POST
    public Response addSupplier(SupplierBean supplier) throws Exception {
        int noOfRow = orm.addSupplier(supplier);
        if (noOfRow>0) {
            return Response.status(201).build();
        }
        return Response.status(500).build();
    }
    
    @Path("/addBuyLeadItem")
    @POST
    public Response addBuyLeadItem(BuyLeadItemBean buyLead) throws Exception {
        int noOfRow = orm.addBuyLeadItem(buyLead);
        if (noOfRow>0) {
            return Response.status(201).build();
        }
        return Response.status(500).build();
    }
    
    @Path("/updateBuyLead")
    @PUT
    public Response updateBuyLead(BuyLeadBean buyLeadBean) throws Exception {
        int noOfRow = orm.updateBuyLead(buyLeadBean);
        if (noOfRow>0) {
            return Response.status(201).build();
        }
        return Response.status(500).build();
    }
    
    @Path("/updateProcuralItems")
    @PUT
    public Response updateBuyLeadItem(BuyLeadBean buyLeadBean, @QueryParam("status") String status, 
            @QueryParam("supplierId") int supplierId) throws Exception {
        int noOfRow = 0;
        if (status != null) {
            noOfRow = orm.updateProcuralItemSupplier(buyLeadBean, supplierId);
        }
        if (supplierId != 0) {
            noOfRow = orm.updateProcuralItemStatus(procuralItemBeans, status);
        }
        if (noOfRow>0) {
            return Response.status(201).build();
        }
        return Response.status(500).build();
    }
    
    @Path("/getBuyLead")
    @GET
    public Response getBuyLeads(@QueryParam("status") String status, @QueryParam("customerName") String customerName) throws Exception {
        return Response.status(200).entity(orm.getBuyLeads(status)).build();
    }
    
    @Path("/getSuppliers")
    @GET
    public Response getSuppliers(@QueryParam("supplierName") String supplierName) throws Exception {
        return Response.status(200).entity(orm.getSuppliers(supplierName)).build();
    }
    
    @Path("/getProcuralItem")
    @GET
    public Response getProcuralItem(@QueryParam("supplierName") String supplierName) throws Exception {
        return Response.status(200).entity(orm.getSuppliers(supplierName)).build();
    }
    
    @POST
    @Path("/userLogin")
    public Response userLogin(UserBean user) throws Exception {
      return Response.status(200).entity(orm.getUser(user)).build();
    }
    
    @Path("/getBuy")
    @GET
    public String getUser(){
        return "coool!!";
    }
}

//package com.acme.orders.order_processing.model;
//import org.springframework.data.annotation.Id;
//
//@Document(indexName = "orders", createIndex = true)
//public class OrderInTransit {
//    @Id
//    private String id;
//    private String orderTargetIdentifier;
//    private String orderTitle;
//    private String orderDescription;
//    private String orderBillingReference;
//    private String orderInvoiceReference;
//    private boolean approved;
//
//
//    public String getId() {
//        return id;
//    }
//
//    public String getOrderTargetIdentifier() {
//        return orderTargetIdentifier;
//    }
//
//    public String getOrderTitle() {
//        return orderTitle;
//    }
//
//    public String getOrderDescription() {
//        return orderDescription;
//    }
//
//    public String getOrderBillingReference() {
//        return orderBillingReference;
//    }
//
//    public String getOrderInvoiceReference() {
//        return orderInvoiceReference;
//    }
//
//    public void setApproved(boolean approved) {
//        this.approved = approved;
//    }
//
//
//    public OrderInTransit(String id, String orderTargetIdentifier, String orderTitle, String orderDescription, String orderBillingReference, String orderInvoiceReference,boolean approved) {
//        this.id = id;
//        this.orderTargetIdentifier = orderTargetIdentifier;
//        this.orderTitle = orderTitle;
//        this.orderDescription = orderDescription;
//        this.orderBillingReference = orderBillingReference;
//        this.orderInvoiceReference = orderInvoiceReference;
//        this.approved = approved;
//    }
//}

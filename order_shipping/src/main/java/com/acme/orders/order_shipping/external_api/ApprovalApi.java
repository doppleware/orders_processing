package com.acme.orders.order_shipping.external_api;

import com.acme.orders.order_shipping.dto.OrderApprovalRecord;
import org.json.JSONException;

import java.io.IOException;

public interface ApprovalApi {
    OrderApprovalRecord getApproval(String id) throws JSONException, IOException;
}

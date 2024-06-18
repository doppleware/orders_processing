package com.acme.orders.order_processing.external_api;

import com.acme.orders.order_processing.dto.OrderApprovalRecord;
import org.json.JSONException;

import java.io.IOException;

public interface ApprovalApi {
    OrderApprovalRecord getApproval(String id) throws JSONException, IOException;
}

package com.acme.orders.order_processing.external_api;

import com.acme.orders.order_processing.dto.OrderApprovalRecord;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ApprovalApiFacade implements ApprovalApi{

    public static final String ORDERS_APPROVAL_URL = "https://approvals.wiremockapi.cloud/";

    public static final String ORDER_APPROVAL_PATH = "approve/";

    private String makeHttpCall(String url) throws IOException {

        Request request = new Request.Builder().url(url).build();
        OkHttpClient client = new OkHttpClient();
        Response result = client.newCall(request).execute();
        return result.body().string();
    }

    @Override
    public OrderApprovalRecord getApproval(String id) throws JSONException, IOException {

        var orderApproval = makeHttpCall(ORDERS_APPROVAL_URL + ORDER_APPROVAL_PATH +  id);

        try {
            JSONObject obj = new JSONObject(orderApproval);
            OrderApprovalRecord record =parseApprovalRecord(obj);

            return record;
        }
        catch (Exception e){
            //
        }


        return null;

    }

    @NotNull
    private static OrderApprovalRecord parseApprovalRecord(JSONObject jsonObject) throws JSONException {
        boolean approved = jsonObject.getBoolean("approved");
        String orderId = jsonObject.getString("orderId");
        String description = jsonObject.getString("description");
        return new OrderApprovalRecord(approved,orderId,description);
    }



}

package com.example.demo.Utils;
import java.util.UUID;
import com.phonepe.sdk.pg.Env;
import com.phonepe.sdk.pg.payments.v2.StandardCheckoutClient;
import com.phonepe.sdk.pg.payments.v2.models.request.CreateSdkOrderRequest;
import com.phonepe.sdk.pg.payments.v2.models.request.StandardCheckoutPayRequest;
import com.phonepe.sdk.pg.payments.v2.models.response.CreateSdkOrderResponse;
import com.phonepe.sdk.pg.payments.v2.models.response.StandardCheckoutPayResponse;
import com.phonepe.sdk.pg.common.models.MetaInfo;

public class CreateOrderPhonePe {

    public String initializeOrder(){
        String clientId = "<clientId>";
        String clientSecret = "<clientSecret>";
        Integer clientVersion = 1;               //insert your client version here
        Env env = Env.SANDBOX;                   //Change to Env.PRODUCTION when you go live

        StandardCheckoutClient client = StandardCheckoutClient.getInstance(clientId, clientSecret,
                clientVersion, env);


        String merchantOrderId = UUID.randomUUID()
                .toString();
        long amount = 100;
        String redirectUrl = "https://www.merchant.com/redirect";

        CreateSdkOrderRequest createSdkOrderRequest = CreateSdkOrderRequest.StandardCheckoutBuilder()
                .merchantOrderId(merchantOrderId)
                .amount(amount)
                .disablePaymentRetry(true) // If you want to disable payment retries set this parameter to true
                .redirectUrl(redirectUrl)
                .build();

        CreateSdkOrderResponse createSdkOrderResponse = client.createSdkOrder(createSdkOrderRequest);
        String token = createSdkOrderResponse.getToken();
        return  token;
    }







}

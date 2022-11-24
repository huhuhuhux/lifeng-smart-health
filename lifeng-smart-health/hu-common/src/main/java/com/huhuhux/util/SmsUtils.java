package com.huhuhux.util;

import com.aliyun.auth.credentials.Credential;
import com.aliyun.auth.credentials.provider.StaticCredentialProvider;
import com.aliyun.sdk.service.dysmsapi20170525.AsyncClient;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsResponse;
import com.google.gson.Gson;
import darabonba.core.client.ClientOverrideConfiguration;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class SmsUtils {
    /**
     * 预约模板
     */
    public static final String TEMPLATE_CODE_1 = "SMS_154950909";
    /**
     * 预约成功模板
     */
    public static final String TEMPLATE_CODE_2 = "SMS_154950909";

    /**
     * 发送短信
     *
     * @param templateCode
     * @param phoneNumbers
     * @param templateParam
     * @throws ExecutionException
     */
    public static String sendShortMessage(String templateCode, String phoneNumbers, String templateParam) {
        // Configure Credentials authentication information, including ak, secret, token
        StaticCredentialProvider provider = StaticCredentialProvider.create(Credential.builder()
                .accessKeyId("LTAI5tRdz1zTj6kVF2BRbFPS")
                .accessKeySecret("LN4I7NoHfJTKeA1MrV6fR07y9obB6V")
                //.securityToken("<your-token>") // use STS token
                .build());

        // Configure the Client
        AsyncClient client = AsyncClient.builder()
                .region("cn-shenzhen") // Region ID
                //.httpClient(httpClient) // Use the configured HttpClient, otherwise use the default HttpClient (Apache HttpClient)
                .credentialsProvider(provider)
                //.serviceConfiguration(Configuration.create()) // Service-level configuration
                // Client-level configuration rewrite, can set Endpoint, Http request parameters, etc.
                .overrideConfiguration(
                        ClientOverrideConfiguration.create()
                                .setEndpointOverride("dysmsapi.aliyuncs.com")
                        //.setReadTimeout(Duration.ofSeconds(30))
                )
                .build();

        // Parameter settings for API request
        SendSmsRequest sendSmsRequest = SendSmsRequest.builder()
                .signName("阿里云短信测试")
                .templateCode(templateCode)
                .phoneNumbers(phoneNumbers)
                .templateParam("{\"code\":\"" + templateParam + "\"}")
                // Request-level configuration rewrite, can set Http request parameters, etc.
                // .requestConfiguration(RequestConfiguration.create().setHttpHeaders(new HttpHeaders()))
                .build();

        // Asynchronously get the return value of the API request
        CompletableFuture<SendSmsResponse> response = client.sendSms(sendSmsRequest);
        // Synchronously get the return value of the API request
        SendSmsResponse resp = null;
        try {
            resp = response.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        System.out.println(new Gson().toJson(resp));

        // Finally, close the client
        client.close();

        return resp.getBody().getCode();
    }

    public static String getSmsValidKey(String redisKey, String phoneNumber) {
        return redisKey + ":" + phoneNumber;
    }


    public static void main(String[] args) throws Exception {
        SmsUtils.sendShortMessage("SMS_154950909", "18999397596", "123321");
    }
}

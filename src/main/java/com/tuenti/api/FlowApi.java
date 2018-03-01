package com.tuenti.api;

import com.google.gson.Gson;
import com.squareup.okhttp.*;

import java.nio.charset.Charset;
import java.util.Base64;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FlowApi {

    private static final String LAUNCH_JOB_URL = "https://flow.tuenti.io/api/ciprojects/launch";
    private static final String CHARSET = "ISO-8859-1";

    private final ExecutorService executor = Executors.newFixedThreadPool(5);
    private final Gson gson = new Gson();
    private final OkHttpClient okHttpClient = new OkHttpClient();

    public Future<Boolean> launchJob(String username, String password, JobRequest jobRequest) {
            return executor.submit(() -> {
                MediaType mediaType = MediaType.parse("application/json; charset=" + CHARSET);
                String authorization = "Basic " + new String(
                        Base64.getEncoder().encode((username + ":" + password).getBytes(Charset.forName(CHARSET))),
                        Charset.forName(CHARSET));
                RequestBody requestBody = RequestBody.create(mediaType, gson.toJson(jobRequest));
                Request request = new Request.Builder()
                        .url(LAUNCH_JOB_URL)
                        .method("PUT", requestBody)
                        .addHeader("Authorization", authorization)
                        .build();

                Response response = okHttpClient
                        .newCall(request)
                        .execute();

                return response.isSuccessful();
            });
    }
}

package com.example.travelassistant;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.travelassistant.utils.SendRequest; // 导入发送请求的工具类
import com.example.travelassistant.utils.AsyncResponse; // 导入异步响应接口

import java.io.IOException;
import java.io.Serializable;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class HealthConsultationActivity extends AppCompatActivity {

    private EditText etQuestion;
    private Button btnSend;
    private TextView tvResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_consultation);

        etQuestion = findViewById(R.id.et_question);
        btnSend = findViewById(R.id.btn_send);
        tvResponse = findViewById(R.id.tv_response);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String question = etQuestion.getText().toString();
                if (!question.isEmpty()) {
                    sendQuestionToServer(question);
                }
            }
        });
    }

    private void sendQuestionToServer(String question) {
        // 这里假设服务器接口地址和需要的参数
        String url = "https://cloud.baidu.com/doc/WENXINWORKSHOP/s/6ltgkzya5";
        SendRequest.makeAsyncRequest(url, new String[]{question}, new AsyncResponse() {
            @Override
            public void processFinish(String response) {
                // 在UI线程中更新响应结果
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvResponse.setText(response);
                    }
                });
            }

            @Override
            public void processFailure() {
                // 请求失败的处理
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvResponse.setText("请求失败，请稍后再试。");
                    }
                });
            }
        });
    }
}
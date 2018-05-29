package com.riky.neteasecloudmusicapi.service;

import com.alibaba.fastjson.JSONObject;
import com.riky.neteasecloudmusicapi.config.Constant;
import com.riky.neteasecloudmusicapi.util.RestTemplateUtil;
import com.riky.neteasecloudmusicapi.util.CryptoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class LoginService {
    @Autowired
    private RestTemplate restTemplate;

    public ResponseEntity<JSONObject> login(String username, String password, String rememberLogin, String clientToken, Map<String, String> cookies) {

        JSONObject object = new JSONObject();
        object.put("username", username);
        object.put("password", password);
        object.put("rememberLogin", rememberLogin);
        object.put("clientToken", clientToken);
        object.put("csrf_token", cookies.get("__csrf"));

        String[] encrypt = CryptoUtil.Encrypt(object.toJSONString());

        return RestTemplateUtil.post(encrypt[0], encrypt[1], Constant.NETEASE_BASE_URL + "/weapi/login?csrf_token=", cookies, restTemplate);
    }

    public ResponseEntity<JSONObject> cellphoneLogin(String phone, String password, String rememberLogin, Map<String, String> cookies) {
        JSONObject object = new JSONObject();
        object.put("phone", phone);
        object.put("password", password);
        object.put("rememberLogin", rememberLogin);
        object.put("csrf_token", cookies.get("__csrf"));

        String[] encrypt = CryptoUtil.Encrypt(object.toJSONString());

        return RestTemplateUtil.post(encrypt[0], encrypt[1], Constant.NETEASE_BASE_URL + "/weapi/login/cellphone", cookies, restTemplate);

    }

    public ResponseEntity<JSONObject> refreshToken(Map<String, String> cookies) {
        JSONObject object = new JSONObject();
        object.put("csrf_token", cookies.get("__csrf"));
        String[] encrypt = CryptoUtil.Encrypt(object.toJSONString());
        return RestTemplateUtil.post(encrypt[0], encrypt[1], Constant.NETEASE_BASE_URL + "/weapi/login/token/refresh", cookies, restTemplate);
    }
}

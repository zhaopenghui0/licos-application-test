package com.licos.licosapplicationtest.com.licos.config.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

import static java.util.function.UnaryOperator.identity;
import static java.util.stream.Collectors.toMap;

/**
 * @author zph
 * @Description:
 */
@Controller
public class LicosController {
    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    private final String PREFIX = "http://192.168.12.203:8090";
    private final String URI_BASE = "/oauth2/authorization/";
    private final List<String> clients =
            List.of("licos");

    @GetMapping("/oauth2login")
    public String oAuth2LoginPage(Model model) {

        Map<String, String> clientUrls =
                clients.stream().collect(
                        toMap(identity(),
                                client -> PREFIX + URI_BASE + client.toLowerCase()));

        model.addAttribute("clientUrls", clientUrls);
        return "oauth2login";
    }

    @GetMapping("/login_success")
    public String loginSuccess(Model model) {
        model.addAttribute("msg", "登录成功！");
        return "login_success_or_error";
    }

    @GetMapping("/login_failure")
    public String loginFailure(Model model) {
        model.addAttribute("msg", "登录失败！");
        return "login_success_or_error";
    }

    @GetMapping("/login_success1")
    public String loginSuccessful(
            OAuth2AuthenticationToken authenticationToken,
            Model model) {

        OAuth2AuthorizedClient client =
                authorizedClientService.loadAuthorizedClient(
                        authenticationToken.getAuthorizedClientRegistrationId(),
                        authenticationToken.getName()
                );
        OAuth2AccessToken accessToken = client.getAccessToken();
        String tokenValue = accessToken.getTokenValue();
        model.addAttribute("value", tokenValue);
        return "token";
    }


}

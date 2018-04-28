package com.pgy.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pgy.auth.AuthConfig;

/**
 * The root config.
 *
 * @author Felix
 */
@Controller
public class RootConfig {

    private String indexPage;

    @Autowired
    public RootConfig(AuthConfig config) {
        indexPage = config.getLoginSuccessUrl();
    }

    @RequestMapping("/pgy")
    public String forwardToIndex() {
        return "forward:/pgy/index.html";
    }

    @RequestMapping("/")
    public String rootForwardToIndex() {
        return String.format("redirect:%s", indexPage);
    }
}

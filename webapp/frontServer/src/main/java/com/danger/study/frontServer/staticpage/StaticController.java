package com.danger.study.frontServer.staticpage;

import com.danger.study.frontServer.web.Controller;
import com.danger.study.protocol.common.ApiError;
import com.danger.study.protocol.common.ApiResult;
import com.danger.study.tools.common.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by PC-361 on 2017/2/23.
 */
@RestController
@RequestMapping("/static")
public class StaticController {

    @Autowired
    private Controller controller;

    @CrossOrigin(origins = "*")
    @RequestMapping(value = {"/index/{msg:[\\w\\W]+}.html"}, method = RequestMethod.GET)
    public String staticIndex(HttpServletRequest request, HttpServletResponse response, @PathVariable String msg) {
        long timeout = 600;     //十分钟
        long nowTime = System.currentTimeMillis();
        long lm = (nowTime / 1000 - timeout) * 1000;
        long ims = request.getDateHeader("If-Modified-Since");
        if (lm < ims) {
            response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
            return null;
        }
        ApiResult<String> apiResult = controller.getIndexMsg(msg);
        if (apiResult.getCode() == ApiError.SUCCESS.getCode()) {
            response.addDateHeader("Last-Modified", nowTime);
            response.addHeader("Cache-Control", "max-age=" + timeout);
        } else {
            //失败的结果，缓存10秒
            response.addDateHeader("Last-Modified", lm + 10000);
            response.addHeader("Cache-Control", "max-age=" + 10);
        }
        return JsonUtils.getJsonString(apiResult);
    }
}

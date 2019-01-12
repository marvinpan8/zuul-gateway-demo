package com.marvinpan.gateway.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.web.ZuulHandlerMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.marvinpan.gateway.entity.ZuulRouteVO;
import com.marvinpan.gateway.event.RefreshRouteService;
import com.marvinpan.gateway.service.ApiGatewayService;

/**
 * Created by xujingfeng on 2017/4/1.
 */
@RestController
public class DemoController {

    @Autowired
    RefreshRouteService refreshRouteService;
    @Autowired
    ApiGatewayService apiGatewayService;

    @RequestMapping("/refreshRoute")
    public String refreshRoute(){
        refreshRouteService.refreshRoute();
        return "refreshRoute";
    }

    @Autowired
    ZuulHandlerMapping zuulHandlerMapping;

    @RequestMapping("/watchNowRoute")
    public String watchNowRoute(){
        //可以用debug模式看里面具体是什么
        Map<String, Object> handlerMap = zuulHandlerMapping.getHandlerMap();
        return "watchNowRoute";
    }
    
    @RequestMapping("/addRoute")
    public String addRoute(@RequestParam String id, @RequestParam String path, @RequestParam String url){
    	ZuulRouteVO vo = new ZuulRouteVO();
    	vo.setTenantId(id);
    	vo.setPath(path);
    	vo.setUrl(url);
    	int result = apiGatewayService.createOneRoute(vo);
    	if(result == 1) {
    		return "SUCCESS";
    	}
        return "FAILURE";
    }

}

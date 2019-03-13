package com.ldy.controller;

import com.ldy.service.UserRedPacketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/userRedPacket")
public class UserRedPacketController {
    @Autowired
    private UserRedPacketService userRedPacketService = null;

    @RequestMapping(value = "/")
    public String redPacket() {
        return "redPacket";
    }

    @RequestMapping(value = "/grabRedPacket")
    @ResponseBody
    public Map<String, Object> grabRedPacket(Long redPacketId, Long userId) {
        //抢红包
        int result = userRedPacketService.grabRedPacket(redPacketId, userId);
        Map<String, Object> retMap = new HashMap<>();
        boolean flag = result > 0;
        retMap.put("success", flag);
        retMap.put("message", flag ? "抢红包成功" : "抢红包失败");
        return retMap;
    }

    @RequestMapping(value = "/grabRedPacketForVersion")
    @ResponseBody
    public Map<String, Object> grabRedPacketForVersion(Long redPacketId, Long userId) {
        //抢红包
        int result = userRedPacketService.grabRedPacketForVersion(redPacketId, userId);
        Map<String, Object> retMap = new HashMap<>();
        boolean flag = result > 0;
        retMap.put("success", flag);
        retMap.put("message", flag ? "抢红包成功" : "抢红包失败");
        return retMap;
    }

}

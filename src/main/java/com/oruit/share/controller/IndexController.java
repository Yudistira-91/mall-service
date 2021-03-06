package com.oruit.share.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.oruit.share.domain.TbBannerDO;
import com.oruit.share.domain.TbClassifyDO;
import com.oruit.share.service.GoodsService;
import com.oruit.share.service.TaobaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
public class IndexController {

    @Autowired
    private GoodsService goodsService;

    @RequestMapping("/index")
    public String index(HttpServletRequest request,Model model) {
        JSONObject obj = goodsService.getGoodClassify(request);
        if(obj.get("code").equals("1000")){
            List<Map<String,Object>> list = JSONObject.parseObject(obj.get("data").toString(),List.class);

            List<Map<String,Object>> arr = new ArrayList<>();
            if(list.size()> 4){
               for(int i=0;i<4;i++){
                   arr.add(list.get(i));
               }
            }
            model.addAttribute("classifyList",arr);
            model.addAttribute("cid",list.get(0).get("cid"));
        }
        List<TbClassifyDO> tbClassifyDOList = goodsService.queryTbClassify(1);
        model.addAttribute("tbClassifyDOList",tbClassifyDOList);
        HashMap map = new HashMap();
        map.put("bannerType","1");
        List<TbBannerDO> tbBannerList = goodsService.queryTbBanner(map);
        model.addAttribute("tbBannerList",tbBannerList);
        return "dtk_index";
    }


    @RequestMapping("/classify")
    public String classify(HttpServletRequest request, Model model) {
        JSONObject obj = goodsService.getGoodClassify(request);
        if(obj.get("code").equals("1000")){
            List<Map<String,Object>> list = JSONObject.parseObject(obj.get("data").toString(),List.class);
            model.addAttribute("classifyList",list);
            model.addAttribute("cid",list.get(0).get("cid"));
        }
        return "classify";
    }


    @RequestMapping("/changeText")
    @ResponseBody
    public JSONObject changeText(HttpServletRequest request,Model model){
        String cid = request.getParameter("cid");
        JSONObject obj = goodsService.getGoodClassifyText(cid);
        return obj;
    }

    @RequestMapping("/mine")
    public String mine(HttpServletRequest request, Model model) {
        return "mine";
    }
}

package com.zjedu.furns.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zjedu.furns.bean.Furn;
import com.zjedu.furns.bean.Msg;
import com.zjedu.furns.service.FurnService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Controller
public class FurnController {
    @Resource
    private FurnService furnService;

    @PostMapping("/save")
    @ResponseBody
    public Msg save(@Validated @RequestBody Furn furn, Errors errors) {
        HashMap<String, Object> map = new HashMap<>();
        List<FieldError> fieldErrors = errors.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            map.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        if (map.isEmpty()) {
            furnService.save(furn);
            return Msg.success();
        } else {
            return Msg.fail().add("errorMsg", map);
        }
    }

    @RequestMapping(value = "/furns")
    @ResponseBody
    public Msg listFurns() {
        List<Furn> furnList = furnService.findAll();
        Msg msg = Msg.success();
        msg.add("furnList", furnList);
        return msg;
    }

    @PutMapping("/update")
    @ResponseBody
    public Msg update(@RequestBody Furn furn) {
        furnService.update(furn);
        return Msg.success();
    }

    @DeleteMapping("/del/{id}")
    @ResponseBody
    public Msg del(@PathVariable Integer id) {
        furnService.del(id);
        return Msg.success();
    }

    @GetMapping("/find/{id}")
    @ResponseBody
    public Msg findById(@PathVariable Integer id) {
        Furn furn = furnService.findById(id);
        return Msg.success().add("furn", furn);
    }

    @ResponseBody
    @RequestMapping("/furnsByPage")
    public Msg listFurnsByPage(@RequestParam(defaultValue = "1") Integer pageNum,
                               @RequestParam(defaultValue = "5") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Furn> furnList = furnService.findAll();
        PageInfo pageInfo = new PageInfo(furnList, pageSize);
        return Msg.success().add("pageInfo", pageInfo);
    }

    @ResponseBody
    @RequestMapping("/furnsByConditionPage")
    public Msg listFurnsByConditionPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                        @RequestParam(defaultValue = "5") Integer pageSize,
                                        @RequestParam(defaultValue = "") String search) {
        PageHelper.startPage(pageNum, pageSize);
        List<Furn> furnList = furnService.findByCondition(search);
        PageInfo pageInfo = new PageInfo(furnList, pageSize);
        return Msg.success().add("pageInfo", pageInfo);
    }
}

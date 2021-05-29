package com.example.demo.controller;

import com.example.demo.model.Imagen;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("")
public class IndexController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("imgs", jdbcTemplate.query("select * from imgs;", (rs, i) -> {
            return new Imagen(rs.getInt("id"), rs.getString("img"));
        }));
        return "index"
    }

    @PostMapping("/insert")
    @ResponseBody
    public Boolean insert(@RequestBody String img) {
        try {
            return jdbcTemplate.update("insert into imgs (img) values (?);", new Object[] { img }) >= 1;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @GetMapping("/delete/{id}")
    @ResponseBody
    public Boolean delete(@PathVariable Integer id) {
        try {
            return jdbcTemplate.update("delete from imgs where id=?;", new Object[] { id }) >= 1;
        } catch (Exception e) {
            return false;
        }
    }
}

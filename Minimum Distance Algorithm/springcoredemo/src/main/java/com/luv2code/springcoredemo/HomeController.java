package com.luv2code.springcoredemo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller

public class HomeController {
    private final Graph graph = new Graph();

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("vertices", graph.getVertices());
        return "index";
    }

    @PostMapping("/calculate")
    public String calculate(@RequestParam String start, @RequestParam String end, Model model) {
        Map<String, Integer> distances = graph.dijkstra(start);
        int minDistance = distances.get(end);

        model.addAttribute("start", start);
        model.addAttribute("end", end);
        model.addAttribute("minDistance", minDistance);

        return "result";
    }
}

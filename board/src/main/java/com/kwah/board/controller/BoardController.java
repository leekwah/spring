package com.kwah.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/board")
public class BoardController {

    @GetMapping("/write")
    public String boardWriteForm() {
        return "boardWrite";
    }
}

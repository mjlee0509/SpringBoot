package com.example.memberboard.controller;

import com.example.memberboard.dto.MemberDTO;
import com.example.memberboard.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/save")
    public String saveForm() {
        return "memberPages/memberSave";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute MemberDTO memberDTO) throws IOException {
        memberService.save(memberDTO);
        return "redirect:/";

    }
    @PostMapping("/dup-check")
    public ResponseEntity emailCheck(@RequestBody MemberDTO memberDTO) {
        boolean result = memberService.emailCheck(memberDTO.getMemberEmail());
        if (result) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/login")
    public String loginForm(@RequestParam(value = "redirectURI", defaultValue = "/member/main") String redirectURI,
                            Model model) {
        model.addAttribute("redirectURI", redirectURI);
        return "memberPages/memberLogin";
    }

    @PostMapping("/login")
    public String login (@ModelAttribute MemberDTO memberDTO, HttpSession session,
                         @RequestParam("redirectURI") String redirectURI) {
        boolean loginResult = memberService.login(memberDTO);
        if (loginResult) {
            session.setAttribute("loginEmail", memberDTO.getMemberEmail());
            return "redirect:"+redirectURI;
        } else {
            return "memberPages/memberLogin";
        }
    }

    @PostMapping("/login/axios")
    public ResponseEntity memberLogin(@RequestBody MemberDTO memberDTO, HttpSession session) throws Exception {
        memberService.loginAxios(memberDTO);
        session.setAttribute("loginEmail", memberDTO.getMemberEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/main")
    public String memberMain() {
        return "membeRPages/memberMain";
    }






}

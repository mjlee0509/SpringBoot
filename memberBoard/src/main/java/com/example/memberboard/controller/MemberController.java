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
import java.lang.reflect.Member;
import java.util.List;

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
                         @RequestParam("redirectURI") String redirectURI, Model model) {
        boolean loginResult = memberService.login(memberDTO);
        if (loginResult) {
            session.setAttribute("loginEmail", memberDTO.getMemberEmail());
            model.addAttribute("member", memberDTO);
            return "redirect:"+redirectURI;
        } else {
            return "memberPages/memberLogin";
        }
    }

    @PostMapping("/login/axios")
    public ResponseEntity memberLogin(@RequestBody MemberDTO memberDTO, HttpSession session, Model model) throws Exception {
        memberService.loginAxios(memberDTO);
        session.setAttribute("loginEmail", memberDTO.getMemberEmail());
        model.addAttribute("member", memberDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/main")
    public String main(@ModelAttribute MemberDTO memberDTO, HttpSession session, Model model) {
        return "membeRPages/memberMain";
    }

    @GetMapping("/")
    public String findAll(Model model) {
        List<MemberDTO> memberDTOList = memberService.findAll();
        model.addAttribute("memberList", memberDTOList);
        return "memberPages/memberList";
    }

    @GetMapping("/axios/{id}")
    public ResponseEntity detailAxios(@PathVariable Long id) throws Exception{
        MemberDTO memberDTO = memberService.findById(id);
        return new ResponseEntity<>(memberDTO, HttpStatus.OK);
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        memberService.delete(id);
        return "redirect:/member/";
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteAxios(@PathVariable Long id) {
        memberService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }









}

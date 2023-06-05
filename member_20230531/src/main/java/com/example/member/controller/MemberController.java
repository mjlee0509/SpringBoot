package com.example.member.controller;

import com.example.member.dto.MemberDTO;
import com.example.member.entity.MemberEntity;
import com.example.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/main")
    public String index() {
        return "memberPages/memberMain";
    }
    @GetMapping("/save")
    public String saveForm() {
        return "memberPages/memberSave";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute MemberDTO memberDTO) {
        memberService.save(memberDTO);
        return "memberPages/memberLogin";
    }

    @GetMapping("/login")
    public String loginForm(@RequestParam(value = "redirectURI", defaultValue = "/member/main") String redirectURI, Model model) {
        model.addAttribute("redirectURI", redirectURI);
        return "memberPages/memberLogin";
    }

    @PostMapping("/login")
    public String login (@ModelAttribute MemberDTO memberDTO, HttpSession session, @RequestParam("redirectURI") String redirectURI) {
        if (memberService.login(memberDTO)) {
            session.setAttribute("loginEmail", memberDTO.getMemberEmail());
            // return "memberPages/memberMain";
            // 로그인 성공시 사용자가 직전에 요청한 주소로 redirect
            // 인터셉터에 걸리지 않고 처음부터 로그인하는 사용자였다면
            // redirect:/member/main으로 요청되며, memberMain 화면으로 전환
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


    @GetMapping("/")
    public String findAll(Model model) {
        List<MemberDTO> memberDTOList = memberService.findAll();
        model.addAttribute("memberList", memberDTOList);
        return "memberPages/memberList";
    }

//    @GetMapping("/detail/{id}")
//    public String findById(@PathVariable Long id, Model model) {
//        MemberDTO memberDTO = memberService.findById(id);
//        model.addAttribute("member", memberDTO);
//        return "memberPages/memberDetail";
//    }

    @GetMapping("/axios/{id}")
    public ResponseEntity detailAxios(@PathVariable Long id) throws Exception {
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

    @GetMapping("/update")
    public String updateForm(HttpSession session, Model model) {
        String loginEmail = (String)  session.getAttribute("loginEmail"); // String으로 형변환하여 session값 받기
        MemberDTO memberDTO = memberService.findByMemberEmail(loginEmail);
        model.addAttribute("member", memberDTO);
        return "memberPages/memberUpdate";
//        MemberDTO memberDTO = memberService.findById(id);
//        model.addAttribute("member", memberDTO);
//        return "memberPages/memberUpdate";
    }

//    @PostMapping("/update")
//    public String update(@ModelAttribute MemberDTO memberDTO) {
//        memberService.update(memberDTO);
//        return "redirect:/member/";
//    }

    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody MemberDTO memberDTO) {
        memberService.update(memberDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        MemberDTO memberDTO = memberService.findById(id);
        model.addAttribute("member", memberDTO);
        return "memberPages/memberDetail";
    }

    @PostMapping("/dup-check")
    public ResponseEntity emailCheck(@RequestBody MemberDTO memberDTO) {
        // 이 메서드를 쓸 경우, 중복됐을 때 OK 처리가 이루어진다.
        // JS에서 then/catch만 안 헷갈리면 이 메서드를 써도 무방하다
        // memberService.findByMemberEmail(memberDTO.getMemberEmail());
        boolean result = memberService.emailCheck(memberDTO.getMemberEmail());
        if (result) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }








}

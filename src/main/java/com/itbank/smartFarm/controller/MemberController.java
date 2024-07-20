package com.itbank.smartFarm.controller;

import com.itbank.smartFarm.aop.PasswordEncoder;
import com.itbank.smartFarm.vo.MemberVO;
import com.itbank.smartFarm.service.MemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService ms;

    // 로그인 페이지로 이동
    @GetMapping("/login")
    public void login() {}

    // 로그인시 DB에 해당 아이디가 있는지 체크후 아이디를 세션으로 반환
    @PostMapping("/login")
    public String login(MemberVO input, HttpSession session) {
        session.setAttribute("user", ms.login(input));
        return "redirect:/";
    }

    // 로그아웃 버튼 클릭시 세션에서 'user'를 삭제후 홈으로 리다이렉트
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/";
    }

    // 회원가입 페이지로 이동
    @GetMapping("/signUp")
    public void signUp() {}

    // 가입란에 작성한 폼을 insert하여 추가하고 홈으로 리다이렉트
    @PostMapping("/signUp")
    public String signUp(MemberVO input) {
        System.out.println(input);
        ms.signUp(input);
        return "redirect:/";
    }

    // 나의정보 페이지로 이동
    @GetMapping("/myPage")
    public void myPage() {}

    // 회원정보 수정정보 페이지로 전송
    @GetMapping("/update")
    public void update() {}

    // 회원정보 수정요청하여 로그아웃으로 리다이렉트
    @PostMapping("/update")
    public String myPage(MemberVO input, HttpSession session) {
        MemberVO member = (MemberVO) session.getAttribute("user");
        String pw = input.getUserpw();
        // hash처리 pw
        pw = PasswordEncoder.encode(pw);
        if(member.getUserpw().equals(pw)){
            member.setUserpw(input.getNewpw());
            member.setEmail(input.getEmail());
            member.setAddress(input.getAddress());
            member.setPhone(input.getPhone());
            ms.update(member);
        }
        return "redirect:/member/logout";
    }

    // 회원탈퇴하고 홈으로 리다이렉트
    @GetMapping("/delete")
    public String delete(HttpSession session) {
        MemberVO member = (MemberVO) session.getAttribute("user");
        ms.delete(member);
        return "redirect:/";
    }

    // 아이디 찾기 페이지로 전송
    @GetMapping("/findId")
    public void findId() {}

    // 아이디를 찾아서 userid로 데이터를 전송하여 해당페이지에서 userid가 있으면 userid를 alert하도록 설정
    @PostMapping("/findId")
    public ModelAndView findId(MemberVO input) {
        ModelAndView mav = new ModelAndView("member/findId");
        mav.addObject("userid", ms.findId(input));
        return mav;
    }

    // 비밀번호 찾기 페이지로 전송
    @GetMapping("/findPw")
    public void findPw() {}

    // 아이디와 폰번호로 해당 계정을 찾은 뒤 랜덤한 새로운 비밀번호를 발행.
    // 새로 발행된 비밀번호를 유저에게 alert로 전달해주고 새로운 비밀번호를 db에 저장
    @PostMapping("/findPw")
    public ModelAndView findPw(MemberVO input) {
        ModelAndView mav = new ModelAndView("member/findPw");
        String newPw = ms.findPw(input);
        System.out.println("newPw = " + newPw);
        mav.addObject("newPw", newPw);
        return mav;
    }

    // 회원가입시 중복아이디 체크를 위한 비동기통신 메서드
    @PostMapping("/checkUserId")
    public ResponseEntity<Map<String, Boolean>> checkUserId(@RequestBody Map<String, String> request) {
        String userid = request.get("userid");
        boolean exists = ms.isUserIdExists(userid);
        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", exists);
        return ResponseEntity.ok(response);
    }
}

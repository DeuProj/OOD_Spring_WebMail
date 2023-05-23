/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.cse.spring_webmail.control;

import deu.cse.spring_webmail.model.AddrBookAgent;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author CHANG
 */
@Controller
@PropertySource("classpath:/system.properties")
@Slf4j
public class AddressController {
    @Autowired
    private ServletContext ctx;
    @Autowired
    private HttpSession session;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private AddrBookAgent addrBookAgent;
    
        
    @GetMapping("/addrbook")
    public String showAddressBook(Model model) {
        String userId = (String) session.getAttribute("userid");
        addrBookAgent.setUserId(userId);
        model.addAttribute("dataRows", addrBookAgent.findAddrBookByUserId());
        
        return "/addrbook/addrbook";
    }
    
    @GetMapping("/addrbook_popup")
    public String popUpAddressBook(Model model) {
        String userId = (String) session.getAttribute("userid");
        addrBookAgent.setUserId(userId);
        model.addAttribute("dataRows", addrBookAgent.findAddrBookByUserId());
        
        return "/addrbook/addrbook_popup";
    }
    
    @GetMapping("/insert_addrbook")
    public String insertAddressBook() {
        return "/addrbook/insert_addrbook";
    }
    
    @GetMapping("/modify_addrbook")
    public String modifyAddrBook(@RequestParam("email") String email, Model model) {
        model.addAttribute("email", email);
        
        return "/addrbook/modify_addrbook";
    }
    
    @GetMapping("/delete_addrbook.do")
    public String deleteAddrBookDo(
            @RequestParam("email") String email,
            RedirectAttributes attrs,
            Model model) {
        addrBookAgent.setUserId((String) session.getAttribute("userid"));
        
        try {
            addrBookAgent.deleteAddrBookEntry(email);
            attrs.addFlashAttribute("msg", "주소록 삭제를 성공하였습니다.");
        } catch (Exception ex) {
            attrs.addFlashAttribute("msg", "주소록 삭제를 실패하였습니다.");
            log.error("Failed deleteAddrBookDo() : {}", ex);
        }
        
        return "redirect:/addrbook";
    }
    
    @PostMapping("/insert.do")
    public String insertAddressBook(
            @RequestParam String email, 
            @RequestParam String name,                                    
            @RequestParam String phone, 
            RedirectAttributes attrs,
            Model model) {
        
        String userId = (String) session.getAttribute("userid");
        addrBookAgent.setUserId(userId);
        
        try {
            addrBookAgent.createAddrBookEntry(email, name, phone);
            attrs.addFlashAttribute("msg", "주소록 추가를 성공하였습니다.");
        } catch (Exception ex) {
            attrs.addFlashAttribute("msg", "주소록 추가를 실패하였습니다.");
            log.error("Failed insertAddressBook() : {}", ex);
        }
        
        return "redirect:/addrbook";
    }
    
    @PostMapping("/update.do")
    public String updateAddressBook(
            @RequestParam String email,
            @RequestParam String name,                           
            @RequestParam String phone,
            RedirectAttributes attrs) {
        
        String userId = (String) session.getAttribute("userid");
        addrBookAgent.setUserId(userId);
        
        try {
            addrBookAgent.updateAddrBookEntry(email, name, phone);
            attrs.addFlashAttribute("msg", "주소록 수정을 성공하였습니다.");
        } catch (Exception ex) {
            attrs.addFlashAttribute("msg", "주소록 수정을 실패하였습니다.");
            log.error("Failed updateAddressBook() : {}", ex);
        }
        
        return "redirect:/addrbook";
    }
  
    @PostMapping("/use_address.do")
    public String useAddressDo(
            @RequestParam(required = false) String[] selectedAddr, 
            RedirectAttributes attrs, 
            Model model) {
        
        String addressString = selectedAddr != null ? String.join(", ", selectedAddr) : "";
        attrs.addFlashAttribute("addressString", addressString);
        return "redirect:/addrbook_popup";
    }
}

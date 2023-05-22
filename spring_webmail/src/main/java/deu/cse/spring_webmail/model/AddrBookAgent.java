/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.cse.spring_webmail.model;

import deu.cse.spring_webmail.data.AddrBook;
import deu.cse.spring_webmail.data.AddrBookId;
import deu.cse.spring_webmail.data.AddrBookRepository;
import java.util.List;
import java.util.Optional;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author CHANG
 */

@Data
@Slf4j
@Service
@Getter @Setter
public class AddrBookAgent {
    private AddrBookRepository addrBookRepository;
    @NonNull private String userId;
    
    @Autowired
    public AddrBookAgent(AddrBookRepository addrBookRepository) {
        this.addrBookRepository = addrBookRepository;
    }
    
    public List findAddrBookByUserId() {
        log.debug("Load Address Book. id : {}", userId);
        return addrBookRepository.findAddrBookByUserId(userId);
    }
    
    public void createAddrBookEntry(String email, String name, String phone) {
        AddrBook addrBook = new AddrBook();
        
        addrBook.setUserId(userId);
        addrBook.setEmail(email);
        addrBook.setName(name);
        addrBook.setPhone(phone);
        
        addrBookRepository.save(addrBook);
    }
    
    public void updateAddrBookEntry(String email, String name, String phone) {
        AddrBookId id = new AddrBookId(userId, email);
        Optional<AddrBook> optionalAddrBook = addrBookRepository.findById(id);

        if (optionalAddrBook.isPresent()) {
            AddrBook addrBook = optionalAddrBook.get();
            addrBook.setName(name);
            addrBook.setPhone(phone);
            addrBookRepository.save(addrBook);
        }
    }
    
    public void deleteAddrBookEntry(String email) {
        AddrBookId id = new AddrBookId(userId, email);
        addrBookRepository.deleteById(id);
    }
}

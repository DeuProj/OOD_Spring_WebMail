/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package deu.cse.spring_webmail.data;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author CHANG
 */
public interface AddrBookRepository extends JpaRepository<AddrBook, AddrBookId> {
    List<AddrBook> findAddrBookByUserId(@Param("user_id") String userId);
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.cse.spring_webmail.data;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author CHANG
 */
public class AddrBookId implements Serializable {

    private String userId;
    private String email;

    public AddrBookId() {}

    public AddrBookId(String userId, String email) {
        this.userId = userId;
        this.email = email;
    }

    // getters and setters

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AddrBookId)) return false;
        AddrBookId that = (AddrBookId) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, email);
    }
}

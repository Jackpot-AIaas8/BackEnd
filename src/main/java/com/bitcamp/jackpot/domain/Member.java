package com.bitcamp.jackpot.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int memberId;

    @Column(length = 20, nullable = false)
    private String name;

    @Column(length = 30, nullable = false, unique = true)
    private String email;

    private String pwd;

    @Column(length = 20, nullable = false)
    private String phone;

    @Column(length = 10, nullable = false, unique = true)
    private String nickName;

    @Column(length = 100, nullable = false)
    private String address;

    @Builder.Default
    @Column(nullable = false)
    private int isAdmin = 0;

    //비밀번호 인코딩 메서드
    public void encodePassword(String rawPassword, BCryptPasswordEncoder encoder) {
        this.pwd = encoder.encode(rawPassword);
    }

//    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE, orphanRemoval = true)
//    @JsonManagedReference
//    //    @JsonBackReference
//    private List<Board> boards;


    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Review> reviews;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Cart> carts;
}
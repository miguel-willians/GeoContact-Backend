package com.example.geocontact.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.geocontact.entity.Contact;
import com.example.geocontact.entity.User;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findByUserAndNameContainingIgnoreCaseOrCpfContaining(User user, String name, String cpf);
    boolean existsByUserAndCpf(User user, String cpf);
}

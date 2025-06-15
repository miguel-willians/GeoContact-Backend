package com.example.geocontact.repository;

import com.example.geocontact.entity.Contact;
import com.example.geocontact.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ContactRepository extends JpaRepository<Contact, UUID> {

    List<Contact> findByUser(User user);

    List<Contact> findByUserAndNameContainingIgnoreCaseOrUserAndCpfContaining(User user1, String name, User user2, String cpf);

    boolean existsByUserAndCpf(User user, String cpf);
}

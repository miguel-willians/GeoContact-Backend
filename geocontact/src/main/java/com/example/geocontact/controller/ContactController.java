package com.example.geocontact.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.example.geocontact.dto.ContactDTO;
import com.example.geocontact.service.ContactService;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {
    private final ContactService contactService;
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping
    public List<ContactDTO> getAll(@RequestParam(required=false) String filter,
            @AuthenticationPrincipal UserDetails user) {
        Long userId = Long.parseLong(user.getUsername());
        return contactService.listContacts(filter, userId);
    }

    @PostMapping
    public ResponseEntity<ContactDTO> create(@Valid @RequestBody ContactDTO dto,
            @AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.ok(contactService.createContact(dto, Long.parseLong(user.getUsername())));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContactDTO> update(@PathVariable Long id,
            @Valid @RequestBody ContactDTO dto,
            @AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.ok(contactService.updateContact(id, dto, Long.parseLong(user.getUsername())));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id,
            @AuthenticationPrincipal UserDetails user) {
        contactService.deleteContact(id, Long.parseLong(user.getUsername()));
        return ResponseEntity.noContent().build();
    }
}
package com.example.geocontact.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.example.geocontact.dto.ContactDTO;
import com.example.geocontact.service.ContactService;

import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {
    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping
    public List<ContactDTO> getAll(@RequestParam(required = false) String filter,
                                   @AuthenticationPrincipal UserDetails user) {
        UUID userId = UUID.fromString(user.getUsername());
        return contactService.listContacts(filter, userId);
    }

    @PostMapping
    public ResponseEntity<ContactDTO> create(@Valid @RequestBody ContactDTO dto,
                                             @AuthenticationPrincipal UserDetails user) {
        UUID userId = UUID.fromString(user.getUsername());
        return ResponseEntity.ok(contactService.createContact(dto, userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContactDTO> update(@PathVariable UUID id,
                                             @Valid @RequestBody ContactDTO dto,
                                             @AuthenticationPrincipal UserDetails user) {
        UUID userId = UUID.fromString(user.getUsername());
        return ResponseEntity.ok(contactService.updateContact(id, dto, userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id,
                                    @AuthenticationPrincipal UserDetails user) {
        UUID userId = UUID.fromString(user.getUsername());
        contactService.deleteContact(id, userId);
        return ResponseEntity.noContent().build();
    }
}

package com.example.geocontact.service;


import java.util.List;
import java.util.UUID;

import com.example.geocontact.dto.ContactDTO;

public interface ContactService {
    List<ContactDTO> listContacts(String filter, UUID userId);
    ContactDTO createContact(ContactDTO dto, UUID userId);
    ContactDTO updateContact(UUID id, ContactDTO dto, UUID userId);
    void deleteContact(UUID id, UUID userId);
}

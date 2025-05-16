package com.example.geocontact.service;


import java.util.List;

import com.example.geocontact.dto.ContactDTO;

public interface ContactService {
    List<ContactDTO> listContacts(String filter, Long userId);
    ContactDTO createContact(ContactDTO dto, Long userId);
    ContactDTO updateContact(Long id, ContactDTO dto, Long userId);
    void deleteContact(Long id, Long userId);
}

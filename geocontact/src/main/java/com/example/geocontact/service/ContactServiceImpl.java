package com.example.geocontact.service;

import com.example.geocontact.dto.ContactDTO;
import com.example.geocontact.entity.Address;
import com.example.geocontact.entity.Contact;
import com.example.geocontact.entity.User;
import com.example.geocontact.repository.ContactRepository;
import com.example.geocontact.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;
    private final UserRepository userRepository;

    public ContactServiceImpl(ContactRepository contactRepository, UserRepository userRepository) {
        this.contactRepository = contactRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<ContactDTO> listContacts(String filter, UUID userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        List<Contact> contacts;

    if (filter == null || filter.isBlank()) {
        contacts = contactRepository.findByUser(user);
    } else {
        contacts = contactRepository.findByUserAndNameContainingIgnoreCaseOrUserAndCpfContaining(user, filter, user, filter);
    }


        return contacts.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ContactDTO createContact(ContactDTO dto, UUID userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        if (contactRepository.existsByUserAndCpf(user, dto.getCpf())) {
            throw new IllegalArgumentException("CPF já cadastrado para esse usuário");
        }

        Contact contact = fromDTO(dto);
        contact.setUser(user);

        return toDTO(contactRepository.save(contact));
    }

    @Override
    @Transactional
    public ContactDTO updateContact(UUID id, ContactDTO dto, UUID userId) {
        Contact contact = contactRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Contato não encontrado"));

        if (!contact.getUser().getId().equals(userId)) {
            throw new SecurityException("Usuário não autorizado");
        }

        contact.setName(dto.getName());
        contact.setCpf(dto.getCpf());
        contact.setPhone(dto.getPhone());

        Address address = contact.getAddress();
        address.setStreet(dto.getStreet());
        address.setNumber(dto.getNumber());
        address.setComplement(dto.getComplement());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setCep(dto.getCep());
        address.setLatitude(dto.getLatitude());
        address.setLongitude(dto.getLongitude());
        contact.setAddress(address);

        return toDTO(contactRepository.save(contact));
    }

    @Override
    @Transactional
    public void deleteContact(UUID id, UUID userId) {
        Contact contact = contactRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Contato não encontrado"));

        if (!contact.getUser().getId().equals(userId)) {
            throw new SecurityException("Usuário não autorizado");
        }

        contactRepository.delete(contact);
    }

    private ContactDTO toDTO(Contact c) {
        ContactDTO dto = new ContactDTO();
        dto.setId(c.getId());
        dto.setName(c.getName());
        dto.setCpf(c.getCpf());
        dto.setPhone(c.getPhone());

        Address a = c.getAddress();
        dto.setStreet(a.getStreet());
        dto.setNumber(a.getNumber());
        dto.setComplement(a.getComplement());
        dto.setCity(a.getCity());
        dto.setState(a.getState());
        dto.setCep(a.getCep());
        dto.setLatitude(a.getLatitude());
        dto.setLongitude(a.getLongitude());

        return dto;
    }

    private Contact fromDTO(ContactDTO dto) {
        Contact c = new Contact();
        c.setName(dto.getName());
        c.setCpf(dto.getCpf());
        c.setPhone(dto.getPhone());

        Address a = new Address();
        a.setStreet(dto.getStreet());
        a.setNumber(dto.getNumber());
        a.setComplement(dto.getComplement());
        a.setCity(dto.getCity());
        a.setState(dto.getState());
        a.setCep(dto.getCep());
        a.setLatitude(dto.getLatitude());
        a.setLongitude(dto.getLongitude());

        c.setAddress(a);
        return c;
    }
}

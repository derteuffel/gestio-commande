package com.derteuffel.gestioncommande.Services;

import com.derteuffel.gestioncommande.entities.Client;
import com.derteuffel.gestioncommande.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    public Client getOne(Long clientId) {
        return clientRepository.getOne(clientId);
    }

    public <S extends Client> S save(S s) {
        return clientRepository.save(s);
    }

    public long count() {
        return clientRepository.count();
    }

    public void deleteById(Long clientId) {
        clientRepository.deleteById(clientId);
    }

    public Client findByPhone(String phone) {
        return clientRepository.findByPhone(phone);
    }
}

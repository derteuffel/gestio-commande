package com.derteuffel.services;

import com.derteuffel.entities.Client;
import com.derteuffel.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public Client save(Client client){
        return clientRepository.save(client);
    }

    public Client getOne(int clientId){
        return clientRepository.getOne(clientId);
    }

    public Client findByNom(String nomClient){
        return  clientRepository.findByNomClient(nomClient);
    }

    public Client findByEmail(String emailClient){
        return clientRepository.findByEmailClient(emailClient);
    }

    public List<Client> findAll(){

        return clientRepository.findAll();
    }

    public List<Client> findAllByNomClient(String nomClient){
        return clientRepository.findAllByNomClient(nomClient);
    }

    public List<Client> findClient(String nomClient){
        return clientRepository.chercherClientExistant(nomClient);
    }
}

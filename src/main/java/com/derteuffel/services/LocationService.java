package com.derteuffel.services;

import com.derteuffel.entities.Commande;
import com.derteuffel.entities.Location;
import com.derteuffel.repositories.CommandeRepository;
import com.derteuffel.repositories.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LocationService {

    @Autowired
    LocationRepository locationRepository;

    public void save(Location commande){
        locationRepository.save(commande);
    }

    public Location getOne(int commandeId){
        return locationRepository.getOne(commandeId);
    }

    public void valid(int commandeId){
        Location commande= locationRepository.getOne(commandeId);
        locationRepository.save(commande);
    }


    public List<Location> findll(){
        return locationRepository.findAll(Sort.by(Sort.Direction.DESC,"commandeId"));
    }

    public void update(Location commande){
        locationRepository.save(commande);
    }

}

package com.derteuffel.gestioncommande.Services;

import com.derteuffel.gestioncommande.entities.Contract;
import com.derteuffel.gestioncommande.repositories.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContractService {

    @Autowired
    private ContractRepository contractRepository;

    public Page<Contract> findAllByUser_UserId(Long userId, Pageable pageable) {
        return contractRepository.findAllByUser_UserId(userId,pageable);
    }

    public List<Contract> findAll() {
        return contractRepository.findAll();
    }

    public Contract getOne(Long contractId) {
        return contractRepository.getOne(contractId);
    }

    public <S extends Contract> S save(S s) {
        return contractRepository.save(s);
    }

    public long count() {
        return contractRepository.count();
    }

    public void deleteById(Long contractId) {
        contractRepository.deleteById(contractId);
    }
}

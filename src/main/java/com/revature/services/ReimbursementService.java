package com.revature.services;

import com.revature.dtos.RbDTO;
import com.revature.exceptions.InvalidRequestException;
import com.revature.exceptions.ResourceNotFoundException;
import com.revature.exceptions.ResourcePersistenceException;
import com.revature.models.Reimbursement;
import com.revature.models.ReimbursementStatus;
import com.revature.models.ReimbursementType;
import com.revature.repositories.ReimbursementsRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class ReimbursementService {
    private ReimbursementsRepository reimbRepo = new ReimbursementsRepository();

    public List<RbDTO> getAllReimb(){
        List<RbDTO> reimbursements = reimbRepo.getAllReimbursements();
        if (reimbursements.isEmpty()){
            throw new ResourceNotFoundException();
        }
        return reimbursements;
    }
    public List<RbDTO> getReimbByUserId(Integer userId){
        if (userId <= 0){
            throw new InvalidRequestException("THE PROVIDED USER ID CANNOT BE LESS THAN OR EQUAL TO ZERO");
        }
        List<RbDTO> reimb = reimbRepo.getAllReimbSetByAuthorId(userId);
        if (reimb.isEmpty()){
            throw new ResourceNotFoundException();
        }
        return reimb;
    }
    public List<RbDTO> getReimbByType(Integer typeId){
        if (typeId <= 0 || typeId >=5){
            throw new InvalidRequestException("THE PROVIDED USER ID CANNOT BE LESS THAN OR EQUAL TO ZERO");
        }
        List<RbDTO> reimb = reimbRepo.getAllReimbSetByType(typeId);
        if (reimb.isEmpty()){
            throw new ResourceNotFoundException();
        }
        return reimb;
    }

    public List<RbDTO> getReimbByStatus(Integer statusId){
        if (statusId <= 0 || statusId >= 4){
            throw new InvalidRequestException("THE PROVIDED USER ID CANNOT BE LESS THAN OR EQUAL TO ZERO");
        }
        List<RbDTO> reimb = reimbRepo.getAllReimbSetByStatus(statusId);
        if (reimb.isEmpty()){
            throw new ResourceNotFoundException();
        }
        return reimb;
    }

    public void save(Reimbursement reimb){
        if (!isReimbursementValid(reimb)){
            throw new InvalidRequestException("Invalid user field values provided!");
        }
        if(!reimbRepo.addReimbursement(reimb)){
            throw new ResourcePersistenceException("Something went wrong trying to save this reimbursement");
        }
        System.out.println(reimb);
    }
    public void updateEMP(Reimbursement reimb) {
        if (!isReimbursementValid(reimb)){
            throw new InvalidRequestException("Invalid user field values provided!");
        }
        if(!reimbRepo.updateEMP(reimb)){
            throw new ResourcePersistenceException("Something went wrong trying to save this reimbursement");
        }
        System.out.println(reimb);
    }
    public void approve(Integer resolverId, Integer reimbId) {
        if (reimbId <= 0){
            throw new InvalidRequestException("Invalid user field values provided!");
        }
        if(!reimbRepo.updateFIN(resolverId, 2, reimbId)){
            throw new ResourcePersistenceException("Something went wrong trying to approve this reimbursement");
        }
    }
    public void deny(Integer resolverId, Integer reimbId) {
        if (reimbId <= 0){
            throw new InvalidRequestException("Invalid user field values provided!");
        }
        if(!reimbRepo.updateFIN(resolverId, 3, reimbId)){
            throw new ResourcePersistenceException("Something went wrong trying to deny this reimbursement");
        }
    }



    public boolean isReimbursementValid(Reimbursement reimb){
        if (reimb == null) return false;
        if (reimb.getAmount() == null || reimb.getAmount() <= 0 ) return false;
        if (reimb.getDescription() == null || reimb.getDescription().trim().equals("")) return false;
        if (reimb.getAuthorId() <= 0 ) return false;
        if (reimb.getReimbursementType() == null ) return false;
        return true;
    }


}

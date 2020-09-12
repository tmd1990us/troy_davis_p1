package com.revature.services;

import com.revature.exceptions.InvalidRequestException;
import com.revature.exceptions.ResourceNotFoundException;
import com.revature.exceptions.ResourcePersistenceException;
import com.revature.models.Reimbursement;
import com.revature.models.ReimbursementStatus;
import com.revature.models.ReimbursementType;
import com.revature.repositories.ReimbursementsRepository;

import java.util.Optional;
import java.util.Set;

public class ReimbursementService {
    private ReimbursementsRepository reimbRepo = new ReimbursementsRepository();

    public Set<Reimbursement> getAllReimb(){
        Set<Reimbursement> reimbursements = reimbRepo.getAllReimbursements();
        if (reimbursements.isEmpty()){
            throw new ResourceNotFoundException();
        }
        return reimbursements;
    }
    public Set<Reimbursement> getReimbByUserId(Integer userId){
        if (userId <= 0){
            throw new InvalidRequestException("THE PROVIDED USER ID CANNOT BE LESS THAN OR EQUAL TO ZERO");
        }
        Set<Reimbursement> reimb = reimbRepo.getAllReimbSetByAuthorId(userId);
        if (reimb.isEmpty()){
            throw new ResourceNotFoundException();
        }
        return reimb;
    }
    public Set<Reimbursement> getReimbByType(String type){
        if (type == null || type.isEmpty() || type.trim().equals("")){
            throw new InvalidRequestException("THE PROVIDED USER ID CANNOT BE LESS THAN OR EQUAL TO ZERO");
        }
        Set<Reimbursement> reimb = reimbRepo.getAllReimbSetByType(ReimbursementType.getByName(type));
        if (reimb.isEmpty()){
            throw new ResourceNotFoundException();
        }
        return reimb;
    }

    public Set<Reimbursement> getReimbByStatus(String status){
        if (status == null || status.isEmpty() || status.trim().equals("")){
            throw new InvalidRequestException("THE PROVIDED USER ID CANNOT BE LESS THAN OR EQUAL TO ZERO");
        }
        Set<Reimbursement> reimb = reimbRepo.getAllReimbSetByStatus(ReimbursementStatus.getByName(status));
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




    public boolean isReimbursementValid(Reimbursement reimb){
        if (reimb == null) return false;
        if (reimb.getAmount() == null || reimb.getAmount() <= 0 ) return false;
        if (reimb.getDescription() == null || reimb.getDescription().trim().equals("")) return false;
        if (reimb.getAuthorId() <= 0 ) return false;
        if (reimb.getReimbursementType() == null ) return false;
        return true;
    }
}

package com.dws.challenge.service;

import com.dws.challenge.domain.Account;
import com.dws.challenge.exception.DuplicateAccountIdException;
import com.dws.challenge.repository.AccountsRepository;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AccountsService {

  @Getter
  private final AccountsRepository accountsRepository;

  @Autowired
  public AccountsService(AccountsRepository accountsRepository) {
    this.accountsRepository = accountsRepository;
  }

  public void createAccount(Account account) {
    this.accountsRepository.createAccount(account);
  }

  public Account getAccount(String accountId) {
    return this.accountsRepository.getAccount(accountId);
  }

	public void transfer(String accountFromId, String accountToId, BigDecimal amount) {
		if (amount.compareTo(BigDecimal.ZERO) < 0) {
			throw new DuplicateAccountIdException("Transfer amount must be positive.");
		}
		
		//from and To Acc exist else exception message
		Account fromAcc =  this.accountsRepository.getAccount(accountFromId);
		if (fromAcc == null) {
            throw new DuplicateAccountIdException(
                    "From Account id " + accountFromId + " does not exists!");
        }
		Account toAcc =  this.accountsRepository.getAccount(accountToId);
		if (toAcc == null) {
            throw new DuplicateAccountIdException(
                    "To Account id " + accountToId + " does not exists!");
        }
		
		synchronized (fromAcc) {
			synchronized (toAcc) {
				// fromacc balance check else exception message
				if (fromAcc.getBalance().compareTo(amount) < 0) {
					throw new DuplicateAccountIdException(
							"From Account id " + accountFromId + " does not have sufficent balance!");
				}
				// Add in toacc and deduction from fromacc
				fromAcc.setBalance(fromAcc.getBalance().subtract(amount));
				this.accountsRepository.updateAccount(fromAcc);
				toAcc.setBalance(toAcc.getBalance().add(amount));
				this.accountsRepository.updateAccount(toAcc);
			}
		}
		// Send notificatoin
//		 notificationService.notify(accountFromId, "Transferred " + amount + " to account " + accountToId);
//	     notificationService.notify(accountToId, "Received " + amount + " from account " + accountFromId);

		log.info("Balance transfer successfully");
	}
}

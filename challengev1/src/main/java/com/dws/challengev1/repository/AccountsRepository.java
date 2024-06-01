package com.dws.challengev1.repository;

import com.dws.challengev1.domain.Account;
import com.dws.challengev1.exception.DuplicateAccountIdException;

public interface AccountsRepository {

  void createAccount(Account account) throws DuplicateAccountIdException;

  Account getAccount(String accountId);
  
  Account updateAccount(Account account);

  void clearAccounts();
}

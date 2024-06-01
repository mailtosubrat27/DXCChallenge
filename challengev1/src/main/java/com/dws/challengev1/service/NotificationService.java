package com.dws.challengev1.service;

import com.dws.challengev1.domain.Account;

public interface NotificationService {

  void notifyAboutTransfer(Account account, String transferDescription);
}

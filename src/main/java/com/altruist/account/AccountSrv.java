package com.altruist.account;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.UUID;

@Validated
@Service
public class AccountSrv {
    private final AccountRepo accountRepo;

    public AccountSrv(final AccountRepo accountRepo) {
        this.accountRepo = accountRepo;
    }

    public UUID create(final @Valid AccountDto accountDto) {
        Account account = new Account();
        account.username = accountDto.username;
        account.email = accountDto.email;
        account.address_uuid = UUID.fromString(accountDto.address_uuid);

        return accountRepo.save(account)
                .account_uuid;
    }
}

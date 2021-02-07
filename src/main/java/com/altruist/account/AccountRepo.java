package com.altruist.account;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.UUID;

@Repository
@Slf4j
public class AccountRepo {
    private final NamedParameterJdbcOperations jdbcOperations;

    public AccountRepo(final NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    public Account save(final Account account) {
        final BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(account);
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        log.info("Saving account [{}].", account);
        final String sql = "INSERT INTO trade.account (username,email,address_uuid) VALUES (:username, :email, :address_uuid)";
        jdbcOperations.update(sql, params, keyHolder);
        final UUID id;
        final Map<String, Object> keys = keyHolder.getKeys();
        if (null != keys) {
            id = (UUID) keys.get("account_uuid");
            log.info("Inserted account record {}.", id);
            account.account_uuid = id;
        } else {
            log.warn("Insert of account record failed. {}", account);
            throw new RuntimeException("Insert failed for account");
        }
        return account;
    }
}

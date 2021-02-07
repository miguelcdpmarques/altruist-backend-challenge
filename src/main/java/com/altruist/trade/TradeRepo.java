package com.altruist.trade;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
@Slf4j
public class TradeRepo {
    private final NamedParameterJdbcOperations jdbcOperations;

    public TradeRepo(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    public Trade save(Trade trade) {
        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(trade);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        log.info("Saving trade [{}].", trade);
        String sql = "INSERT INTO trade.trade (symbol, side, quantity, price, status, account_uuid) VALUES (:symbol, :side::trade.side, :quantity, :price, :status::trade.status, :account_uuid)";
        jdbcOperations.update(sql, params, keyHolder);
        UUID id;
        Map<String, Object> keys = keyHolder.getKeys();
        if (null != keys) {
            id = (UUID) keys.get("trade_uuid");
            log.info("Inserted trade record {}.", id);
            trade.trade_uuid = id;
        } else {
            log.warn("Insert of trade record failed. {}", trade);
            throw new RuntimeException("Insert failed for trade");
        }
        return trade;
    }

    public List<Trade> fetchAll() {
        log.info("Fetching all trade records.");
        String sql = "SELECT * FROM trade.trade";
        return jdbcOperations.query(sql, new BeanPropertyRowMapper<>(Trade.class));
    }

    public Trade findById(UUID tradeId) {
        log.info("Fetching trade {}.", tradeId);
        String sql = "SELECT * FROM trade.trade WHERE trade_uuid=:trade_uuid";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("trade_uuid", tradeId);
        return jdbcOperations.queryForObject(sql, params, new BeanPropertyRowMapper<>(Trade.class));
    }

    public void updateStatus(Trade trade) {
        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(trade);
        log.info("Updating trade [{}].", trade);
        String sql = "UPDATE trade.trade SET status=:status::trade.status WHERE trade_uuid=:trade_uuid";
        jdbcOperations.update(sql, params);
    }
}

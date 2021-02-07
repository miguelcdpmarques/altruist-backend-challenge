package com.altruist.trade;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
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

    public List<TradeDto> read() {
        String sql = "SELECT * FROM trade.trade";
        return jdbcOperations.query(sql, new RowMapper<TradeDto>() {

            @Override
            public TradeDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                TradeDto tradeDto = new TradeDto();
                tradeDto.setSymbol(rs.getString("symbol"));
                return tradeDto;
            }
        });
    }
}

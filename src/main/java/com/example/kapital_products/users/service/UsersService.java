package com.example.kapital_products.users.service;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class UsersService {
    private final JdbcTemplate jdbcTemplate;
    public byte[] europrotocol_doc(Integer cid, Integer aid ) throws SQLException {

            SimpleJdbcCall jdbcCall =
                    new SimpleJdbcCall(jdbcTemplate)
                            .withFunctionName("europrotocol_doc_pdf");

            SqlParameterSource in = new MapSqlParameterSource()
                    .addValue("cid", cid)
                    .addValue("aid", aid);

            Map<String, Object> out = jdbcCall.execute(in);
            Blob blob =(Blob) out.get("return");


            // Encode the byte array to Base64
            byte[] bytes = blob.getBytes(1, (int) blob.length());
            return  bytes;
    }

}

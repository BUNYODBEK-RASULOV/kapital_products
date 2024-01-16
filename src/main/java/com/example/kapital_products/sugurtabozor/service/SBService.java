package com.example.kapital_products.sugurtabozor.service;


import com.example.kapital_products.commons.entity.UserEntity;
import com.example.kapital_products.commons.payload.request.ContractRequest;
import com.example.kapital_products.commons.payload.response.ApiResponse;
import com.example.kapital_products.sugurtabozor.payload.reponse.SugurtaBozorCreatResponse;
import com.example.kapital_products.sugurtabozor.payload.request.SugurtaBozorRequest;
import com.example.kapital_products.uploadFile.entity.AnketaDocs;
import com.example.kapital_products.uploadFile.repository.AnketaDocsRepo;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.Map;

import static com.example.kapital_products.commons.payload.enams.ResponseEnum.ERROR;
import static com.example.kapital_products.commons.payload.enams.ResponseEnum.SUCCESS;


@Service
@RequiredArgsConstructor
public class SBService {
    private final JdbcTemplate jdbcTemplate;
    private final AnketaDocsRepo anketaDocsRepo;

    public ApiResponse savesDB(SugurtaBozorRequest req){

        String sql = "SELECT a.TB_ANKETA FROM ins_avto a WHERE ROWNUM = 1 AND a.UUID_DOC = ?";

        try {
            Integer ankId = jdbcTemplate.queryForObject(sql, new Object[]{req.getUuid()}, Integer.class);
            var doc=new AnketaDocs();
            doc.setDocNote("sug'urta bozor");
            doc.setDocFn("file.pdf");
            doc.setDoc(downloadAndSavePdf(req.getPdf_url()));
            doc.setAnketaId(ankId);
            doc.setDocDate(LocalDateTime.now());
            doc.setDocMt("application/pdf");
            anketaDocsRepo.save(doc);
        }catch (EmptyResultDataAccessException ee){
            return new ApiResponse(ERROR.getResult(), "No such uuid id was found: "+ee.getMessage());
        }
        catch (FileNotFoundException ee){
            return new ApiResponse(ERROR.getResult(), "Could not download file: "+ee.getMessage());
        }
        catch (Exception e) {
            return new ApiResponse(ERROR.getResult(),"server error: "+ e.getMessage());
        }

        return new ApiResponse(SUCCESS.getResult(), SUCCESS.getText());
    }

    public SugurtaBozorCreatResponse creat(ContractRequest req){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity users=(UserEntity) authentication.getPrincipal();

        try {
            SimpleJdbcCall jdbcCall =
                    new SimpleJdbcCall(jdbcTemplate)
                            .withFunctionName("POST_CARINFO_SBOZOR")
                            .declareParameters(
                                    new SqlOutParameter("errc", Types.INTEGER),
                                    new SqlOutParameter("errm", Types.VARCHAR)
                            );

            SqlParameterSource in = new MapSqlParameterSource()
                    .addValue("ankId", req.getContractId())
                    .addValue("user_id", users.getTbId());
            Map<String, Object> out = jdbcCall.execute(in);
//        String.valueOf(out.get("return"))
            return new SugurtaBozorCreatResponse(Integer.parseInt(String.valueOf(out.get("errc"))),String.valueOf(out.get("errm")), req.getContractId(),String.valueOf(out.get("return")));
        }
        catch (Exception e) { return new SugurtaBozorCreatResponse(ERROR.getResult(),"server error: "+ e.getMessage(),req.getContractId()); }
    }

    public byte[] downloadAndSavePdf(String url) throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(url);

            // Execute the request
            HttpResponse response = httpClient.execute(httpGet);

            // Check if the request was successful (status code 200)
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                // Save the PDF file to a local directory
                byte[] pdfData;
                try (InputStream inputStream = entity.getContent()) {
                    pdfData = inputStream.readAllBytes();
                }
                return pdfData;
        }
            else {
                throw new FileNotFoundException();
            }

    }
    }

}

package com.example.kapital_products.uploadFile.service;


import com.example.kapital_products.commons.payload.response.ApiResponse;
import com.example.kapital_products.uploadFile.entity.AnketaDocs;
import com.example.kapital_products.uploadFile.repository.AnketaDocsRepo;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import static com.example.kapital_products.commons.payload.enams.ResponseEnum.ERROR;
import static com.example.kapital_products.commons.payload.enams.ResponseEnum.SUCCESS;


@Service
@RequiredArgsConstructor
public class FileService {

    private final AnketaDocsRepo anketaDocsRepo;

    public ApiResponse savesDB(MultipartHttpServletRequest request,Integer anketa_id){
        List<AnketaDocs>docs=new ArrayList<>();
        Iterator<String> fileNames = request.getFileNames();

        try {
            while (fileNames.hasNext()) {
                MultipartFile file = request.getFile(fileNames.next());
                if (file ==null)
                    break;
                var doc=new AnketaDocs();
                doc.setDocNote(file.getName());
                doc.setDocFn(file.getOriginalFilename());
                doc.setDoc(file.getBytes());
                doc.setAnketaId(anketa_id);
                doc.setDocDate(LocalDateTime.now());
                doc.setDocMt(file.getContentType());
                docs.add(doc);
            }
            anketaDocsRepo.saveAll(docs);
            }catch (Exception e){ return new ApiResponse(ERROR.getResult(), "NOT SAVED NAME: "+e.getMessage()); }

        return new ApiResponse(SUCCESS.getResult(), SUCCESS.getText());
    }

}

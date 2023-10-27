package com.erser.jpashop.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;
@Service
public class FileService {

    public String uploadFile(String itemImgLocation, String oriImgName, MultipartFile itemImgFile) {

        // 파일의 이름이 cxczas-asdf14354 같은 모양
        // Universal Unique Identifier 중복될 가능성이 거의 없는 파일의 이름으로 중복 문제 해셜
        UUID uuid = UUID.randomUUID();
        // 파일 확장자 추출
        String extension = oriImgName.substring(oriImgName.lastIndexOf(".")); // .png
        String savedFileName = uuid.toString() + extension; // cxczas-asdf14354.png
        String fileUploadPath = itemImgLocation + "/" + savedFileName;

        // 파일 출력 스트림으로 업로드 위치에 저장
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(fileUploadPath);
            fos.write(itemImgFile.getBytes());
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return savedFileName;
    }
}

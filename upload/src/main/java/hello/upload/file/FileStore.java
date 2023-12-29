package hello.upload.file;

import hello.upload.domain.UploadFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileStore {

    @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String filename) {  //"C:/Users/Jung/study/file/1r2f2214.png"
        return fileDir + filename;
    }

    //파일 저장(MultipartFile을 저장하고 UploadFile로 리턴)
    public UploadFile storeFile(MultipartFile multipartFile) throws IOException {

        if (multipartFile.isEmpty()) {
            return null;
        }

        String originalFilename = multipartFile.getOriginalFilename();  //"mvc2.png"
        String storeFileName = createStoreFileName(originalFilename);  //"1r2f2214.png"
        multipartFile.transferTo(new File(getFullPath(storeFileName)));  //MultipartFile 파일 지정된 경로에 저장
        return new UploadFile(originalFilename, storeFileName);
    }

    //이미지 파일 여러개 저장
    public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<UploadFile> storeFileResult = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                storeFileResult.add(storeFile(multipartFile));
            }
        }
        return storeFileResult;
    }

    private String createStoreFileName(String originalFilename) {  //서버 내부에서 관리하는 파일명
        String ext = extractExt(originalFilename);  //"png"
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;  //"1r2f2214.png"
    }

    private String extractExt(String originalFilename) {  //파일 형식 리턴
        int pos = originalFilename.lastIndexOf(".");  //4
        return originalFilename.substring(pos + 1);  //"png"
    }


}

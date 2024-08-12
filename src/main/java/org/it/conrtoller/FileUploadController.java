package org.it.conrtoller;

import org.it.pojo.Result;
import org.it.utils.QnOssUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@RestController
public class FileUploadController {

    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) throws IOException {
        // 把文件的内容存储到本地磁盘上
        String originalFilename = file.getOriginalFilename();
        // 保证文件名唯一，从而防止文件覆盖
        String fileName = UUID.randomUUID().toString()+originalFilename.substring(originalFilename.lastIndexOf("."));
//        file.transferTo(new File("D:\\Software\\project\\java\\springboot\\big-event-img\\"+fileName));
        String url = QnOssUtil.uploadFile(fileName,file.getInputStream());
        return Result.success(url);
    }
}

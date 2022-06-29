package com.heyutang.blog.web.admin;

import com.heyutang.blog.common.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
public class FileUploadController {

    @Value("${filePath}")
    private String filePath;


    @RequestMapping("/toUploadPage")
    private String toUploadPage() {
        return "admin/fileUpload";
    }

    SimpleDateFormat sdf = new SimpleDateFormat("\\yyyy\\MM\\dd\\");

    @PostMapping("/fileUpload")
    @ResponseBody
    public R<String> upload(@RequestPart(value = "file") MultipartFile[] files
            , HttpServletRequest request
    ) {
        String realPath = request.getServletContext().getRealPath("/");
        String format = sdf.format(new Date());
        String resultPath = realPath + filePath + format;

        File fold = new File(resultPath);
        while (!fold.exists()) {
            fold.mkdirs();
        }

        try {
            for (MultipartFile file : files) {
                String old = file.getOriginalFilename();

                String newName = UUID.randomUUID() + old.substring(old.lastIndexOf("."));
                System.out.println(newName);
                file.transferTo(new File(fold, newName));
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new R<String>().error("upload fail");
        }
        return new R<String>().ok("upload success");
    }


}

package com.neo.controller;

import com.neo.util.OSSClientUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author RLY
 */
@Controller
public class UploadController {


    /**
     * 保存到项目根目录下
     */
    @Value("${pic.path}")
    private String UPLOAD_FOLDER;

    //保存到指定目录
    //private static String UPLOAD_FOLDER = "E://temp//";



    @GetMapping("/")
    public String index() {
        return "upload";
    }

    @PostMapping("/upload")
    public String singleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:uploadStatus";
        }

        try {
            String fileName= OSSClientUtil.createFileName("dispute");
            String Sys=OSSClientUtil.uploadImage(file.getInputStream(),fileName);
            if(Sys!=null){
                byte[] bytes = file.getBytes();
                Path path = Paths.get(UPLOAD_FOLDER + file.getOriginalFilename());
                //如果没有files文件夹，则创建
                if (!Files.isWritable(path)) {
                    Files.createDirectories(Paths.get(UPLOAD_FOLDER));
                }
                //文件写入指定路径
                Files.write(path, bytes);

                redirectAttributes.addFlashAttribute("message",
                        "You successfully uploaded '【"+OSSClientUtil.getStartStaff()+"/" + fileName + "】'");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/uploadStatus";
    }

    @GetMapping("/uploadStatus")
    public String uploadStatus() {
        return "uploadStatus";
    }

}
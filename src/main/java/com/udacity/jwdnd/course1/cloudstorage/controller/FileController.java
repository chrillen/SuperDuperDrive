package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.*;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@RequestMapping("/file")
@Controller
public class FileController extends ControllerBase {

    private final FileService fileService;
    private final UserService userService;

    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @GetMapping("/delete")
    public  String deleteFile(Integer fileId, Authentication authentication, Model model) {
        if(fileId == null) {
            model.addAttribute("resultType",ResultTypes.ErrorWithMessage);
            model.addAttribute("errorMessage","fileId is missing in the call.");
            return "result";
        }
        User user = userService.getUser(authentication.getName());
        File file = this.fileService.getFile(fileId,user.getUserId());
        if(file == null) {
            model.addAttribute("resultType",ResultTypes.ErrorWithMessage);
            model.addAttribute("errorMessage","file doesnt exist.");
            return "result";
        }

        this.fileService.deleteFile(fileId,user.getUserId());
        model.addAttribute("files", this.fileService.getFiles(user.getUserId()));
        model.addAttribute("resultType",ResultTypes.Success);
        return "result";
    }

    @GetMapping("/download")
    public ResponseEntity<byte[]> getFile(Integer fileId, Authentication authentication) {
        if(fileId == null) {
            return ResponseEntity.badRequest().build();
        }
        User user = userService.getUser(authentication.getName());
        File file = this.fileService.getFile(fileId,user.getUserId());
        if(file == null) {
            return ResponseEntity.notFound().build();
        }

        var headers = new HttpHeaders();
        headers.add("Content-Type", file.getContentType());
        return ResponseEntity.accepted().headers(headers).body(file.getFileData());
    }

    @PostMapping
    public String addFiles(@RequestParam("fileUpload") MultipartFile fileUpload,
                           Authentication authentication, Model model)  {

         User user = userService.getUser(authentication.getName());
         try {
             File file = new File(null, fileUpload.getOriginalFilename(), fileUpload.getContentType(),
                     String.valueOf(fileUpload.getSize()), user.getUserId(), fileUpload.getBytes());
             int fileSaveResult = this.fileService.addFile(file);
             model.addAttribute("resultType",fileSaveResult);
             model.addAttribute("errorMessage",ResultTypes.getErrorDescription(fileSaveResult));
         } catch (IOException ex) {
             logger.error(ex.getMessage());
             model.addAttribute("resultType",ResultTypes.ErrorWithoutMessage);
             model.addAttribute("errorMessage",ResultTypes.getErrorDescription(ResultTypes.ErrorWithoutMessage));
             return "result";
         }
        model.addAttribute("files", this.fileService.getFiles(user.getUserId()));
        return "result";
    }
}

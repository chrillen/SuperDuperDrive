package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.*;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/credential")
public class CredentialController extends ControllerBase {
    private final CredentialService credentialService;
    private final UserService userService;

    public CredentialController(CredentialService credentialService, UserService userService) {
        this.credentialService = credentialService;
        this.userService = userService;
    }

    @GetMapping("/delete")
    public String deleteCredential(Integer credentialId, Authentication authentication, Model model) {
        if(credentialId == null) {
            model.addAttribute("resultType",ResultTypes.ErrorWithMessage);
            model.addAttribute("errorMessage","noteId is missing in the call.");
            return "result";
        }
        User user = userService.getUser(authentication.getName());
        Credential credential = this.credentialService.getCredential(credentialId, user.getUserId(), false);
        if(credential == null) {
            model.addAttribute("resultType",ResultTypes.ErrorWithMessage);
            model.addAttribute("errorMessage","note doesnt exist.");
            return "result";
        }

        this.credentialService.deleteCredential(credentialId,user.getUserId());
        model.addAttribute("files", this.credentialService.getCredentials(user.getUserId()));
        model.addAttribute("resultType",ResultTypes.Success);
        return "result";
    }

    @GetMapping("/decrypt")
    public ResponseEntity<String> getPassword(Integer credentialId, Authentication authentication) {
        if(credentialId == null) {
            return ResponseEntity.badRequest().build();
        }
        User user = userService.getUser(authentication.getName());
        Credential credential = this.credentialService.getCredential(credentialId, user.getUserId(), true);
        if(credential == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.accepted().body(credential.getPassword());
    }

    @PostMapping()
    public String addUpdateCredential(Authentication authentication,Credential credential, Model model) {
        User user = userService.getUser(authentication.getName());
        credential.setUserId(user.getUserId());

        if(credential.getCredentialId() == null) {
            int credentialSaveResult = this.credentialService.addCredential(credential);
            model.addAttribute("resultType",credentialSaveResult);
            model.addAttribute("errorMessage",ResultTypes.getErrorDescription(credentialSaveResult));
        }
        else {
                Credential existingCredential = this.credentialService.getCredential(credential.getCredentialId(),
                        user.getUserId(), false);

                if(existingCredential == null) {
                    model.addAttribute("resultType", ResultTypes.ErrorWithMessage);
                    model.addAttribute("errorMessage", "credential doesnt exist.");
                    return "result";
                }

                credential.setKey(existingCredential.getKey());
                this.credentialService.updateCredential(credential);
                model.addAttribute("resultType",ResultTypes.Success);
        }
        model.addAttribute("credentials", this.credentialService.getCredentials(user.getUserId()));
        return "result";
    }
}

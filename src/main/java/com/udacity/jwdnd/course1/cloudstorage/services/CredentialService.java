package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.ResultTypes;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {

    private CredentialMapper credentialMapper;
    private final EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper,EncryptionService encryptionService) {
        this.encryptionService = encryptionService;
        this.credentialMapper = credentialMapper;
    }

    public Credential getCredential(int credentialId,int userId,boolean decryptPassword)
    {
        Credential credential = this.credentialMapper.getCredential(credentialId,userId);
        if(decryptPassword)
            credential.setPassword(encryptionService.decryptValue(credential.getPassword(), credential.getKey()));

        return credential;
    }

    public List<Credential> getCredentials(int userId) {
        return this.credentialMapper.getCredentials(userId);
    }

    public void deleteCredential(int credentialId,int userId) {
        this.credentialMapper.deleteCredential(credentialId,userId);
    }

    public void updateCredential(Credential credential) {
        credential.setPassword(encryptionService.encryptValue(credential.getPassword(),credential.getKey()));
        this.credentialMapper.updateCredential(credential);
    }

    public int addCredential(Credential credential) {
        String key = encryptionService.generateKey();
        credential.setKey(key);
        credential.setPassword(encryptionService.encryptValue(credential.getPassword(),key));

        Credential existingCredential = this.credentialMapper.getCredentialFromUserName(credential.getUserName(),credential.getUserId());

        if(existingCredential != null)
            return ResultTypes.ErrorDuplicateExist;

        return (this.credentialMapper.addCredential(credential) == 1) ? ResultTypes.Success : ResultTypes.ErrorWithoutMessage;
    }
}

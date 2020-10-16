package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.ResultTypes;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {

    private FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public File getFile(int fileId,int userId) {
        return this.fileMapper.getFile(fileId,userId);
    }

    public List<File> getFiles(int userId) {
        return this.fileMapper.getFiles(userId);
    }

    public void deleteFile(int fileId,int userId) {
        this.fileMapper.deleteFile(fileId,userId);
    }

    public void updateFile(File file) {
        this.fileMapper.updateFile(file);
    }

    public int addFile(File file) {
        if(file.getFileName().isEmpty())
            return ResultTypes.ErrorEmptyObject;

        File existingFile = this.fileMapper.getFileFromName(file.getFileName(),file.getUserId());

        if(existingFile != null)
            return ResultTypes.ErrorDuplicateExist;

        return (this.fileMapper.addFile(file) == 1) ? ResultTypes.Success : ResultTypes.ErrorWithoutMessage;
    }
}

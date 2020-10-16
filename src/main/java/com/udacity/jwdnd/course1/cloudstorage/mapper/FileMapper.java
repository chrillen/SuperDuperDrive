package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {
    @Select("SELECT * FROM FILES WHERE userid = #{userid}")
    List<File> getFiles(int userid);

    @Select("SELECT * FROM FILES WHERE fileid = #{fileid} and userid=#{userid}")
    File getFile(int fileid,int userid);

    @Select("SELECT * FROM FILES WHERE filename = #{fileName} and userid=#{userid}")
    File getFileFromName(String fileName,int userid);

    @Delete("DELETE FROM FILES WHERE fileid = #{fileid} and userid=#{userid}")
    void deleteFile(int fileid,int userid);

    @Update("UPDATE FILES SET filename=#{fileName},contenttype=#{contentType},filesize=#{fileSize},filedata=#{fileData} WHERE fileid=#{fileId}")
    void updateFile(File file);

    @Insert("INSERT INTO FILES (filename,contenttype,filesize,filedata,userid) VALUES(#{fileName},#{contentType},#{fileSize},#{fileData},#{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int addFile(File file);
}
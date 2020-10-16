package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {
    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userid}")
    List<Credential> getCredentials(int userid);

    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialid} and userid = #{userid}")
    Credential getCredential(int credentialid,int userid);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialid} and userid = #{userid}")
    void deleteCredential(int credentialid,int userid);

    @Select("SELECT * FROM CREDENTIALS WHERE username = #{userName} and userid=#{userid}")
    Credential getCredentialFromUserName(String userName, int userid);

    @Update("UPDATE CREDENTIALS SET url=#{url},username=#{userName},key=#{key},password=#{password} WHERE credentialid=#{credentialId}")
    void updateCredential(Credential credential);

    @Insert("INSERT INTO CREDENTIALS (url,username,key,password,userid) VALUES(#{url},#{userName},#{key},#{password},#{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int addCredential(Credential credential);
}
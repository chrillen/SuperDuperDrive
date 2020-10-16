package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {
    @Select("SELECT * FROM NOTES WHERE userid = #{userid}")
    List<Note> getNotes(int userid);

    @Select("SELECT * FROM NOTES WHERE noteid = #{noteid} and userid = #{userid}")
    Note getNote(int noteid,int userid);

    @Delete("DELETE FROM NOTES WHERE noteid = #{noteid} and userid = #{userid}")
    void deleteNote(int noteid,int userid);

    @Update("UPDATE NOTES SET notetitle=#{noteTitle},noteDescription=#{noteDescription} WHERE noteid=#{noteId}")
    void updateNote(Note note);

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES(#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int addNote(Note note);
}



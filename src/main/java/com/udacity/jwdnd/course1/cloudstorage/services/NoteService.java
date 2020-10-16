package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.ResultTypes;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public Note getNote(int noteId,int userId) {
        return this.noteMapper.getNote(noteId,userId);
    }

    public List<Note> getNotes(int userId) {
        return this.noteMapper.getNotes(userId);
    }

    public void deleteNote(int noteId,int userId) {
        this.noteMapper.deleteNote(noteId,userId);
    }

    public void updateNote(Note note) {
      this.noteMapper.updateNote(note);
    }

    public int addNote(Note note) {
        return this.noteMapper.addNote(note);
    }
}

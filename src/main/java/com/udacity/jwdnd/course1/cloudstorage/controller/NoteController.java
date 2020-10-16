package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.*;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@RequestMapping("/note")
public class NoteController extends ControllerBase {
    private final NoteService noteService;
    private final UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @GetMapping("/delete")
    public  String deleteFile(Integer noteId, Authentication authentication, Model model) {
        if(noteId == null) {
            model.addAttribute("resultType",ResultTypes.ErrorWithMessage);
            model.addAttribute("errorMessage","noteId is missing in the call.");
            return "result";
        }
        User user = userService.getUser(authentication.getName());
        Note note = this.noteService.getNote(noteId, user.getUserId());
        if(note == null) {
            model.addAttribute("resultType",ResultTypes.ErrorWithMessage);
            model.addAttribute("errorMessage","note doesnt exist.");
            return "result";
        }

        this.noteService.deleteNote(noteId,user.getUserId());
        model.addAttribute("files", this.noteService.getNotes(user.getUserId()));
        model.addAttribute("resultType",ResultTypes.Success);
        return "result";
    }

    @PostMapping()
    public String addUpdateNote(Authentication authentication,Note note, Model model) {
        User user = userService.getUser(authentication.getName());
        note.setUserId(user.getUserId());

        if(note.getNoteId() == null) {
            this.noteService.addNote(note);
        }
        else {
            Note existingNote = this.noteService.getNote(note.getNoteId(), user.getUserId());
            if(existingNote == null) {
                model.addAttribute("resultType",ResultTypes.ErrorWithMessage);
                model.addAttribute("errorMessage","note doesnt exist.");
                return "result";
            }
            this.noteService.updateNote(note);
        }

        model.addAttribute("notes", this.noteService.getNotes(user.getUserId()));
        model.addAttribute("resultType",ResultTypes.Success);
        return "result";
    }
}

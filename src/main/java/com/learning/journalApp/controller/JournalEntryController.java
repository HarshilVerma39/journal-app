package com.learning.journalApp.controller;

import com.learning.journalApp.entity.JournalEntry;
import com.learning.journalApp.entity.User;
import com.learning.journalApp.service.JournalEntryService;
import com.learning.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping("{userName}")
    public ResponseEntity<List<JournalEntry>> getAllJournalEntriesOfUser(@PathVariable String userName){
        User user = userService.findByUserName(userName);
        List<JournalEntry> allEntry = user.getJournalEntries();
        if(allEntry != null && !allEntry.isEmpty()){
            return new ResponseEntity<>(allEntry,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable String myId){
        JournalEntry entry = journalEntryService.findById(myId);
        return new ResponseEntity<>(entry,HttpStatus.OK);
    }

    @PostMapping("/{userName}")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry, @PathVariable String userName){
        journalEntryService.saveEntry(myEntry, userName);
        return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
    }

    @DeleteMapping("id/{myId}/{userName}")
    public ResponseEntity<JournalEntry> deleteJournalEntryById(@PathVariable String myId, @PathVariable String userName){

        journalEntryService.deleteById(myId,userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("id/{id}/{userName}")
    public ResponseEntity<JournalEntry> updateJournalEntries(
            @PathVariable String id,
            @RequestBody JournalEntry entry,
            @PathVariable String userName){
        return new ResponseEntity<>(journalEntryService.updateJournalEntries(id,entry,userName),HttpStatus.OK);
    }

/*    @PatchMapping("id/{id}")
    public JournalEntry updateJournalEntry(@PathVariable String id, @RequestBody JournalEntry entry){
        return journalEntryService.updateJournalEntry(id,entry);
    }*/
}

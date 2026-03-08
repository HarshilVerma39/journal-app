package com.learning.journalApp.service;

import com.learning.journalApp.entity.JournalEntry;
import com.learning.journalApp.entity.User;
import com.learning.journalApp.exception.BadRequestException;
import com.learning.journalApp.exception.ResourceNotFoundException;
import com.learning.journalApp.repository.JournalEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName){
        if(journalEntry == null){
            throw new BadRequestException("Journal entry payload cannot be null");
        }
        User user = userService.findByUserName(userName);
        journalEntry.setDate(LocalDate.now());
        JournalEntry saved = journalEntryRepository.save(journalEntry);
        user.getJournalEntries().add(saved);
        userService.createUser(user);
    }

    public void saveEntry(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }

    public JournalEntry findById(String id){
        if(id == null || id.isBlank()){
            throw new BadRequestException("Journal entry id cannot be blank");
        }
        Optional<JournalEntry> result = journalEntryRepository.findById(id);
        return result.orElseThrow(() -> new ResourceNotFoundException("Journal entry not found for id: " + id));
    }

    public void deleteById(String id, String userName){
        User user = userService.findByUserName(userName);
        findById(id);
        user.getJournalEntries().removeIf(x -> x.getId().equals(id));
        userService.createUser(user);
        journalEntryRepository.deleteById(id);
    }

    public JournalEntry updateJournalEntries(String id, JournalEntry newEntry, String userName){
        if(newEntry == null){
            throw new BadRequestException("Journal entry payload cannot be null");
        }
        userService.findByUserName(userName);
        JournalEntry oldEntry = findById(id);
        oldEntry.setContent((newEntry.getContent() != null && !newEntry.getContent().isEmpty()) ?
                newEntry.getContent() : oldEntry.getContent());
        oldEntry.setTitle((newEntry.getTitle() != null && !newEntry.getTitle().isEmpty()) ?
                newEntry.getTitle() : oldEntry.getTitle());
        return journalEntryRepository.save(oldEntry);
    }

    public JournalEntry updateJournalEntry(String id, JournalEntry journalEntry){
        /*
         * let's keep this aside for a while
         * we have to use JsonMergePatch here,
         * we gotta learn and implement that part
         */
        return null;
    }
}

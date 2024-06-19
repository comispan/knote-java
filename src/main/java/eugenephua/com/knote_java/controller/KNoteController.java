package eugenephua.com.knote_java.controller;

import eugenephua.com.knote_java.service.NotesService;
import java.io.IOException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Controller
public class KNoteController {

  @Autowired
  private NotesService notesService;

  @GetMapping("/")
  public String index(Model model) {
    notesService.getAllNotes(model);
    return "index";
  }

  @PostMapping("/note")
  public String saveNotes(@RequestParam(name = "image", required = false) MultipartFile file,
      @RequestParam String description,
      @RequestParam(required = false) String publish,
      @RequestParam(required = false) String upload,
      Model model) throws IOException {

    if (publish != null && publish.equals("Publish")) {
      notesService.saveNote(description, model);
      notesService.getAllNotes(model);
      return "redirect:/";
    }

    if (upload != null && upload.equals("Upload")) {
      if (file != null && file.getOriginalFilename() != null
          && !file.getOriginalFilename().isEmpty()) {
        notesService.uploadImage(file, description, model);
      }
      notesService.getAllNotes(model);
      return "index";
    }

    // After save fetch all notes again
    return "index";
  }

}

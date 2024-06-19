package eugenephua.com.knote_java.service;

import eugenephua.com.knote_java.config.KnoteConfig;
import eugenephua.com.knote_java.dto.Note;
import eugenephua.com.knote_java.repository.NotesRepository;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class NotesService {

  @Autowired
  NotesRepository notesRepository;

  @Autowired
  KnoteConfig properties;

  private Parser parser = Parser.builder().build();
  private HtmlRenderer renderer = HtmlRenderer.builder().build();

  public void getAllNotes(Model model) {
    List<Note> notes = notesRepository.findAll();
    Collections.reverse(notes);
    log.info("Retrieved and sorted notes");
    model.addAttribute("notes", notes);
  }

  public void saveNote(String description, Model model) {
    if (description != null && !description.trim().isEmpty()) {
      //You need to translate markup to HTML
      Node document = parser.parse(description.trim());
      String html = renderer.render(document);
      notesRepository.save(new Note(null, html));
      log.info("Saved note");

      //After publish you need to clean up the textarea
      model.addAttribute("description", "");
    }
  }

  public void uploadImage(MultipartFile file, String description, Model model) throws IOException {
    File uploadsDir = new File(properties.getUploadDir());
    if (!uploadsDir.exists()) {
      uploadsDir.mkdir();
    }
    String fileId = UUID.randomUUID().toString() + "."
        + file.getOriginalFilename().split("\\.")[1];
    file.transferTo(new File(properties.getUploadDir() + fileId));
    model.addAttribute("description", description + " ![](/uploads/" + fileId + ")");
  }
}

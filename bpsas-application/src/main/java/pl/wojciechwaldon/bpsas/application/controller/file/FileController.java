package pl.wojciechwaldon.bpsas.application.controller.file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import pl.wojciechwaldon.bpsas.domain.file.StorageService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/files")
public class FileController {

    @Autowired
    private StorageService storageService;

    List<String> files = new ArrayList<String>();

    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public String handleFileUpload(@RequestParam("file") MultipartFile file) throws Exception {
        try {
            storageService.store(file);
            files.add(file.getOriginalFilename());
        } catch (Exception e) {
            throw new Exception();
        }
        String savedFileName = file.getOriginalFilename();
        return savedFileName;
    }

    @GetMapping("/getallfiles")
    @ResponseBody
    public List<String> getListFiles() {
        return files.stream()
                .map(fileName -> MvcUriComponentsBuilder
                        .fromMethodName(FileController.class, "getFile", fileName).build().toString())
                .collect(Collectors.toList());
    }

    @GetMapping("/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = storageService.loadFile(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }
}

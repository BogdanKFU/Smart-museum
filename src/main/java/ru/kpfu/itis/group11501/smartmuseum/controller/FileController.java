package ru.kpfu.itis.group11501.smartmuseum.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.kpfu.itis.group11501.smartmuseum.util.FileUploader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Created by volkov on 13.05.2018.
 */
@Controller
public class FileController {

    private FileUploader fileUploader;

    @Autowired
    public FileController(FileUploader fileUploader) {
        this.fileUploader = fileUploader;
    }

    @ResponseBody
    @RequestMapping(value = "/image/{name}.{extension}", method = RequestMethod.GET)
    public byte[] getImage(@PathVariable(value = "name") String name, @PathVariable(value = "extension") String extension)  {
        File image = new File(fileUploader.getImagePath() + name + "." + extension);
        try {
            return Files.readAllBytes(image.toPath());
        } catch (IOException e) {
            return null;
        }
    }
}

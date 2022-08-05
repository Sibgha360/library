package com.example.controller;

import com.example.helper.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.service.CSVService;
import com.example.helper.CSVHelper;
import com.example.helper.message.ResponseMessage;

@Controller
@RequestMapping("/api/csv")
public class DataImportController {

    @Autowired
    CSVService fileService;

    @PostMapping("/upload_books")
    public ResponseEntity<ResponseMessage> uploadFileBooks(@RequestParam("file") MultipartFile file) throws Exception {
        validateFileFormat(file);

        fileService.saveBooks(file);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Uploaded the file successfully: " + file.getOriginalFilename()));
    }


    @PostMapping("/upload_users")
    public ResponseEntity<ResponseMessage> uploadFileUsers(@RequestParam("file") MultipartFile file) throws Exception {
        validateFileFormat(file);

        fileService.saveUsers(file);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Uploaded the file successfully: " + file.getOriginalFilename()));

    }

    @PostMapping("/upload_borrowed")
    public ResponseEntity<ResponseMessage> uploadFileBorrowed(@RequestParam("file") MultipartFile file) throws Exception {
        validateFileFormat(file);

        fileService.saveBorrowed(file);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Uploaded the file successfully: " + file.getOriginalFilename()));
    }

    private void validateFileFormat(MultipartFile file) throws BadRequestException {
        if (!CSVHelper.hasCSVFormat(file)) {
            throw new BadRequestException("Please upload a csv file!");
        }
    }
}

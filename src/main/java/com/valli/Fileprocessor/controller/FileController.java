package com.valli.Fileprocessor.controller;


import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.valli.Fileprocessor.exception.ResourceNotFoundException;
import com.valli.Fileprocessor.model.Employee;
import net.minidev.json.JSONArray;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
public class FileController {
    private static final Logger logger = LogManager.getLogger(FileController.class);

    @RequestMapping("/{getFiles}/**")
    public List<String> getAllFiles(@PathVariable String getFiles, HttpServletRequest request) {

        String urlTail = new AntPathMatcher()
                .extractPathWithinPattern("/{getFiles}/**", request.getRequestURI());
        Path path = Paths.get("/" + urlTail);
        if(Files.exists(path)==false){
            logger.debug("Folder is not available");
            throw new ResourceNotFoundException("Folder is not available");
        }
        List<String> resultFiles = new ArrayList<>();
        listFiles(resultFiles, path.toString());
        Collections.sort(resultFiles);
        logger.info("Info: This is rest controoler");
        logger.debug("Debug: This is rest controoler");
        return resultFiles;
    }

    @RequestMapping(path = "/createjsonfile/{employeedtails}", method = RequestMethod.GET)
    @SuppressWarnings("deprecation")
    public String getSingleOrder(@PathVariable String employeedtails) {

        JSONArray jsArray = new JSONArray();
        String[] result = employeedtails.split(",");
        Employee employee = new Employee();
        if(result.length<4)
            throw new ResourceNotFoundException("Insufficient information, should be 4 argument");

        employee.setId(Integer.parseInt(result[0]));
        employee.setFirstName(result[1]);
        employee.setLastName(result[2]);
        employee.setEmail(result[3]);


        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(new File("src/main/resources/Employee.json"), employee);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            logger.debug("JSon Error "+e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            logger.debug("JSon Error "+e.getMessage());
            e.printStackTrace();
        }
        return employee.toString();
    }


    private void listFiles(List<String> filesList, String path) {
        File folder = new File(path);
        File[] files = folder.listFiles();

        for (File file : files) {
            if (file.getName().endsWith(".json")) {
                filesList.add(file.getPath());
            } else if (file.isDirectory()) {
                listFiles(filesList, file.getAbsolutePath());
            }
        }

    }
}

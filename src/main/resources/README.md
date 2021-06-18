# Spring REST Project
This is demo project for Spring REST

## Description
This demo project do the following bussiness functionalities 

 1. GetAllJSONFiles: 
    fectch all json file names provided the path in URL
     Ex: http://localhost:8080/<<folder path>>
         This application reads all the folders and subfolders of the given folder path 
            and displays all json file names in sorting order
    
          URL:  http://localhost:8080/getFiles/Users/shaikkhvalli/Downloads/TestFolder
          Result: ["json1.json","json2.json","json3.json","json4.json","json5.json","json6.json"]


2.Create JSON File:
    Creates the json file with the details given in the path
    http://localhost:8080/createjsonfile/200,shaikkh,valli,mai@mail.com
    by reading the details of the url through request Employee object will set all the details and JSON file will created
    Employee [id=200, firstName=shaikkh, lastName=valli, email=mai@mail.com]
            
List of URLs:
    /createjsonfile/{employeedtails}
    /{getFiles}/**

## Libraries used
- Spring Boot
- Spring REST Controller
- Development Tools
- Log4J2


## Compilation Command
- `mvn clean install` - Plain maven clean and install

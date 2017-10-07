package com.uic.ahegde5.instrumentation.utility;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Adarsh Hegde
 * This class lists all java files in a directory
 *
 */

public class JavaFileListing {

    static final String FILE_TYPE = ".java";

    public static List<String> listFilesInDirectory(String dirPath) {
        File directory = new File(dirPath);
        List<String> listOfFiles = new ArrayList<>();

        File[] listOfFilesInDirectory = directory.listFiles();
        for(File file : listOfFilesInDirectory){

            if(file.isDirectory()){

                List<String> fileList = listFilesInDirectory(file.toPath().toString());
                if(null != fileList && !fileList.isEmpty())
                    listOfFiles.addAll(fileList);

            } else if(file.isFile()){
                if(file.getName().endsWith(FILE_TYPE)){
                    listOfFiles.add(file.getPath());
                }

            }
        }
        return listOfFiles;
    }

    /*public static void main(String[] args){
        FileListing fileListing = new JavaFileListing();
        List<String> fileList = fileListing.listFilesInDirectory("D:\\IntellijWorkspace\\OOLE\\adarsh_hegde_hw1\\src");

        System.out.println("Number of files : " + fileList.size());
        for(String fileName : fileList){
            System.out.println(fileName);
        }

    }*/
}

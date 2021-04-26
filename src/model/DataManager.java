package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;

public class DataManager {

    File partsCsv = new File("/data/products.csv");
    File productsCsv = new File("/data/parts.csv");

    private static List files = new List();

    if(partsCsv.isFile()){
        BufferedReader csvReadParts = new BufferedReader(new FileReader(partsCsv));
    }
   
    if(productsCsv.isFile()){
        BufferedReader csvReadProducts = new BufferedReader(new FileReader(productsCsv));
    }
    
}

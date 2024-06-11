/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package dao;

import entities.Folder;
import java.util.ArrayList;

public interface FolderDAO {
    int createFolder(String foldername);
    ArrayList<Folder> listAllFolders();
}

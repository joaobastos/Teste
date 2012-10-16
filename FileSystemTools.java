package ASP;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author joaobastos
 */
public class FileSystemTools {

    /**
     *  Checks if a specific File object exists in the user's current directory, or if a path is passed as argument, 
     * in the path's end directory. The argument fileName can be either a path or a filename. 
     * This method always return a boolean value. If the file is found, returns True, if the file doesn't exist, returns
     * False.
     * 
     * @param fileName a path or a filename for the file to be checked.
     * 
     * @return a boolean value, true if the file exists, false otherwise.
     */
    public static boolean FileExists(String fileName) throws FileNotFoundException {
        File fin=new File (fileName);
        return fin.exists();
    }

    /**
     * Checks if any file of the fileType exists in the current directory, if they do, the method returns them.
     * The parameter fileType is of type String, but it only accepts Strings started by ".".
     * This method returns an array of String with the names of the files found of the required fileType.
     * 
     * @param fileType a String started by "." and that represents a file extension
     * @return an array of Strings representing the names of the files found
     * 
     */
    public static String[] retrieveFiles(final String fileType) throws FileNotFoundException {
        File fin = new File(System.getProperty("user.dir"));
        String[] lista = fin.list(new FilenameFilter() {

            @Override
            public boolean accept(File dir, String name) {
                if (name.endsWith(fileType)) {
                    return true;
                }
                return false;
            }
        });
        
        
        return lista;
        
    }

    /**
     * Copy the information stored in file srFile to dtFile file.
     * Returns a boolean variable that is true if the opeartion was sucessful, or false in case it was not
     * 
     * @param srFile source File
     * @param dtFile destination File
     * 
     */
    public static void copyfile(File source, File destination) throws IOException {


      
        InputStream in = new FileInputStream(source);
        OutputStream out = new FileOutputStream(destination);

        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }

        in.close();
        out.close();
        System.out.println("Ficheiro "+source.getName() +" copiado.");

    }

    /**
     * Deletes a file from the FileSystem. The parameter fileName represents the file to be deleted
     * This method return a boolean variable that represents the sucess of the operation, true = suceess, false = failure
     * @param fileName path of file to be deleted
     * @return sucess of operation, in a boolean value
     * @throws FileNotFoundException if the file does not exist or is invalid
     */
// Deletes all files and subdirectories under dir.
// Returns true if all deletions were successful.
// If a deletion fails, the method stops attempting to delete and returns false.
public static boolean deleteFile(File file) {
    if (file.isDirectory()) {
        String[] children = file.list();
        for (int i=0; i<children.length; i++) {
            boolean success = deleteFile(new File(file, children[i]));
            if (!success) {
                return false;
            }
        }
    }

    // The directory is now empty so delete it
    return file.delete();
}

    /**
     * Prints all the files inside a specific directory, represented by the parameter dirName
     * @param dirName target directory
     * @throws IOException in case there is a error in the I/O process
     * 
     */
    public static void printAllFiles(String dirName) throws IOException {
        File file = new File(dirName);
        if (file.exists()) {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String strLine;

            while ((strLine = br.readLine()) != null) {
                System.out.println(strLine);
            }
            br.close();
        } else {
            throw new FileNotFoundException("printAllFiles: Directory not found " + dirName);
        }
    }
}

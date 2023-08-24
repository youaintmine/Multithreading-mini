package PatternFinder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class PatternFinder {
    public List<Integer> findFile(File file, String pattern){
        //TODO
        List<Integer> lineNum = new ArrayList<>();

        //
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            int lineNo = 1;
            String line;
            while ((line=bufferedReader.readLine()) != null){
                if(line.contains(pattern)){
                    lineNum.add(lineNo);
                }
                lineNo++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lineNum;
    }
}

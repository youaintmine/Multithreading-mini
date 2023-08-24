import PatternFinder.PatternFinder;
import java.io.File;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class App {
    public static void main(String[] args) throws Exception {

        String filePath = Paths.get("./Pattern-Search/src/sample").toRealPath().toString();
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("File or directory does not exist.");
            return;
        }
        File[] files = file.listFiles();

        //Creating the file pattern
        PatternFinder finder = new PatternFinder();
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        Map<String, Object> ansMap = new HashMap<String, Object>();

        String pattern = "public";
        for(File fl : files) {
            //We'll be searching for the files here
            Future<List<Integer>> future =
                    executorService.submit(
                            new Callable<List<Integer>>() {
                                @Override
                                public List<Integer> call() throws Exception {
                                    List<Integer> lineNumbers = finder.findFile(fl, pattern);
                                    return lineNumbers;
                                }
                            });
            ansMap.put(fl.getName(), future);
        }

        waitForAll(ansMap);
        //Wait for All is basically synchronizing all answers
        for( Map.Entry<String,Object> entry : ansMap.entrySet()){
            System.out.println(
                    pattern + " found at - " + entry.getValue() +
                            " in file " + entry.getKey());

        }
    }

    private static void waitForAll(Map<String, Object> ansMap) throws Exception {
        Set<String> keys = ansMap.keySet();

        for(String key : keys){
            Future<List<Integer>> future = (Future<List<Integer>>) ansMap.get(key);

            while (!future.isDone()){
                Thread.yield();
            }

            ansMap.put(key, future.get());
        }
    }
}

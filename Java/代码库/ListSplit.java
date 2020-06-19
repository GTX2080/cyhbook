import java.util.ArrayList;
import java.util.List;

/**
 * 拆分list
 * @param <T>
 */
public class ListSplit<T> {
    public List<List<T>> split(List<T> sList, int num) {
        List<List<T>> eList = new ArrayList<List<T>>();
        List<T> gList;
        int size = (sList.size()) / num;
        int size2 = (sList.size()) % num;
        int j = 0;
        int xx = 0;
        for (int i = 0; i < num; i++) {
            gList = new ArrayList<T>();

            for (j = xx; j < (size + xx); j++) {
                gList.add(sList.get(j));
            }
            xx = j;
            eList.add(gList);
        }
        if (size2 != 0) {
            gList = new ArrayList<T>();
            for (int y = 1; y < size2 + 1; y++) {
                gList.add(sList.get(sList.size() - y));
            }
            eList.add(gList);
        }
        return eList;
    }


}
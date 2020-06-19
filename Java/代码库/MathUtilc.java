
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MathUtilc {
    public MathUtilc() {
    }

    static int randomInteger(int start, int end) {
        int t = ThreadLocalRandom.current().nextInt(start, end);
        return t;
    }

    /**
     * 状态转移随机算法
     * 取随机数
     * @param start 开始
     * @param end   结束
     * @param count 截取多少个
     * @return
     */
    private List statusRandom(int start, int end, int count) {
        //状态转移随机算法
        List<Integer> l = new ArrayList<Integer>()  ;
        int[] status = new int[end + 1];
        for (int i = 0; i < count; i++) {
            int random = randomInteger(start, end);
            if (status[random] == 0) {
                l.add(random);
                status[random] = random == end ? start : (random + 1); // 不可能有在start之前的数字
            } else {
                // 状态转移
                int index = random;
                do {
                    index = status[index];
                } while (status[index] != 0);
                l.add(index);
                status[index] = index == end ? start : (index + 1); // 不可能有在start之前的数字
            }
        }

//        System.out.println(l);
        return l;
    }

    /**
     * 迭代Floyd随机算法：
     */
    private List<Integer> iterationFloyd(int start, int end, int count) {
        //System.out.println("迭代Floyd随机算法：");
        List<Integer> list = new ArrayList<>();
        for (int i = end - count + 1; i < end; i++) {
            int random = randomInteger(start, i);
            if (list.contains(random)) {
                list.add(i);
            } else {
                list.add(random);
            }
        }
        return list;
    }
}

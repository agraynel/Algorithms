import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);

        if (k == 0) return;
        double size = 1.0;
        RandomizedQueue<String> queue = new RandomizedQueue<>();

        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            if (queue.size() < k) {
                queue.enqueue(s);
            } else {
                //http://www.1point3acres.com/bbs/forum.php?mod=viewthread&tid=116090&extra=page%3D1%26filter%3Dsortid%26sortid%3D309%26sortid%3D309
                double rand = StdRandom.uniform();
                if (rand > (size - k) / size) {
                    queue.dequeue();
                    queue.enqueue(s);
                }
            }
            size++;
        }

        while (!queue.isEmpty()) {
            System.out.println(queue.dequeue());
        }
    }
}

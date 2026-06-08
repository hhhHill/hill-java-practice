public class OddEvenPrinter {

    // 共享资源类
    static class Printer {
        private int count = 1;
        private final int max;

        public Printer(int max) {
            this.max = max;
        }

        // 打印奇数
        public synchronized void printOdd() {
            while (count <= max) {
                if (count % 2 != 0) {
                    System.out.println(Thread.currentThread().getName() + " 打印奇数: " + count);
                    count++;
                    // 打印完后，唤醒等待的偶数线程
                    this.notify();
                } else {
                    try {
                        // 如果不是奇数，当前线程等待，释放锁
                        this.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
            // 循环结束后，再次 notify 防止另一个线程死等（死锁）
            this.notify();
        }

        // 打印偶数
        public synchronized void printEven() {
            while (count <= max) {
                if (count % 2 == 0) {
                    System.out.println(Thread.currentThread().getName() + " 打印偶数: " + count);
                    count++;
                    // 打印完后，唤醒等待的奇数线程
                    this.notify();
                } else {
                    try {
                        // 如果不是偶数，当前线程等待，释放锁
                        this.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
            // 循环结束后，再次 notify 防止另一个线程死等（死锁）
            this.notify();
        }
    }

    public static void main(String[] args) {
        int max = 10;
        Printer printer = new Printer(max);

        Thread oddThread = new Thread(printer::printOdd, "奇数线程");
        Thread evenThread = new Thread(printer::printEven, "偶数线程");

        oddThread.start();
        evenThread.start();
    }
}
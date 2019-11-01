package java高并发;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;
public class CollectionsTest {

	public static void main(String[] args) {
		Map m = Collections.synchronizedMap(new HashMap());	//包装HashMap实现锁同步，性能一般
		List l = Collections.synchronizedList(new ArrayList());	//包装ArrayList实现锁同步，性能一般
		ConcurrentLinkedQueue queue = new ConcurrentLinkedQueue();	//无锁cas操作，性能优越
		CopyOnWriteArrayList list = new CopyOnWriteArrayList(); //修改副本，并在合适的时机写入，性能高
		BlockingQueue bQueue1 = new ArrayBlockingQueue(0);	//数组通知队列
		BlockingQueue bQueue2 = new LinkedBlockingQueue();	//链表通知队列
		ConcurrentSkipListMap cMap = new ConcurrentSkipListMap(); //声明一个跳表
	}
}

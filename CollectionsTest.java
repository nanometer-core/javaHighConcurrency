package java�߲���;
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
		Map m = Collections.synchronizedMap(new HashMap());	//��װHashMapʵ����ͬ��������һ��
		List l = Collections.synchronizedList(new ArrayList());	//��װArrayListʵ����ͬ��������һ��
		ConcurrentLinkedQueue queue = new ConcurrentLinkedQueue();	//����cas������������Խ
		CopyOnWriteArrayList list = new CopyOnWriteArrayList(); //�޸ĸ��������ں��ʵ�ʱ��д�룬���ܸ�
		BlockingQueue bQueue1 = new ArrayBlockingQueue(0);	//����֪ͨ����
		BlockingQueue bQueue2 = new LinkedBlockingQueue();	//����֪ͨ����
		ConcurrentSkipListMap cMap = new ConcurrentSkipListMap(); //����һ������
	}
}

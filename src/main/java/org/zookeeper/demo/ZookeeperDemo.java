package org.zookeeper.demo;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

/**
 * @author Administrator
 *zookeeperDemo
 */
public class ZookeeperDemo  implements Watcher{
// ���ƶ�ʱ�����ܵ�����zookeeper�ɹ���ʹ���Զ���һʵ���̻߳���
	private static CountDownLatch cdl = new CountDownLatch(1);

	public static void main(String[] args) throws Exception {
		ZooKeeper zk = new ZooKeeper("192.168.25.128:2181", 1000,new ZookeeperDemo());
		cdl.await();
		//System.out.println("the connetion is ok!");
//		ȡ���ӽڵ��б�
		System.out.println(zk.getChildren("/", true));
		// ��ȡ�ӽڵ�����
		System.out.println((zk.getData("/zookeeper", true, null)));
		// �����ڵ�
		zk.create("/hello", "hello".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		// ��ӡ�ڵ�����
		System.out.println(new String(zk.getData("/hello", true, null)));
		//�޸��ӽڵ�����
		zk.setData("/hello","hello very".getBytes(), -1);
		System.out.println(new String(zk.getData("/hello", true, null)));
		// �ر�����
		System.out.println(zk.getChildren("/", true));
		//�رսڵ�
		zk.close();
	}

	public void process(WatchedEvent event) {
		// ��ʾ���ӳɹ�
		 if(KeeperState.SyncConnected==event.getState()){
			 // ��ʱ����һ
			 cdl.countDown();
			 
		 }
		 // ��ӡ��ǰ�����Ļص�ʱ������
		 System.out.println("the connection is very+�����ڴ���"+event.getType());
	}

	 

}

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
// 类似定时器功能当连接zookeeper成功后使其自动减一实现线程唤醒
	private static CountDownLatch cdl = new CountDownLatch(1);

	public static void main(String[] args) throws Exception {
		ZooKeeper zk = new ZooKeeper("192.168.25.128:2181", 1000,new ZookeeperDemo());
		cdl.await();
		//System.out.println("the connetion is ok!");
//		取出子节点列表
		System.out.println(zk.getChildren("/", true));
		// 获取子节点数据
		System.out.println((zk.getData("/zookeeper", true, null)));
		// 创建节点
		zk.create("/hello", "hello".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		// 打印节点数据
		System.out.println(new String(zk.getData("/hello", true, null)));
		//修改子节点数据
		zk.setData("/hello","hello very".getBytes(), -1);
		System.out.println(new String(zk.getData("/hello", true, null)));
		// 关闭连接
		System.out.println(zk.getChildren("/", true));
		//关闭节点
		zk.close();
	}

	public void process(WatchedEvent event) {
		// 表示连接成功
		 if(KeeperState.SyncConnected==event.getState()){
			 // 定时器减一
			 cdl.countDown();
			 
		 }
		 // 打印当前出发的回调时间类型
		 System.out.println("the connection is very+你正在触发"+event.getType());
	}

	 

}

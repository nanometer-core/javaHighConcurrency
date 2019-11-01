package java高并发;

public class MyFutureTest {
	public static void main(String[] args) {
		Client client = new Client();	//新建请求类
		Data data = client.request("name");	//请求类发出请求
		System.out.println("请求完毕！");	//输出提示信息
		try {
			Thread.sleep(2000);	//休眠两秒
		}catch(InterruptedException e) {}	//捕捉中断异常
		System.out.println("数据="+data.getResult());		//输出结果
	}

}

class FutureData implements Data{	//定义契约类
	protected RealData realData = null;	//声明数据类
	protected boolean isReady = false;	//标记完成类
	public synchronized void setRealData(RealData realData) {	//设置数据
		if(isReady) {	//检查标记是否已设置
			return;	//返回
		}
		this.realData=realData;	//保存数据
		isReady = true;	//设置标记
		notifyAll();	//唤醒全部等待的线程
	}
	public synchronized String getResult() {	//获取结果
		while(!isReady) {	//如果数据没有准备好
			try {
				wait();	//在当前对象上等待
			}catch(InterruptedException e) {}	//捕捉中断异常
		}
		return realData.result;	//返回准备好的数据
	}
}

class RealData implements Data {	//定义数据类
	protected final String result;	//声明结果数据
	RealData(String para) {	//构造数据
		StringBuffer sb = new StringBuffer();	//新建字符缓冲类
		for(int i = 0;i<10;i++) {	//循环十次
			sb.append(para);	//将参数加入字符串
			try {
				Thread.sleep(100);	//休眠零点一秒
			}catch(InterruptedException e) {	//捕捉中断异常
			}
		}
		result = sb.toString();	//设置结果数据
	}
	public String getResult() {	//获取数据
		return result;	//返回结果数据
	}
}

class Client {	//定义请求类
	public Data request(final String queryStr) {	//实现请求方法
		final FutureData future = new FutureData();	//构造契约
		new Thread() {	//实例化新的线程
			public void run() {	//重写线程方法
				RealData realData = new RealData(queryStr);	//构建数据
				future.setRealData(realData);	//设置数据
			}
		}.start();	//启动线程
		return future;	//返回契约
	}
}

interface Data{	//定义契约接口
	public String getResult();	//定义接口方法
}
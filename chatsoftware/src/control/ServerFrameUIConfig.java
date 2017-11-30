package control;

//将所有的统一的配置参数最好定义到额外的配置文件（接口，属性是公共的静态常亮）
public interface ServerFrameUIConfig {
	public int serverFrameWidth=1000;
	public int serverFrameHeight=600;
	public String serverFrameTitle="Server";
	public String logoPath="resources/images/logo.jpg";
	public String serverIP="171.80.83.157";
	public int serverPort=8888;
	
}

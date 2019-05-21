package io.github.pleuvoir.more.apache;

public class RemotingConnect {

	private String ip;
	private String host;

	public RemotingConnect(String ip, String host) {
		super();
		this.ip = ip;
		this.host = host;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	@Override
	public String toString() {
		return "RemotingConnect [ip=" + ip + ", host=" + host + "]";
	}

}

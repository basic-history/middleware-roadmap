package io.github.pleuvoir.more.helper;

import java.util.Objects;

public class BrokerData  {

	private String brokerIp;

	private String brokerInstanceName;

	private long lastUpdateTimestamp;

	/**
	 * 实例名相同即认为是相同对象
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof BrokerData))
			return false;
		BrokerData that = (BrokerData) o;
		return Objects.equals(brokerInstanceName, that.brokerInstanceName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(brokerInstanceName);
	}

	public String getBrokerIp() {
		return brokerIp;
	}

	public void setBrokerIp(String brokerIp) {
		this.brokerIp = brokerIp;
	}

	public String getBrokerInstanceName() {
		return brokerInstanceName;
	}

	public void setBrokerInstanceName(String brokerInstanceName) {
		this.brokerInstanceName = brokerInstanceName;
	}

	public long getLastUpdateTimestamp() {
		return lastUpdateTimestamp;
	}

	public void setLastUpdateTimestamp(long lastUpdateTimestamp) {
		this.lastUpdateTimestamp = lastUpdateTimestamp;
	}
}



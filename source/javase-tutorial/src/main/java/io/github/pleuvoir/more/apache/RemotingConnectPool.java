package io.github.pleuvoir.more.apache;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

public class RemotingConnectPool extends BasePooledObjectFactory<RemotingConnect> {

	@Override
	public RemotingConnect create() throws Exception {
		RemotingConnect remotingConnect = new RemotingConnect("127.0.0.1", "8754");
		return remotingConnect;
	}

	@Override
	public PooledObject<RemotingConnect> wrap(RemotingConnect obj) {
		return new DefaultPooledObject<RemotingConnect>(obj);
	}

	
	
	
}

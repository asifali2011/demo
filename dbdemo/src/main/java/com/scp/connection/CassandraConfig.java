package com.scp.connection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import com.datastax.driver.core.ConsistencyLevel;
import com.datastax.driver.core.PlainTextAuthProvider;
import com.datastax.driver.core.QueryOptions;
import com.datastax.driver.core.SocketOptions;
import com.datastax.driver.core.policies.ConstantReconnectionPolicy;
import com.datastax.driver.core.policies.DCAwareRoundRobinPolicy;
import com.datastax.driver.core.policies.TokenAwarePolicy;

@Configuration
@EnableCassandraRepositories(basePackages = "${cassandra.basepackages}")
@PropertySource("classpath:cassandra.properties")
class CassandraConfig extends AbstractCassandraConfiguration {

	@Value("${cassandra.contactpoints}")
	private String contactPoints;

	@Value("${cassandra.port}")
	private int port;

	@Value("${cassandra.keyspace}")
	private String keySpace;

	@Value("${cassandra.username}")
	private String username;

	@Value("${cassandra.password}")
	private String password;

	@Value("${cassandra.basepackages}")
	private String basePackages;

	@Override
	public CassandraClusterFactoryBean cluster() {
//		CassandraClusterFactoryBean bean = super.cluster();
//		PlainTextAuthProvider sap = new PlainTextAuthProvider(username, password);
//		bean.setContactPoints(contactPoints);
//		bean.setReconnectionPolicy(new ConstantReconnectionPolicy(1000));
//		bean.setLoadBalancingPolicy(new TokenAwarePolicy(DCAwareRoundRobinPolicy.builder().build()));
//		SocketOptions so = new SocketOptions();
//		// read timeout to data reading from cassandra
//		so.setReadTimeoutMillis(600000000);
//		bean.setSocketOptions(so);
//		bean.setAuthProvider(sap);
		 CassandraClusterFactoryBean bean = super.cluster();
	        PlainTextAuthProvider sap = new PlainTextAuthProvider(username, password);
	        bean.setContactPoints(contactPoints);
	        bean.setAuthProvider(sap);

		return bean;
	}

	/**
	 * 
	 * Strong consistency: The data that we just wrote should be available when we
	 * read it stating there is no stale data. WRITE CL=QUORUM, READ CL=QUORUM: it
	 * will give high read and write speed without sacrificing availability.
	 */
//	@Override
//	protected QueryOptions getQueryOptions() {
//		QueryOptions queryOptions = new QueryOptions();
//		queryOptions.setConsistencyLevel(ConsistencyLevel.QUORUM);
//		return queryOptions;
//	}

	@Override
	protected String getKeyspaceName() {
		return keySpace;
	}

	@Override
	protected String getContactPoints() {
		return contactPoints;
	}

	@Override
	protected int getPort() {
		return port;
	}

	@Override
	public String[] getEntityBasePackages() {
		return new String[] { basePackages };
	}

	@Override
	protected boolean getMetricsEnabled() {
		return false;
	}
}

# ibetter-framework_0.2
ibetter内部使用框架

1.读写分离
	数据源配置:
	 <bean id="dataSource" class="org.apache.commons.dbcp2.BasicDataSource"> 
		<property name="driverClassName"  value="com.mysql.jdbc.Driver" ></property>
		<property name="url"  value="jdbc:mysql://localhost:3306/zm_test?useUnicode=true" ></property>
		<property name="username"  value="root" ></property>
		<property name="password"  value="123qwe" ></property>
	</bean>
	
	 <bean id="dataSource1" class="org.apache.commons.dbcp2.BasicDataSource"> 
		<property name="driverClassName"  value="com.mysql.jdbc.Driver" ></property>
		<property name="url"  value="jdbc:mysql://localhost:3306/zm_test1?useUnicode=true" ></property>
		<property name="username"  value="root" ></property>
		<property name="password"  value="123qwe" ></property>
	</bean>
	<bean id="dynamicDataSource" class="com.ibetter.spring.mybatis.datasource.DynamicDbSource">
		<property name="defaultTargetDataSource" ref="dataSource"></property>
		<property name="targetDataSources">
			<map>
				<entry key="rw" value-ref="dataSource"/>
				<entry key="r" value-ref="dataSource1"/>
			</map>
		</property>
	</bean> 
	
	<bean id="qCtxAdvice" class="com.ibetter.spring.mybatis.query.advice.MyBatisQContextAdvice" />
    <aop:config>
        <aop:aspect id="c" ref="qCtxAdvice">
            <aop:pointcut id="tx" expression="execution(* com.my.mapper.*.*.*(..))"/>
            <aop:around pointcut-ref="tx" method="execute"/>
        </aop:aspect>
    </aop:config> 
 

	@Repository
	public interface PersonMapper {
		//读数据源
		@QRoute("r")
		public List<Person> selectPersonList(QParam qParam);
		
		//使用默认数据源
		public long insertPerson(Person person); 
	}

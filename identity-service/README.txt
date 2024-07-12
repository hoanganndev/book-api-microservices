# Identity Service
- Trong spring boot: Để các service trong microservices giao tiếp được với nhau thông qua giao thức http thì có thư viện OpenFeign (OpenFeign là thành phần của Spring clound)
- Vì Identity Service cần gọi qua profile-service nên cần cài đặt dependencies ở Identity Service

+ cài dependencyManagement cho spring clound
+ Lưu ý mỗi spring boot version sẽ tương ứng với mỗi spring clound version khác nhau

	<properties>
	 	...
		<spring-cloud.version>2023.0.1</spring-cloud.version>
	</properties>
	

<dependencies>
<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-openfeign</artifactId>
		</dependency>
		</dependencies>
	...
	
	<dependencyManagement>
		<dependencies>
			<dependency>
				<groupId>org.springframework.cloud</groupId>
				<artifactId>spring-cloud-dependencies</artifactId>
				<version>${spring-cloud.version}</version>
				<type>pom</type>
				<scope>import</scope>
			</dependency>
		</dependencies>
	</dependencyManagement>
+

# Dependencies for setup

## build.gradle file configs

### For basic startup
```
	implementation 'org.springframework.boot:spring-boot-starter-webmvc'
```

### For Devtools

```
	developmentOnly 'org.springframework.boot:spring-boot-devtools'
```

### For Docker 

```
	developmentOnly 'org.springframework.boot:spring-boot-docker-compose'
```

### For postgres and jpa

```
	implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
	runtimeOnly 'org.postgresql:postgresql'
```

### For Validation 

```
  implementation 'org.springframework.boot:spring-boot-starter-validation'
```

### For UUID generators 

```
	implementation 'com.fasterxml.uuid:java-uuid-generator:5.1.0'
```

### For Security

```
	implementation 'org.springframework.boot:spring-boot-starter-security'
```

### For JWT

```
  implementation 'io.jsonwebtoken:jjwt-api:0.11.5'
  runtimeOnly 'io.jsonwebtoken:jjwt-impl:0.11.5'
  runtimeOnly 'io.jsonwebtoken:jjwt-jackson:0.11.5'
```

### For OAuth

```
  implementation 'org.springframework.boot:spring-boot-starter-security-oauth2-client'
  implementation 'org.springframework.boot:spring-boot-starter-security-oauth2-resource-server'
```

### For Mapstruct

```
  implementation 'org.mapstruct:mapstruct:1.6.3'
  implementation 'org.projectlombok:lombok-mapstruct-binding:0.2.0'
  annotationProcessor 'org.mapstruct:mapstruct-processor:1.6.3'
```

### For Lombok 

```
	compileOnly 'org.projectlombok:lombok'
	annotationProcessor 'org.projectlombok:lombok'
```

### For Testing

```
	testImplementation 'org.springframework.boot:spring-boot-starter-data-jpa-test'
	testImplementation 'org.springframework.boot:spring-boot-starter-security-test'
	testImplementation 'org.springframework.boot:spring-boot-starter-webmvc-test'
	testRuntimeOnly 'org.junit.platform:junit-platform-launcher'
```
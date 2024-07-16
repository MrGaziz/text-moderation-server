# SafeTextFilter

# Read the selection of Java + OpenAPI integration from here https://www.baeldung.com/java-openai-api-client


## Dependencies

Ensure that you include the following dependencies in your `build.gradle` file:

```groovy
dependencies {
    // Spring Boot dependencies
    implementation 'org.springframework.boot:spring-boot-starter'
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
    implementation 'org.springframework.boot:spring-boot-starter:3.3.1'
    testImplementation 'org.springframework.boot:spring-boot-starter-test:3.3.1'
    implementation 'org.springframework.boot:spring-boot-starter-web:3.3.1'

    // OkHttpClient for HTTP operations
    implementation 'com.squareup.okhttp3:okhttp:4.9.0'

    // Gson for JSON parsing
    implementation 'com.google.code.gson:gson:2.8.6'

    // OpenAI GPT-3 Java SDK for AI interactions
    implementation 'com.theokanning.openai-gpt3-java:service:0.18.2'
    implementation 'com.theokanning.openai-gpt3-java:api:0.18.2'
    implementation 'com.theokanning.openai-gpt3-java:client:0.18.2'
}

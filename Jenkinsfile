#!/usr/bin/env groovy

node {
    stage('checkout') {
        checkout scm
    }

    stage('check java') {
        sh "java -version"
    }

    stage('clean') {
        sh "chmod +x mvnw"
        sh "./mvnw clean"
    }

    stage('packaging') {
        sh "./mvnw verify -Pprod -DskipTests"
        archiveArtifacts artifacts: '**/target/*.war', fingerprint: true
    }

    stage('deploy') {
        sh "kill \$(lsof -t -i:8093) > /dev/null 2> /dev/null || : "
        sh "cd ./target/ && java -jar nagakawa-guarantee-backend-0.0.1-SNAPSHOT.war --spring.profiles.active=prod"
    }
}

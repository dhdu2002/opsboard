pipeline {
    agent any

    tools {
        maven 'Maven-3.9'
    }

    options {
        timestamps()
        timeout(time: 30, unit: 'MINUTES')
        disableConcurrentBuilds()
        buildDiscarder(logRotator(numToKeepStr: '20'))
    }

    parameters {
        booleanParam(name: 'RUN_DEPLOY', defaultValue: false, description: 'true면 내부 서버 배포 단계 실행')
        string(name: 'DEPLOY_HOST', defaultValue: '', description: '배포 대상 서버 호스트/IP (예: 10.10.1.20)')
        string(name: 'DEPLOY_USER', defaultValue: '', description: '배포 대상 SSH 계정')
    }

    environment {
        APP_NAME = 'opsboard'
        WAR_PATH = 'target/opsboard.war'
        DEPLOY_DIR = '/opt/tomcat/webapps'
        TOMCAT_SERVICE = 'tomcat'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build & Test') {
            steps {
                sh 'mvn -B clean test package'
            }
        }

        stage('Archive WAR') {
            steps {
                archiveArtifacts artifacts: 'target/*.war', fingerprint: true
            }
        }

        stage('Validate Deploy Inputs') {
            when {
                expression { return params.RUN_DEPLOY }
            }
            steps {
                script {
                    if (!params.DEPLOY_HOST?.trim()) {
                        error('RUN_DEPLOY=true 인 경우 DEPLOY_HOST 파라미터가 필요합니다.')
                    }
                    if (!params.DEPLOY_USER?.trim()) {
                        error('RUN_DEPLOY=true 인 경우 DEPLOY_USER 파라미터가 필요합니다.')
                    }
                }
            }
        }

        stage('Deploy To Internal Server') {
            when {
                allOf {
                    expression { return params.RUN_DEPLOY }
                    anyOf {
                        branch 'master'
                        branch 'main'
                    }
                }
            }
            steps {
                sshagent(credentials: ['opsboard-ssh-key']) {
                    sh '''
                    chmod +x scripts/deploy_war.sh
                    scripts/deploy_war.sh \
                      "$WAR_PATH" \
                      "${params.DEPLOY_HOST}" \
                      "${params.DEPLOY_USER}" \
                      "$DEPLOY_DIR" \
                      "$APP_NAME" \
                      "$TOMCAT_SERVICE"
                    '''
                }
            }
        }
    }

    post {
        always {
            junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
        }
        success {
            echo 'OpsBoard pipeline succeeded.'
        }
        failure {
            echo 'OpsBoard pipeline failed. Check Jenkins console logs.'
        }
    }
}

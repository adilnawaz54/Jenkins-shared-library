import groovy.json.JsonSlurper

def call(Map config) {
    pipeline {
        agent any

        environment {
            CONFIG_FILE = 'configuration/config.yaml'
        }

        stages {
            stage('Clone Repository') {
                steps {
                    script {
                        checkout scm
                    }
                }
            }

            stage('Load Configuration') {
                steps {
                    script {
                        def configFile = readYaml file: "${CONFIG_FILE}"
                        env.SLACK_CHANNEL_NAME = configFile.SLACK_CHANNEL_NAME
                        env.ENVIRONMENT = configFile.ENVIRONMENT
                        env.CODE_BASE_PATH = configFile.CODE_BASE_PATH
                        env.ACTION_MESSAGE = configFile.ACTION_MESSAGE
                        env.KEEP_APPROVAL_STAGE = configFile.KEEP_APPROVAL_STAGE
                    }
                }
            }

            stage('User Approval') {
                when {
                    expression { env.KEEP_APPROVAL_STAGE.toBoolean() }
                }
                steps {
                    timeout(time: 5, unit: 'MINUTES') {
                        input message: "Deploy Elasticsearch to ${env.ENVIRONMENT}?"
                    }
                }
            }

            stage('Playbook Execution') {
                steps {
                    script {
                        sh "ansible-playbook playbooks/deploy-elasticsearch.yml -i inventory/${env.ENVIRONMENT}"
                    }
                }
            }

            stage('Notification') {
                steps {
                    script {
                        slackSend channel: "#${env.SLACK_CHANNEL_NAME}", message: "${env.ACTION_MESSAGE} - Deployment Completed"
                    }
                }
            }
        }
    }
}


def call(Map config = [:]) {
    pipeline {
        agent any

        stages {
            stage('Clone Repository') {
                steps {
                    script {
                        checkout scm
                    }
                }
            }

            stage('User Approval') {
                steps {
                    script {
                        timeout(time: 5, unit: 'MINUTES') {
                            input message: "Deploy to ${config.ENVIRONMENT}?"
                        }
                    }
                }
            }

            stage('Execute Task') {
                steps {
                    script {
                        echo "Executing task for ${config.ENVIRONMENT}"
                    }
                }
            }

            stage('Send Notification') {
                steps {
                    script {
                        slackSend channel: "#${config.SLACK_CHANNEL_NAME}", message: "Deployment Completed!"
                    }
                }
            }
        }
    }
}


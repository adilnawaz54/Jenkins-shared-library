def call() {
    def config = loadConfig()  

    pipeline {
        stages {
            stage('Execute Playbook') {
                steps {
                    script {
                        sh "ansible-playbook -i ${config.CODE_BASE_PATH}/inventory ${config.CODE_BASE_PATH}/playbook.yml"
                    }
                }
            }
        }
    }
}

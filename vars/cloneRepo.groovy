def call(String repoUrl) {
    def config = loadConfig()  // Load config file
    def branch = config.ENVIRONMENT == "prod" ? "main" : "develop"
    
    // Directory name (assumes the repo is named shared_library)
    def dirName = 'shared_library'
    
    // Remove the existing directory if it exists
    sh "rm -rf ${dirName}"

    // Clone the repository based on the branch
    sh "git clone -b ${branch} ${repoUrl} ${dirName}"
}

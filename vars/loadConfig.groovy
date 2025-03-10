
def call() {
    // Load YAML file using the built-in readYaml step
    def configFile = libraryResource 'config.yml'  // Load the YAML file as a resource
    def yaml = readYaml text: configFile  // Parse the YAML content
    return yaml
}

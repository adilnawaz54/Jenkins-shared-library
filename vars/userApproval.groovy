def call() {
    def config = loadConfig()  // Load configuration

    if (config.KEEP_APPROVAL_STAGE) {  
        // If approval is required, echo and skip approval
        echo "Approval stage skipped, as KEEP_APPROVAL_STAGE is true."
    } else {
        input message: "Proceed with deployment?", ok: 'Approve'
    }
}

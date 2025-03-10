def call(String message) {
    slackSend(channel: env.SLACK_CHANNEL_NAME, message: message, color: '#36a64f')
}


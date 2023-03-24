# Translation slack bot

## Usage

### Individual translations

The bot replies with a translation whenever a user reacts to a message with a flag emoji, among some supported langues:

- Portuguese :flag-pt:
- Spanish :es:
- English :flag-england:

This translation is sent as an ephemeral message only visible to the user which requested the translation.

### Global translations

A user might want to translate their own message to the whole slack community. The users can tag the bot and they will be able to choose the target language trough some buttons sent by the bot.


## How to run

### Env variables

Please define the following environmental variables:

- SLACK_TOKEN: Bot token obtained in *OAuth and Permissions* slack api page 
- DEEPL_TOKEN: DeepL translation token, obtained in *Account* DeepL page

### Slack config

#### Bot token scopes

The following bot token scopes should be added

- **app_mentions**:read
- **channels**:history
- **channels**:read
- **chat**:write
- **chat**:write.public
- **reactions**:read

#### Events

The following bot events should be subscribed:
- **app_mentions**
- **reactions_added**

If running locally, use ngrok to forward the port.

Request URL: `https://example.eu.ngrok.io`

#### Interactivity & shortcuts

If running locally, use ngrok to forward the port.

Request URL: `https://example.eu.ngrok.io/interactivity`

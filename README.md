# ChatMIstra

A simple Java console application that allows you to chat with Mistral AI's API.

## Features

- Interactive chat interface with Mistral AI
- Support for the `mistral-small-latest` model
- Secure API key management through config file or environment variables
- JSON response parsing to extract clean chat responses

## Prerequisites

- Java 8 or higher
- Maven
- Mistral AI API key

## Setup

### 1. Clone the repository
```bash
git clone <repository-url>
cd ChatMIstra
```

### 2. Configure API Key

You have two options to set up your Mistral API key:

**Option 1: Using config.properties file**
Create a `config.properties` file in `src/main/resources/`:
```properties
MISTRAL_KEY=your_mistral_api_key_here
```

**Option 2: Using environment variable**
Set the environment variable:
```bash
export MISTRAL_KEY=your_mistral_api_key_here
```

### 3. Build the project
```bash
mvn clean install
```

## Usage

Run the application:
```bash
mvn exec:java -Dexec.mainClass="com.example.chatmistra.ChatMIstraApplication"
```

Once running, you can:
- Type your message and press Enter to chat with Mistral
- Type `quit` to exit the application

## Dependencies

- Jackson Databind: For JSON parsing
- Java standard libraries for HTTP communication

## Example

```
Me: Hello, how are you?
🤖 Mistral: {"id":"3464a5357b36409bbcca1aa554aa6f58","created":1776278929,"model":"mistral-small-latest","usage":{"prompt_tokens":17,"total_tokens":33,"completion_tokens":16,"prompt_tokens_details":{"cached_tokens":0}},"object":"chat.completion","choices":[{"index":0,"finish_reason":"stop","message":{"role":"assistant","tool_calls":null,"content":"¡Hola! 😊 ¿En qué puedo ayudarte hoy?"}}]}
Mistral: ¡Hola! 😊 ¿En qué puedo ayudarte hoy?
```

## Security Notes

- Never commit your API key to version control
- Use environment variables for production deployments
- The config.properties file should be added to .gitignore

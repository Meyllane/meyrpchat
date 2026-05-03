<div align="center">

# MeyRPChat

**A Roleplay Chat Plugin for Minecraft 1.21**

[![Minecraft](https://img.shields.io/badge/Minecraft-1.21-62b347?style=flat-square&logo=minecraft)](https://minecraft.net)
[![License](https://img.shields.io/github/license/Meyllane/MeyRPChat?style=flat-square)](LICENSE)
[![Release](https://img.shields.io/github/v/release/Meyllane/MeyRPChat?style=flat-square)](https://github.com/Meyllane/meyrpchat/releases)

</div>

---

## Overview

**MeyRPChat** allows you to set up **Channels**, a set of formatting and sending rules that will be triggered when a
pre-configured non-alphanumeric is found at the beginning of a message sent by the player. 

Whether you want whispered emotes, in-character speech, or out-of-character brackets, MeyRPChat gives you full control over how your players communicate.

---

## Features

- **Prefix-based Channels** — Define any non-alphanumeric character as a channel trigger. The plugin will then handle the formatting and the emission of the message.
- **Fully Configurable** — All behavior is driven by a single, well-structured `config.yml`. The plugin fully leverages AdventureAPI, allowing for the use of Tags in the format section, providing great flexibility. The plugin even adds custom Tags to allow for a greater control!

---

## Installation

1. Download the latest `.jar` from the [**Releases page**](https://github.com/Meyllane/meyrpchat/releases)
   > Or build it yourself directly from the source (see [Building from Source](#building-from-source) below)
2. Place the `.jar` file in your server's `plugins/` folder
3. Restart your server
4. Configure the plugin via the generated `config.yml` (see [Configuration](#configuration))

### Building from Source

````bash
git clone https://github.com/Meyllane/meyrpchat.git
cd MeyRPChat
./gradlew build   # or: mvn package
````

The compiled `.jar` will be in `build/libs/` (Gradle).

---

## Configuration

All configuration is handled in the `config.yml` file, automatically generated in the plugin's folder (`plugins/MeyRPChat/`) on first load.

For a full walkthrough of every option, refer to the **[📚 Wiki](../../wiki)**.

### Quick Example

Define Ranges, a standard object that represents all the information the plugin's need when sending a message that has
a limited range.

````yaml
range:
   - prefix: "#" #This can be empty
     range: 3
     descriptor: "whispers"
     color: "#842bb0"
     default: false
   - prefix: "-"
     range: 10
     descriptor: "says"
     color: "#2b6bb0"
     default: false
````

Define Channels, the core object that will handle the formating of the message, the permissions to emit or receive the
said message and indicate which category of channels has to be used.

````yaml
channel:
   - prefix: ""
     type: "ranged"
     format: "├├ <displayname/> [<descriptor>] <rangecolor><message/></rangecolor>"
     options:
        sendPermission: "meyrpchat.channel.send.basic"
        receivePermission: "meyrpchat.channel.receive.basic"
   - prefix: "*"
     type: "ranged"
     format: "►<displayname> <italic><rangecolor><message></rangecolor></italic>"
````

---

## 📚 Wiki

The [**Wiki**](../../wiki) covers everything that you will need to configure and use the plugin.

---

## 🤝 Contributing

Contributions, issues, and feature requests are welcome! Feel free to open an [issue](../../issues) or submit a pull request.

---

## 📄 License

Distributed under [CC BY-NC-SA](LICENSE).
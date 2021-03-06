/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.modules.chatlogger.config;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

@ConfigSerializable
public class ChatLoggingConfig {

    @Setting(value = "enable-logging", comment = "loc:config.chatlog.enable")
    private boolean enableLog = false;

    @Setting(value = "log-chat", comment = "loc:config.chatlog.chat")
    private boolean logChat = true;

    @Setting(value = "log-messages", comment = "loc:config.chatlog.message")
    private boolean logMessages = true;

    @Setting(value = "log-mail", comment = "loc:config.chatlog.mail")
    private boolean logMail = false;

    public boolean isEnableLog() {
        return enableLog;
    }

    public boolean isLogChat() {
        return logChat;
    }

    public boolean isLogMessages() {
        return logMessages;
    }

    public boolean isLogMail() {
        return logMail;
    }
}

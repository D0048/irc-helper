# irc-helper
Another irc bot depends on pircbot-plugin support now coming.

## General usage:

1. Download the runnable jar file from releases.
2. Run the jar file `java -jar irc-helper.jar`
3. Configuration files and chat logs will be recorded at the current dictionary, change it if needed.

## Configuration file:
`sudoers = d0048` Users that have the access to sudo commands, seperate multiple users with a `, `
`sudoPwd = 123123` The sudo password, please modify for the sake of security
`server = irc.freenode.net` The server to connect, only one server is currently supported
`name = testbot` The nick of the bot
`pwd = 123123` The password of that nick, with be send via `/msg NickServ identify {pwd}`
`channels = #berton-research` Channels to join on start up, seperated with `, `
`preffix = -` The prefix for commands to call this bot, change if it conflicts with others
`recordFileName = helper.record` The file name used to read records from and write records to
`useProxy = 0` `0` For not using proxy and `1` for using proxy
`Proxy = 127.0.0.1:1080` The proxy to use, if enabled.(Only socks supported)

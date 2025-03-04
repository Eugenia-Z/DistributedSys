📌 什么是 apt？
apt（Advanced Package Tool）是 Debian 及其衍生 Linux 发行版（如 Ubuntu）中用于管理软件包的工具。它可以用来：

安装 软件包：apt install <package-name>
更新 软件包列表：apt update
升级 已安装的软件包：apt upgrade
卸载 软件包：apt remove <package-name>
apt 从**软件仓库（repository）**下载和安装软件包，默认使用官方的 Debian/Ubuntu 源

📌 什么是 Cloudsmith？
Cloudsmith 是一个软件包托管和分发平台，允许开发者创建私有或公共的仓库，存储各种格式的软件包（如 deb、rpm、npm、docker 等）。

🚀 RabbitMQ 使用 Cloudsmith 提供官方 apt 仓库

RabbitMQ 官方提供的 DEB 软件包 托管在 Cloudsmith 上，而不是 Ubuntu/Debian 的官方仓库。
你需要手动添加 Cloudsmith 提供的 RabbitMQ 仓库，然后使用 apt 进行安装。

# Debian 是 Ubuntu 的“祖先”

Debian 是一个独立的、社区驱动的 Linux 发行版，最早发布于 1993 年。
Ubuntu 是 基于 Debian 开发的，最早由 Canonical 公司在 2004 年发布。
Ubuntu 继承了 Debian 的包管理系统（APT、dpkg）和大部分软件包。

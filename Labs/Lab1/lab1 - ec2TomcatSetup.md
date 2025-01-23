1. Security Groups

2. Nano vs. Vim editor

- both are text editors commonly used in terminal environments

Nano:

- ^O for save
- ^X for exit
- ^K for cut
- ^U for paste

Vim:

- modes: Normal mode, insert mode, visual mode, command mode(for advanced commands - search, replace etc.)
- i: switch to insert mode
- esc: return to normal mode
- :wq write and quit
- /search_term: search for text in the file

3. sudo useradd -d /usr/share/tomcat -r -s /bin/false -g tomcat tomcat

- sudo: Runs the command with superuser (root) privileges
- useradd: Create a new user account on the system.
- -d: Specifies the home directory for the user.
- -r: Tells the system to create a system account.
- -s: Specifies the shell the user will use.
- /bin/false: Sets the user’s shell to /bin/false, which effectively prevents the user from logging in to the system interactively.
- -g: Specifies the primary group for the user.

The command creates a system user named tomcat with the home directory /usr/share/tomcat, no login shell (/bin/false), and with the primary group set to tomcat. This user will typically be used to run the Tomcat server, without interactive login access.

4. getent passwd tomcat

- getent stands for get entries. It is a command-line tool used to query system databases, such as:
  /etc/passwd (user accounts)
  /etc/group (groups)
  /etc/hosts (hostname information)
- passwd: database contains information about user accounts on the system. This includes:
  Username
  Password (or placeholder if using shadow passwords)
  User ID (UID)
  Group ID (GID)
  Home directory
  Login shell

5. sudo yum -y install wget

- yum: The Yellowdog Updater Modified (YUM) is a package manager for RPM-based distributions
- wget is a command-line utility for downloading files from the web via HTTP, HTTPS, or FTP protocols.

6. sudo chown -R tomcat:tomcat /usr/share/tomcat

- chown: change ownership.
- R recursive

7. # Enable and start tomcat service
   sudo systemctl daemon-reload
   sudo systemctl start tomcat
   sudo systemctl enable tomcat

# Check your tomcat service status

sudo service tomcat status

8. Configure Tomcat Authentication
   sudo nano /usr/share/tomcat/conf/tomcat-users.xml

9. symlink

- symbolic link is a type of file in Linux and other Unix-like operating systems that acts as a pointer or shortcut to another file or directory
- It allows you to reference the target file or directory from a different location in the filesystem.

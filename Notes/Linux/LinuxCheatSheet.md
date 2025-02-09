ssh: secure shell
mkdir xxx
cd xxx
ls
ls -la (list all)
pwd
cd .. (one level up)
cp -r (copy all)
(r for recursive -incl all files and subdirectories)
from folder A to B
scp (secure copy)
cp path1 path2

1. chmod: change mode (change permissions of a file/directory)

# File Permission Basics

Permissions are divided into three categories:

- User (u): The owner of the file.
- Group (g): A group of users who have access.
- Others (o): Everyone else.

Each permission type has three actions:

- Read (r): Allows viewing the contents.
- Write (w): Allows modifying the contents.
- Execute (x): Allows running the file as a program.

- Symbolic mode vs. Numeric(Octal) mode
  chmod u+x file # Add execute permission for the owner
  chmod g-w file # Remove write permission for the group
  chmod o=r file # Set read-only permission for others
  chmod a+rw file # Allow everyone to read and write

- Permissions are represented as a three-digit number, where each digit is a sum of:

4 for Read (r)
2 for Write (w)
1 for Execute (x)
chmod 755 file:
Owner: 7 (Read + Write + Execute = 4 + 2 + 1)
Group: 5 (Read + Execute = 4 + 1)
Others: 5 (Read + Execute = 4 + 1)

# grep: Global Regular Expression Print

- search for specific patterns of text within files or output streams
  grep [options] PATTERN [FILE...]

1. Search for a word in a file: grep "hello" file.txt
2. Ignore case sensitivity: grep -i "hello" file.txt
3. Search recursively in directories: grep -r "error" /var/log/
4. Count matches: grep -c "success" file.txt
5. Show line numbers: grep -n "TODO" file.txt
6. Search for an exact word: grep -w "world" file.txt
7. Invert the search: grep -v "error" file.txt
8. Use regular expressions: grep "^[A-Za-z]" file.txt

Use cases:

1. Search system logs: grep "ERROR" /var/log/syslog
2. Monitor logs in real-time: tail -f /var/log/syslog | grep "ERROR"
3. Find processes by name: ps aux | grep "nginx"
4. Extract specific data: cat file.csv | grep "2025" | grep -i "completed"

# Linux Utilities

# Process management

- ps aux :List all running processes on the system with details (PID/CPU/Memory etc. )
- top: dynamic real-time view of system processes
- kill: terminates a process using its PID (-9 force kill)

# File and Directory Management

ls # Lists files in the current directory
ls -l # Shows detailed information (permissions, size, etc.)
ls -a # Includes hidden files
File Viewing:

- cat: display the content of a file
- tail: shows the last few lines of a file (often used for logs)
  - tail -n 10 file.txt # Shows the last 10 lines
  - tail -f file.txt # Follows new updates in the file in real-time
- head: shows the first few lines of a file
  - head -n 5 file.txt # Shows the first 5 lines

# Networking:

- ping: test the connectivity to another device: ping google.com
- curl: Makes HTTP requests to test or fetch data from web servers curl https://example.com
- wget: downloads files from the internet

# System Monitoring:

- df: Shows available disk space
- du: Shows the size of files or directories.
  - du -sh /path/to/folder
- free: display memory usage
  - free -h
- uptime: Shows how long the system has been running and the current load.

# Search and Filters

- grep: Searches for patterns in text files or output.: grep "error" log.txt
- find: Searches for files or directories: find / -name "file.txt"
- sort: Sorts lines of text: sort file.txt
- awk: A text-processing tool used for extracting or transforming text: awk '{print $1}' file.txt

# File Compression:

- tar: Archives files into a single file and extracts them.
  - tar -cvf archive.tar file1 file2
  - tar -xvf archive.tar
    (c-create; x-extract; v-verbose; f-filename)
- gzip: compressed files
  - gzip file.txt
  - gunzip file.txt.gz

# ThingBot Binary In/Out Deputy

# Deployed file structure
/var/log/tbot			base directory for all TBot related logs.
/var/lib/tbot-deputy	directory where deputy stores files with current outputs values.
/etc/tbot-deputy		directory where configuration files needs to be located.
/srv/tbot-deputy		directory where start scripts and jar files need to placed.

# Installation
Only manual installation is available at the time. Recommended steps are as follows:

0. Assumptions
All steps below should be done as root (sudo), all files mentioned to be owned by root user.
The server itself needs to be also run by the root.

1. Configuration.
	i. Create /etc/tbot-deputy/ directory and copy there:
		* tbot-deputy.yml
		* logback.xml
	ii. Modify these files if needed however they should be good enough for default configuration.

2. Executables.
	i. Create /srv/tbot-deputy/ directory and copy there:
		* tbot-deputy.sh				make sure it's executable (chmod 755)
		* tbot-deputy-<version>.jar		depending on required deputy version
	ii. Create link to jar. Sample command is:
		ln -s tbot-deputy-<version>.jar tbot-deputy.jar
	
3. Service.
When running deputy as a service is demanded, check following procedure.
	i. Copy tbot-deputy-d.sh script to /etc/init.d. Make sure it's executable (chmod 755).	 
	ii. If you wish to start it on system boot, try this:
		update-rc.d tbot-deputy-d.sh defaults
	iii. If you need to stop running it on system boot, remove it from run levels:
		update-rc.d -f tbot-deputy-d.sh remove
		
# Enjoy!
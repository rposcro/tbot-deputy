# ThingBot Binary In/Out Deputy

## Hardware
ThingBot Deputy is a REST service designed to control GPIO ports available on mini computer solutions 
like Raspberry Pi or similar. Following section discusses hardware prerequisites before actual sevice
deployment.

### Raspberry Pi
The instruction below is good in July 2020, there is no guarantee it is still valid at the time of reading.

0. Prepare SD card with Raspberry Pi OS (former Raspbian), instructions can be found at the vendor page: 
https://www.raspberrypi.org/downloads/. For applications assuming pure GPIO utilization, I recommend lite
version of the system.

0. By default, the only OS user is `pi` with password `raspberry`. At least change the user's password, 
better add new user which you share among your local network.<br>
Create the user with home dir and groups:<br>
`sudo useradd -m -g users -G adm,dialout,cdrom,sudo,audio,video,plugdev,games,input,netdev,gpio,i2c,spi username`<br>
Don't forget to change the user's password.

0. Raspberry OS comes with default _raspberrypi_ host name, it's good to custom the hostname on network,
to change it, edit `/etc/hostname` and `/etc/hosts` files.  

0. RPi OS comes without SSH server disabled. When device configuration is ready, our deputy RPi is put 
as standalone on the local network with no screen nor keyboard connected. Sometimes it's necessary to reach
it remotely and SSH is recommended for the purpose. <br><br>
Type `sudo systemctl enable ssh` to start the server at system boot.<br>
Next type `sudo systemctl start ssh` to start it without rebooting the system.<br><br>
Handy is to set up user public key to login without password.

0. Install WiringPi tool. For installation and usage guidance check the page here: 
http://wiringpi.com/download-and-install/

0. Install OpenJDK 8: `sudo apt-get install open-jdk-8-jdk`

0. Your device is ready, put it in the destination point of your home or facility, connect to network and run.
Next go on with ThingBot service installation.

## Service Installation
### File structure
`/var/log/tbot`			base directory for all TBot related logs.<br>
`/var/lib/tbot-deputy`	directory where deputy stores files with current outputs values.<br>
`/etc/tbot-deputy`		directory where configuration files needs to be located.<br>
`/srv/tbot-deputy`		directory where start scripts and jar files need to placed.

### Installation
Only manual installation is available at the time. Recommended steps are as follows:

0. Assumptions
All steps below should be done as root (sudo), all files mentioned to be owned by root user.
The server itself needs to be also run by the root.

0. Configuration.
	i. Create /etc/tbot-deputy/ directory and copy there:
		* tbot-deputy.yml
		* logback.xml
	ii. Modify these files if needed however they should be good enough for default configuration.

0. Executables.
	i. Create /srv/tbot-deputy/ directory and copy there:
		* tbot-deputy.sh				make sure it's executable (chmod 755)
		* tbot-deputy-<version>.jar		depending on required deputy version
	ii. Create link to jar. Sample command is:
		ln -s tbot-deputy-<version>.jar tbot-deputy.jar
	
0. Service.
When running deputy as a service is demanded, check following procedure.
	i. Copy tbot-deputy-d.sh script to /etc/init.d. Make sure it's executable (chmod 755).	 
	ii. If you wish to start it on system boot, try this:
		update-rc.d tbot-deputy-d.sh defaults
	iii. If you need to stop running it on system boot, remove it from run levels:
		update-rc.d -f tbot-deputy-d.sh remove
		
### Enjoy!
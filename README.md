# Java-UDP-Server
## java desktop application :
For receiving UDP packets and also for processing some of it ,
- allways opens This port #1111 : unless it's been changed in  the code.
- shows the local ip address #192.168.0.0
- shows the public ip address using #inputStream("http://ipv4bot.whatismyipaddress.com")
- Requires : NIRCMD app for most commands : http://www.nirsoft.net/utils/nircmd.html 

![capture](https://user-images.githubusercontent.com/28542963/27280302-c05fc518-54e7-11e7-89f8-59f342b76f83.PNG)

### recognized String UDP Packets : 
- musicon  : starts windows media player ( all songs that have been played before )
- musicoff : stops the windows media player
- next : starts WMP again with random 
- mute : mute windows sound 
- unmute : unmute windows sound 
- From ( 0 ) --> ( 10 ) : for controling the volume value ( 0 ) = mute + 0 value , ( 10 ) = maximum volume  value
- ( Any other word not recognized ) : will be pronounced 
- standby : standby
- poweroff  : shutdown pc
- screenoff : close the screen
- cdon : opens cdrom

- And others .. 

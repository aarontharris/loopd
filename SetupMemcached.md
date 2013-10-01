Tools you will need:<br>
Note: if you download to ~/Downloads, the scripts below will work<br>
Libevent 2.0.21 @ https://github.com/downloads/libevent/libevent/libevent-2.0.21-stable.tar.gz<br>
Memcached v1.4.15 @ http://memcached.org<br>

Installation Vars:
```
memc_ver=1.4.15
lib_ver=2.0.21
```

Set Memcached Environment
```
echo "" >> ~/.bash_profile
echo "# MEMCACHED DEVELOPER ENVIRONMENT" >> ~/.bash_profile
echo "export MEMC_PORT=11211" >> ~/.bash_profile
echo "export MEMC_HOME='$HOME/dev/services/memcached'" >> ~/.bash_profile
echo "export PATH=\$PATH:\$MEMC_HOME/bin" >> ~/.bash_profile
echo "" >> ~/.bash_profile
source ~/.bash_profile
```

Move Memcached into its home
```
mkdir -p $MEMC_HOME/bin
cp -R ~/Downloads/memcached-$memc_ver $MEMC_HOME/memcached-$memc_ver
```

You'll need to build Memcached<br>
If ./configure fails see [Setup Libevent](https://github.com/aarontharris/loopd/wiki/Setup-Libevent-for-Memcached)
```
cd $MEMC_HOME/memcached-$memc_ver
./configure
sudo make install
```

Our run script
```
memc_bin=`which memcached`
echo '#!/bin/bash' > $MEMC_HOME/bin/memcachedrun
echo "$memc_bin" >> $MEMC_HOME/bin/memcachedrun
chmod 755 $MEMC_HOME/bin/memcachedrun
```

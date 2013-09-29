loopd
=====

Loopd Server v0.1 BETA

Wiki @ https://github.com/aarontharris/loopd/wiki

Tools you will need:
====================

Maven v3 (or newer) @ http://maven.apache.org/download.cgi<br>
Git v1.7.9.6 (or newer) @ http://git-scm.com/downloads<br>
MongoDB v2.4.6 @ http://www.mongodb.org/downloads<br>

Installation of loopd server:
=============================

Create a home for MongoDB<br>
```
mkdir ~/dev/tools/mongo
mkdir ~/dev/tools/mongo/data
mkdir ~/dev/tools/mongo/data/loopd
```

Move mongodb into tools/mongo<br>
```
cd ~/dev/tools/mongo
cp -R ~/Downloads/mongodb-osx-x86_64-2.4.6 ./
```

Set Mongo Environment (you can copy and paste -- it should run?)<br>
```
echo "" >> ~/.bash_profile
echo "# MONGO ENV" >> ~/.bash_profile
echo "export MONGO_PORT=26017" >> ~/.bash_profile
echo "export MONGO_HOME='$HOME/dev/tools/mongo/mongodb-osx-x86_64-2.4.6'" >> ~/.bash_profile
echo "export MONGO_DATA='$HOME/dev/tools/mongo/data'" >> ~/.bash_profile
echo "export PATH=\$PATH:\$MONGO_HOME/bin" >> ~/.bash_profile
echo "" >> ~/.bash_profile
source ~/.bash_profile
```

Our Mongo run script 'mongorun'<br>
```
echo '#!/bin/bash' >> $MONGO_HOME/bin/mongorun
echo "mongod \\" >> $MONGO_HOME/bin/mongorun
echo "  --port 26017 \\" >> $MONGO_HOME/bin/mongorun
echo "  --dbpath $MONGO_DATA/loopd \\" >> $MONGO_HOME/bin/mongorun
echo "  --auth \\" >> $MONGO_HOME/bin/mongorun
echo "  --setParameter supportCompatibilityFormPrivilegeDocuments=0" >> $MONGO_HOME/bin/mongorun
chmod 755 $MONGO_HOME/bin/mongorun
```



Pull down the server code<br>
```
git clone git@github.com:aarontharris/loopd.git
```

Pull down libraries and build using maven<br>
```
mvn clean install
```



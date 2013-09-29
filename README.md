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

Set Mongo Environment (you can copy and paste -- it should run?)<br>
```
echo "" >> ~/.bash_profile
echo "# MONGO DEVELOPER ENVIRONMENT" >> ~/.bash_profile
echo "export MONGO_PORT=26017" >> ~/.bash_profile
echo "export MONGO_DBNAME=loop" >> ~/.bash_profile
echo "export MONGO_DBUSER=loopuser" >> ~/.bash_profile
echo "export MONGO_DBPASS=looppass" >> ~/.bash_profile
echo "export MONGO_HOME='$HOME/dev/services/mongo/mongodb-osx-x86_64-2.4.6'" >> ~/.bash_profile
echo "export MONGO_DATA='$HOME/dev/services/mongo/data'" >> ~/.bash_profile
echo "export PATH=\$PATH:\$MONGO_HOME/bin" >> ~/.bash_profile
echo "" >> ~/.bash_profile
source ~/.bash_profile
```

Create a home for MongoDB<br>
```
mkdir -p $MONGO_DATA/$MONGO_DBNAME
```

Move mongodb into tools/mongo<br>
```
cp -R ~/Downloads/mongodb-osx-x86_64-2.4.6 $MONGO_HOME
```

Our Mongo run script 'mongorun'<br>
```
echo '#!/bin/bash' > $MONGO_HOME/bin/mongorun
echo "mongod \\" >> $MONGO_HOME/bin/mongorun
echo "  --port \$MONGO_PORT \\" >> $MONGO_HOME/bin/mongorun
echo "  --dbpath \$MONGO_DATA/\$MONGO_DBNAME \\" >> $MONGO_HOME/bin/mongorun
echo "  --auth \\" >> $MONGO_HOME/bin/mongorun
echo "  --setParameter supportCompatibilityFormPrivilegeDocuments=0" >> $MONGO_HOME/bin/mongorun
chmod 755 $MONGO_HOME/bin/mongorun
```

Run Mongo without auth, setup admin and stop mongo (make sure mongo isn't already running)
```
mongod --port $MONGO_PORT --dbpath $MONGO_DATA/$MONGO_DBNAME & # run mongo in the background
mongo_pid=$! # cache the pid mongo started with
sleep 2 # give the db a chance to wake up
mongo --port $MONGO_PORT \
  --eval "\
    db = db.getSiblingDB('admin');\
    db.addUser( { user: 'admin', pwd: 'adminadmin', roles: [ 'userAdminAnyDatabase', 'readWriteAnyDatabase' ] } );\
    "
mongo --port $MONGO_PORT \
  --eval "\
    db = db.getSiblingDB('$MONGO_DBNAME');\
    db.addUser( { user: '$MONGO_DBUSER', pwd: '$MONGO_DBPASS', roles: [ 'readWrite' ] } );\
    "
sleep 2 # give the db a chance to write
kill $mongo_pid # soft kill the server
mongo_pid= # clear mongo pid
```

Rerun Mongo with auth
```
mongorun
```

Test the dbuser connection with auth
```
mongo --port $MONGO_PORT -u $MONGO_DBUSER -p $MONGO_DBPASS $MONGO_DBNAME
  db.deleteme.save({key: 'blah', value: 'asdf'});
  db.deleteme.find( {key:'blah'});
  db.deleteme.drop();
  show dbs # should return errmsg: "unauthorized"
  exit
```

MONGO COMPLETE!


Pull down the server code<br>
```
git clone git@github.com:aarontharris/loopd.git
```

Pull down libraries and build using maven<br>
```
mvn clean install
```


